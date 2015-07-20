package com.dd;

import com.google.gson.Gson;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class SettingsManager {

    private static final String KEY_SETTINGS = "KEY_COMPOSED_FOLDERS";

    public static boolean isComposed(@NotNull String folder) {
        boolean isFoldingOn = false;
        Project currentProject = Utils.getCurrentProject();
        if (currentProject != null) {
            Settings settings = getSettings(currentProject);
            isFoldingOn = settings.getComposedFolders().contains(folder);

        }

        return isFoldingOn;
    }

    public static void addComposedFolder(@NotNull String folder) {
        Project currentProject = Utils.getCurrentProject();
        if (currentProject != null) {
            Gson gson = new Gson();
            Settings settings = getSettings(currentProject);
            settings.getComposedFolders().add(folder);

            PropertiesComponent.getInstance(currentProject).setValue(KEY_SETTINGS, gson.toJson(settings));
        }

    }

    public static void removeComposedFolder(@NotNull String folder) {
        Project currentProject = Utils.getCurrentProject();
        if (currentProject != null) {
            Gson gson = new Gson();
            Settings settings = getSettings(currentProject);
            settings.getComposedFolders().remove(folder);

            PropertiesComponent.getInstance(currentProject).setValue(KEY_SETTINGS, gson.toJson(settings));
        }
    }

    @NotNull
    private static Settings getSettings(@NotNull Project currentProject) {
        Gson gson = new Gson();
        String json = PropertiesComponent.getInstance(currentProject).getValue(KEY_SETTINGS);
        Settings settings;
        if (json == null) {
            settings = new Settings();
        } else {
            settings = gson.fromJson(json, Settings.class);
        }

        return settings;
    }
}
