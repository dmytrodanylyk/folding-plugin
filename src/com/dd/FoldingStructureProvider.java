package com.dd;

import com.intellij.ide.projectView.ViewSettings;
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

public class FoldingStructureProvider implements com.intellij.ide.projectView.TreeStructureProvider {

    private static final char FOLDING_CHAR = '_';
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
            if (SettingsManager.isFoldingOn(path)) {
                resultList.addAll(createFoldedFiles(children, viewSettings));
            } else {
                resultList.addAll(children);
            }
        } else {
            resultList.addAll(children);
        }

        return resultList;
    }

    @NotNull
    private List<AbstractTreeNode> createFoldedFiles(@NotNull Collection<AbstractTreeNode> fileNodes, ViewSettings viewSettings) {
        List<AbstractTreeNode> resultList = new ArrayList<>();
        Project project = Utils.getCurrentProject();
        if (project != null) {
            HashSet<String> foldedDirNameSet = new HashSet<>();
            List<AbstractTreeNode> notFoldedFileNodes = new ArrayList<>();

            for (AbstractTreeNode fileNode : fileNodes) {
                if (fileNode.getValue() instanceof PsiFile) {
                    PsiFile psiFile = (PsiFile) fileNode.getValue();
                    String fileName = psiFile.getName();
                    int endIndex = fileName.indexOf(FOLDING_CHAR);
                    if (endIndex != -1) {
                        String foldedDirName = fileName.substring(0, endIndex);
                        foldedDirNameSet.add(foldedDirName);
                    } else {
                        notFoldedFileNodes.add(fileNode);
                    }
                }
            }

            for (String foldedDirName : foldedDirNameSet) {
                FoldedDirectoryNode foldedDirNode = new FoldedDirectoryNode(project, viewSettings, foldedDirName);
                List<AbstractTreeNode> foldedFileNodes = filterByDirName(fileNodes, foldedDirName);
                foldedDirNode.getChildren().addAll(foldedFileNodes);
                resultList.add(foldedDirNode);
            }

            if(!notFoldedFileNodes.isEmpty()) {
                FoldedDirectoryNode foldedDirNode = new FoldedDirectoryNode(project, viewSettings, OTHER_NODE);
                foldedDirNode.getChildren().addAll(notFoldedFileNodes);
                resultList.add(foldedDirNode);
            }
        }
        return resultList;
    }

    @NotNull
    private List<AbstractTreeNode> filterByDirName(Collection<AbstractTreeNode> fileNodes, String token) {
        List<AbstractTreeNode> resultList = new ArrayList<>();
        for (AbstractTreeNode fileNode : fileNodes) {
            if (fileNode.getValue() instanceof PsiFile) {
                PsiFile psiFile = (PsiFile) fileNode.getValue();
                String fileName = psiFile.getName();
                int endIndex = fileName.indexOf(FOLDING_CHAR);
                if (endIndex != -1) {
                    String foldedDirName = fileName.substring(0, endIndex);
                    if(foldedDirName.equals(token)) {
                        resultList.add(fileNode);
                    }
                }
            }
        }

        return resultList;
    }
}
