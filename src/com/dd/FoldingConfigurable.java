/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2014 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.dd;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author skyrylyuk
 */
public class FoldingConfigurable implements Configurable {


    private JPanel mPanel = new JPanel();
    private JTextField mHolderName = new JTextField();
    private JTextField mPrefix;

    @Nls
    @Override
    public String getDisplayName() {
        return "Android Folding";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        reset();
        mHolderName.setText("Hello");
        mPanel.add(mHolderName);
        return mPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
//    PropertiesComponent.getInstance().setValue(PREFIX, mPrefix.getText());
//    PropertiesComponent.getInstance().setValue(VIEWHOLDER_CLASS_NAME, mHolderName.getText());
    }

    @Override
    public void reset() {
//    mPrefix.setText(Utils.getPrefix());
//    mHolderName.setText(Utils.getViewHolderClassName());
    }

    @Override
    public void disposeUIResources() {

    }
}
