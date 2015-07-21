package com.dd;

import com.intellij.icons.AllIcons;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends ProjectViewNode<String> {

    private final String mName;
    private List<AbstractTreeNode> mChildNodeList;

    protected DirectoryNode(Project project, ViewSettings viewSettings, String name) {
        super(project, name, viewSettings);
        mName = name;
        mChildNodeList = new ArrayList<AbstractTreeNode>();
    }

    @Override
    public boolean contains(@NotNull VirtualFile file) {
        for (final AbstractTreeNode childNode : mChildNodeList) {
            ProjectViewNode treeNode = (ProjectViewNode) childNode;
            if (treeNode.contains(file)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    @Override
    public List<AbstractTreeNode> getChildren() {
        return mChildNodeList;
    }

    @Override
    protected void update(PresentationData presentation) {
        presentation.setPresentableText(mName);
        presentation.setIcon(AllIcons.Nodes.Folder);
    }
}
