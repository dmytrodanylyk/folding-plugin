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

package com.dd

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * @author skyrylyuk
 */
class FoldingConfigurable : Configurable {


    private val mPanel = JPanel()
    private val mHolderName = JTextField()
    private val mPrefix: JTextField? = null

    @Nls
    override fun getDisplayName(): String {
        return "Android Folding"
    }

    override fun getHelpTopic(): String? {
        return null
    }

    override fun createComponent(): JComponent? {
        reset()
        mHolderName.text = "Hello"
        mPanel.add(mHolderName)
        return mPanel
    }

    override fun isModified(): Boolean {
        return true
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        //    PropertiesComponent.getInstance().setValue(PREFIX, mPrefix.getText());
        //    PropertiesComponent.getInstance().setValue(VIEWHOLDER_CLASS_NAME, mHolderName.getText());
    }

    override fun reset() {
        //    mPrefix.setText(Utils.getPrefix());
        //    mHolderName.setText(Utils.getViewHolderClassName());
    }

    override fun disposeUIResources() {

    }
}
