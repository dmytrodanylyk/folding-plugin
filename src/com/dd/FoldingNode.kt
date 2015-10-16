package com.dd

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class FoldingNode(project: Project, value: PsiFile, viewSettings: ViewSettings, private val mName: String) : PsiFileNode(project, value, viewSettings) {

    override fun update(presentationData: PresentationData) {
        super.update(presentationData)


        presentationData.presentableText = mName
    }
}
