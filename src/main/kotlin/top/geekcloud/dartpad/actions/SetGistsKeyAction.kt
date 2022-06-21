package top.geekcloud.dartpad.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import top.geekcloud.dartpad.utils.GistsCreator

class SetGistsKeyAction: AnAction("SetGistsKey") {
    override fun update(e: AnActionEvent) {
        // ignored
    }

    override fun actionPerformed(e: AnActionEvent) {
        GistsCreator.showTokenSetterDialog(e.project)
    }
}