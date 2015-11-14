package com.dd;

import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectStructureProvider implements com.intellij.ide.projectView.TreeStructureProvider {

    public static final char COMPOSE_BY_CHAR = '_';
    private static final String OTHER_NODE = "other";

    @Nullable
    @Override
    public Object getData(Collection<AbstractTreeNode> collection, String s) {
        return null;
    }

    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings viewSettings) {
        List<AbstractTreeNode> resultList = new ArrayList<>();
        if (parent.getValue() instanceof PsiDirectory) {
            PsiDirectory directory = (PsiDirectory) parent.getValue();
            String path = directory.getVirtualFile().getPath();
            if (SettingsManager.isComposed(path)) {
                resultList.addAll(createComposedFiles(children, viewSettings));
            } else {
                resultList.addAll(children);
            }
        } else {
            resultList.addAll(children);
        }

        return resultList;
    }

    @NotNull
    private List<AbstractTreeNode> createComposedFiles(@NotNull Collection<AbstractTreeNode> fileNodes, ViewSettings viewSettings) {
        List<AbstractTreeNode> resultList = new ArrayList<>();
        Project project = Utils.getCurrentProject();
        if (project != null) {
            HashSet<String> composedDirNameSet = new HashSet<>();
            List<AbstractTreeNode> notComposedFileNodes = new ArrayList<>();

            final boolean customPrefix = PropertiesComponent.getInstance().getBoolean(SettingConfigurable.PREFIX_CUSTOM_USE, false);

            Pattern pattern = customPrefix ? Pattern.compile(PropertiesComponent.getInstance().getValue(SettingConfigurable.PREFIX_PATTERN, SettingConfigurable.DEFAULT_PATTERN)) : null;

            for (AbstractTreeNode fileNode : fileNodes) {
                if (fileNode.getValue() instanceof PsiFile) {
                    PsiFile psiFile = (PsiFile) fileNode.getValue();
                    String fileName = psiFile.getName();
                    if (customPrefix) {
                        Matcher m = pattern.matcher(fileName);
                        if (m.find()) {
                            String composedDirName = m.group(0);
                            composedDirNameSet.add(composedDirName);
                        } else {
                            notComposedFileNodes.add(fileNode);
                        }
                    } else {
                        int endIndex = fileName.indexOf(COMPOSE_BY_CHAR);
                        if (endIndex != -1) {
                            String composedDirName = fileName.substring(0, endIndex);
                            composedDirNameSet.add(composedDirName);
                        } else {
                            notComposedFileNodes.add(fileNode);
                        }
                    }
                }
            }

            for (String composedDirName : composedDirNameSet) {
                List<AbstractTreeNode> composedFileNodes = filterByDirName(fileNodes, composedDirName);
                PsiFile psiFile = (PsiFile) composedFileNodes.get(0).getValue();
                DirectoryNode composedDirNode = new DirectoryNode(project, viewSettings, psiFile, composedDirName);
                composedDirNode.addAllChildren(composedFileNodes);
                resultList.add(composedDirNode);
            }

            if (!notComposedFileNodes.isEmpty()) {
                PsiFile psiFile = (PsiFile) notComposedFileNodes.get(0).getValue();
                DirectoryNode composedDirNode = new DirectoryNode(project, viewSettings, psiFile, OTHER_NODE);
                composedDirNode.addAllChildren(notComposedFileNodes);
                resultList.add(composedDirNode);
            }
        }
        return resultList;
    }

    @NotNull
    private List<AbstractTreeNode> filterByDirName(Collection<AbstractTreeNode> fileNodes, String token) {
        List<AbstractTreeNode> resultList = new ArrayList<>();

        final boolean customPrefix = PropertiesComponent.getInstance().getBoolean(SettingConfigurable.PREFIX_CUSTOM_USE, false);

        Pattern pattern = customPrefix ? Pattern.compile(PropertiesComponent.getInstance().getValue(SettingConfigurable.PREFIX_PATTERN, SettingConfigurable.DEFAULT_PATTERN)) : null;

        for (AbstractTreeNode fileNode : fileNodes) {
            if (fileNode.getValue() instanceof PsiFile) {
                PsiFile psiFile = (PsiFile) fileNode.getValue();
                String fileName = psiFile.getName();

                if (customPrefix) {
                    Matcher m = pattern.matcher(fileName);
                    if (m.find()) {

                        String composedDirName = m.group(0);
                        if (composedDirName.equals(token)) {
                            resultList.add(fileNode);
                        }
                    }
                } else {

                    int endIndex = fileName.indexOf(COMPOSE_BY_CHAR);
                    if (endIndex != -1) {
                        String composedDirName = fileName.substring(0, endIndex);
                        if (composedDirName.equals(token)) {
                            resultList.add(fileNode);
                        }
                    }
                }
            }
        }

        return resultList;
    }
}
