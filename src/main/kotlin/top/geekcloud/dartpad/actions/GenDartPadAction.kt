package top.geekcloud.dartpad.actions

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.vfs.VirtualFile
import kotlinx.coroutines.*
import top.geekcloud.dartpad.utils.*


class GenDartPadAction : AnAction("Gen Dart Pad") {

    override fun update(e: AnActionEvent) {
        e.presentation.isVisible = false
        e.presentation.isEnabled = false

        if (e.project == null) {
            log.error("e.project == null")
            return
        }

        if (GistsCreator.token == null) {
            log.error("GistsCreator.token == null")
            return
        }

        val currentDoc: Document = FileEditorManager.getInstance(e.project!!).selectedTextEditor!!.document
        val currentFile: VirtualFile = FileDocumentManager.getInstance().getFile(currentDoc) ?: return

        val type: FileType = currentFile.fileType
        val isDart = type is LanguageFileType && type.displayName == "Dart"
        if (isDart) {
            e.presentation.isVisible = true
            e.presentation.isEnabled = true
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        NotificationHelper.sendNotification(e.project, "正在生成 dartpad")
        val file = FileTypeHelper.getFileByAnActionEvent(e)
        val doc = FileTypeHelper.getDocByAnActionEvent(e)
        if (file == null || doc == null) {
            NotificationHelper.sendNotification(e.project, "失败: 获取文件失败")
            return
        }

        runBlocking {
            try {
                val id = GistsCreator.createGists(file.name, doc.text)

                val dartpadUrl = "https://dartpad.dev/$id"
                log.info(id)

                NotificationGroupManager.getInstance()
                    .getNotificationGroup("DefGroup")
                    .createNotification(
                        "<html>DartPad 创建成功",
                        "<a href=\"$dartpadUrl\" target=\"blank\">前往</a></html>",
                        NotificationType.INFORMATION,
                    )
                    .setListener(
                        NotificationListener.UrlOpeningListener(true)
                    )
                    .notify(e.project)
            } catch (exception: TokenNotAvailableException) {
                NotificationGroupManager.getInstance()
                    .getNotificationGroup("DefGroup")
                    .createNotification(
                        "Gists Token 不可用",
                        "请重新设置 Gists Token",
                        NotificationType.ERROR,
                    )
                    .addAction(SetGistsKeyAction())
                    .notify(e.project)
            } catch (exception: DartPadGenFailException) {
                NotificationGroupManager.getInstance()
                    .getNotificationGroup("DefGroup")
                    .createNotification(
                        "生成失败",
                        "${exception.message}",
                        NotificationType.ERROR,
                    )
                    .addAction(SetGistsKeyAction())
                    .notify(e.project)
            }
        }
    }
}
