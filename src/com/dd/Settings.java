package com.dd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    @SerializedName("FoldingFolders")
    private List<String> mFoldingFolders;

    @NotNull
    public List<String> getFoldingFolders() {
        if(mFoldingFolders == null) {
            mFoldingFolders = new ArrayList<>();
        }

        return mFoldingFolders;
    }

    public void setFoldingFolders(List<String> foldingFolders) {
        mFoldingFolders = foldingFolders;
    }
}
