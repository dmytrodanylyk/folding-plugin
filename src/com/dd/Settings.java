package com.dd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    @SerializedName("DecomposedFolders")
    private List<String> mComposedFolders;

    @NotNull
    public List<String> getComposedFolders() {
        if(mComposedFolders == null) {
            mComposedFolders = new ArrayList<String>();
        }

        return mComposedFolders;
    }
}
