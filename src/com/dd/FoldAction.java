package com.dd;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiDirectory;

public class FoldAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        Object navigatable = actionEvent.getData(CommonDataKeys.NAVIGATABLE);
        if(navigatable instanceof PsiDirectory) {
            PsiDirectory directory = (PsiDirectory) navigatable;

            String path = directory.getVirtualFile().getPath();

            if (SettingsManager.isFoldingOn(path)) {
                SettingsManager.removeFoldingFolder(path);
            } else {
                SettingsManager.addFoldingFolder(path);
            }

            Project project = ProjectManager.getInstance().getOpenProjects()[0];
            ProjectView.getInstance(project).refresh();
        }

    }

    @Override
    public void update(AnActionEvent actionEvent) {
        final Project project = actionEvent.getData(CommonDataKeys.PROJECT);
        if (project != null) {
            Object navigatable = actionEvent.getData(CommonDataKeys.NAVIGATABLE);
            actionEvent.getPresentation().setEnabledAndVisible(navigatable instanceof PsiDirectory);
        }
    }
}
