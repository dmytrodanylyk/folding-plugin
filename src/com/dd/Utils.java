package com.dd;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.Nullable;

public final class Utils {

    @Nullable
    public static Project getCurrentProject() {
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        return openProjects.length > 0 ? openProjects[0] : null;
    }
}
