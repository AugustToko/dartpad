package top.geekcloud.dartpad.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.VirtualFile

object FileTypeHelper {
    fun getFileByAnActionEvent(e: AnActionEvent): VirtualFile? {
        val currentDoc: Document = FileEditorManager.getInstance(e.project!!).selectedTextEditor!!.document
        return FileDocumentManager.getInstance().getFile(currentDoc)
    }

    fun getDocByAnActionEvent(e: AnActionEvent): Document? {
        return FileEditorManager.getInstance(e.project!!).selectedTextEditor?.document
    }
}