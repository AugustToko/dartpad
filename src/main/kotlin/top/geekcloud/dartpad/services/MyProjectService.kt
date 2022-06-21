package top.geekcloud.dartpad.services

import com.intellij.openapi.project.Project
import top.geekcloud.dartpad.MyBundle

class MyProjectService(project: Project) {
    init {
        println(MyBundle.message("projectService", project.name))
    }
}