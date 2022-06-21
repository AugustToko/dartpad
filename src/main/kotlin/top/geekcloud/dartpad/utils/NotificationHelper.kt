package top.geekcloud.dartpad.utils

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object NotificationHelper {
    fun sendNotification(project: Project?, content: String, type: NotificationType = NotificationType.INFORMATION) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("DefGroup")
            .createNotification(content, type)
            .notify(project)
    }
}