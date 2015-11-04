package com.dd;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Created by skyrylyuk on 10/15/15.
 */
public class SettingConfigurable implements Configurable {
    public static final String PREFIX_PATTERN = "folding_plugin_prefix_pattern";
    public static final String PREFIX_CUSTOM_USE = "folding_plugin_prefix_custom_use";
    public static final String PREFIX_HIDE = "folding_plugin_prefix_hide";

    public static final String DEFAULT_PATTERN = "[^_]{1,}(?=_)";
    public static final String DEFAULT_PATTERN_DOUBLE = "[^_]{1,}_[^_]{1,}(?=_)";

    private JPanel mPanel;
    private JCheckBox useCustomPatternCheckBox;
    private JTextField customPattern;
    private JCheckBox hideFoldingPrefix;
    private boolean isModified = false;

    @Nls
    @Override
    public String getDisplayName() {
        return "Android Folding";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "null:";
    }

    @Nullable
    @Override
    public JComponent createComponent() {

        useCustomPatternCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                boolean selected = getCheckBoxStatus(actionEvent);

                customPattern.setEnabled(selected);
                isModified = true;
            }
        });

        customPattern.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isModified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isModified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isModified = true;
            }
        });

        hideFoldingPrefix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                isModified = true;
            }
        });

        reset();

        return mPanel;
    }

    private boolean getCheckBoxStatus(ActionEvent actionEvent) {
        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
        return abstractButton.getModel().isSelected();
    }


    @Override
    public boolean isModified() {

        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent.getInstance().setValue(PREFIX_CUSTOM_USE, Boolean.valueOf(useCustomPatternCheckBox.isSelected()).toString());
        PropertiesComponent.getInstance().setValue(PREFIX_PATTERN, customPattern.getText());
        PropertiesComponent.getInstance().setValue(PREFIX_HIDE, Boolean.valueOf(hideFoldingPrefix.isSelected()).toString());

        if (isModified) {
            Project currentProject = Utils.getCurrentProject();

            if (currentProject != null) {
                ProjectView.getInstance(currentProject).refresh();
            }
        }

        isModified = false;
    }

    @Override
    public void reset() {
        final boolean customPrefix = PropertiesComponent.getInstance().getBoolean(PREFIX_CUSTOM_USE, false);
        useCustomPatternCheckBox.setSelected(customPrefix);
        customPattern.setEnabled(customPrefix);
        customPattern.setText(PropertiesComponent.getInstance().getValue(PREFIX_PATTERN, DEFAULT_PATTERN_DOUBLE));
        hideFoldingPrefix.getModel().setSelected(PropertiesComponent.getInstance().getBoolean(PREFIX_HIDE, false));
    }

    @Override
    public void disposeUIResources() {
    }
}
