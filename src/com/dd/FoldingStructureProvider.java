package com.dd;

import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class FoldingStructureProvider implements com.intellij.ide.projectView.TreeStructureProvider {

    public List<AbstractTreeNode> filterByToken(Collection<AbstractTreeNode> children, String token) {
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

    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(AbstractTreeNode parent, Collection<AbstractTreeNode> children, ViewSettings viewSettings) {
//        System.out.println("===================================");
//        System.out.println("parent.getName " + parent.getValue());
//        System.out.println("children " + children.size());
        List<AbstractTreeNode> resultList = new ArrayList<>();
        Project project = ProjectManager.getInstance().getOpenProjects()[0];

        HashSet<String> nodeTokenList = new HashSet<>();

        if (parent.getValue() instanceof PsiDirectory) {
            PsiDirectory directory = (PsiDirectory) parent.getValue() ;
            String path = directory.getVirtualFile().getPath();
            System.out.println("modify directory " + path);
            if(SettingsManager.isFoldingOn(path)) {
                for (AbstractTreeNode child : children) {
                    if (child.getValue() instanceof PsiFile) {
                        PsiFile file = (PsiFile) child.getValue();
                        String fileName = file.getName();
                        int endIndex = fileName.indexOf('_');
                        if (endIndex != -1) {
                            String nodeToken = fileName.substring(0, endIndex);
                            nodeTokenList.add(nodeToken);
                        }
                    }
                }

                for (String nodeToken : nodeTokenList) {
                    List<AbstractTreeNode> treeNodes = filterByToken(children, nodeToken);
                    MyFileNode fileNode = new MyFileNode(project, viewSettings, nodeToken);
                    fileNode.getChildren().addAll(treeNodes);
                    resultList.add(fileNode);
                }
            } else {
                resultList.addAll(children);
            }
        } else {
            resultList.addAll(children);
        }

        return resultList;
    }

    @Nullable
    @Override
    public Object getData(Collection<AbstractTreeNode> collection, String s) {
        return null;
    }
}
