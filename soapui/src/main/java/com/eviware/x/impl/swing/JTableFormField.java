/*
 * SoapUI, Copyright (C) 2004-2022 SmartBear Software
 *
 * Licensed under the EUPL, Version 1.1 or - as soon as they will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

package com.eviware.x.impl.swing;

import com.eviware.x.form.XFormTextField;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class JTableFormField extends AbstractSwingXFormField<JComponent> implements XFormTextField {
    private final JScrollPane scrollPane;

    public JTableFormField(String description) {
        super(new JXTable());

        scrollPane = new JScrollPane(getTable());
        scrollPane.setPreferredSize(new Dimension(350, 200));
        getTable().setToolTipText(description);
        getTable().setHorizontalScrollEnabled(true);
    }

    public void setRequired(boolean required, String message) {
        super.setRequired(required, message);

        if (required) {
            getComponent().setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        }
        else {
            getComponent().setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        }
    }

    @Override
    public Object getProperty(String name) {
        if ("tableModel".equals(name)) {
            return getTable().getModel();
        }
        else {
            return super.getProperty(name);
        }
    }

    @Override
    public boolean isMultiRow() {
        return true;
    }

    public JXTable getTable() {
        return (JXTable)super.getComponent();
    }

    public JComponent getComponent() {
        return scrollPane;
    }

    @Override
    public void setProperty(String name, Object value) {
        if ("tableModel".equals(name)) {
            getTable().setModel((TableModel)value);
        }
        else {
            super.setProperty(name, value);
        }
    }

    public String getValue() {
        return null;
    }

    public void setValue(String value) {

    }

    public void setWidth(int columns) {
    }
}
