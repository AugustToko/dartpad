package top.geekcloud.dartpad.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import top.geekcloud.dartpad.MyBundle
import top.geekcloud.dartpad.utils.GistsCreator
import java.util.*
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class GistsKeyDialog(project: Project?) : DialogWrapper(project) {
    private val panel: JPanel
    private val input: JTextField

    init {
        title = "${MyBundle.message("displayTitle")} API Key"
        setOKButtonText("Save")
        panel = JPanel()
        input = JTextField(36)
        panel.add(input)
        init()
    }

    override fun createCenterPanel(): JComponent {
        return panel
    }

    override fun doValidate(): ValidationInfo? {
        val token = input.text
        if (token.trim().isEmpty()) {
            return ValidationInfo("Invalid api key.")
        }
        return null
    }

    public override fun doOKAction() {
        GistsCreator.setPersonKey(input.text)
        super.doOKAction()
    }

    fun promptForApiKey(): String {
        input.text = GistsCreator.getPersonKey()
        show()
        return input.text
    }
}
