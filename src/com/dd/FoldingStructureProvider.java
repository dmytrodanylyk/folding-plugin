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
    private List<AbstractTreeNode> createFoldedFiles(@NotNull Collection<AbstractTreeNode> nodeList, ViewSettings viewSettings) {
        List<AbstractTreeNode> resultList = new ArrayList<>();
        Project project = Utils.getCurrentProject();
        if (project != null) {
            List<AbstractTreeNode> otherNodeList = new ArrayList<>();
            HashSet<String> nodeTokenList = new HashSet<>();
            for (AbstractTreeNode node : nodeList) {
                if (node.getValue() instanceof PsiFile) {
                    PsiFile file = (PsiFile) node.getValue();
                    String fileName = file.getName();
                    int endIndex = fileName.indexOf(FOLDING_CHAR);
                    if (endIndex != -1) {
                        String nodeToken = fileName.substring(0, endIndex);
                        nodeTokenList.add(nodeToken);
                    } else {
                        otherNodeList.add(node);
                    }
                }
            }

            for (String nodeToken : nodeTokenList) {
                List<AbstractTreeNode> treeNodes = filterByToken(nodeList, nodeToken);
                FoldedDirectoryNode fileNode = new FoldedDirectoryNode(project, viewSettings, nodeToken);
                fileNode.getChildren().addAll(treeNodes);
                resultList.add(fileNode);
            }

            FoldedDirectoryNode fileNode = new FoldedDirectoryNode(project, viewSettings, OTHER_NODE);
            fileNode.getChildren().addAll(otherNodeList);
            resultList.add(fileNode);
        }
        return resultList;
    }

    @NotNull
    private List<AbstractTreeNode> filterByToken(Collection<AbstractTreeNode> children, String token) {
        List<AbstractTreeNode> tokenList = new ArrayList<>();
        for (AbstractTreeNode child : children) {
            if (child.getValue() instanceof PsiFile) {
                PsiFile file = (PsiFile) child.getValue();
                String fileName = file.getName();
                if (fileName.startsWith(token)) {
                    tokenList.add(child);
                }
            }
        }

        return tokenList;
    }
}
