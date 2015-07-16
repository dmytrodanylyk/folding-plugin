package com.dd;

import com.google.gson.Gson;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class SettingsManager {

    private static final String KEY_SETTINGS = "KEY_FOLDING_FOLDERS";

    public static boolean isFoldingOn(@NotNull String folder) {
        boolean isFoldingOn = false;
        Project currentProject = Utils.getCurrentProject();
        if (currentProject != null) {
            Settings settings = getSettings(currentProject);
            isFoldingOn = settings.getFoldingFolders().contains(folder);

        }

        return isFoldingOn;
    }

    public static void addFoldingFolder(@NotNull String folder) {
        Project currentProject = Utils.getCurrentProject();
        if (currentProject != null) {
            Gson gson = new Gson();
            Settings settings = getSettings(currentProject);
            settings.getFoldingFolders().add(folder);

            PropertiesComponent.getInstance(currentProject).setValue(KEY_SETTINGS, gson.toJson(settings));
        }

    }

    public static void removeFoldingFolder(@NotNull String folder) {
        Project currentProject = Utils.getCurrentProject();
        if (currentProject != null) {
            Gson gson = new Gson();
            Settings settings = getSettings(currentProject);
            settings.getFoldingFolders().remove(folder);

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
