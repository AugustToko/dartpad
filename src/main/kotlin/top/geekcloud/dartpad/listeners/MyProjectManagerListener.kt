package top.geekcloud.dartpad.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import top.geekcloud.dartpad.services.MyProjectService
import top.geekcloud.dartpad.utils.GistsCreator

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<MyProjectService>()
        if (GistsCreator.token == null) {
            GistsCreator.showTokenSetterDialog(project)
        }
    }

}
