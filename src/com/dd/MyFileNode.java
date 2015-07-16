package com.dd;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyFileNode extends ProjectViewNode<MyFile> {


    private final String mName;
    List<AbstractTreeNode> mCollection;

    protected MyFileNode(Project project, ViewSettings viewSettings, String name) {
        super(project, new MyFile(), viewSettings);
        mName = name;
        mCollection = new ArrayList<>();
    }

    @Override
    public boolean contains(VirtualFile file) {
        for (final AbstractTreeNode aMyChildren : mCollection) {
            ProjectViewNode treeNode = (ProjectViewNode)aMyChildren;
            if (treeNode.contains(file)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    @Override
    public List<AbstractTreeNode> getChildren() {
        return mCollection;
    }

    @Override
    protected void update(PresentationData presentation) {
        presentation.setPresentableText(mName);
    }
}
