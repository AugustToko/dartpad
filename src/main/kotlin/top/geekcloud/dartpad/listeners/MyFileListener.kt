package top.geekcloud.dartpad.listeners

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import top.geekcloud.dartpad.utils.log

internal class MyFileListener : FileEditorManagerListener {
    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        log.info("fileOpened: ${file.path}")
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        log.info("fileClosed: ${file.path}")
    }
}