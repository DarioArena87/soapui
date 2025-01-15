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

package com.eviware.soapui.support.resolver;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.actions.project.SimpleDialog;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.model.iface.Interface;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.support.components.SimpleForm;
import com.eviware.soapui.support.resolver.ResolveContext.Resolver;
import com.eviware.soapui.support.swing.ModelItemListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ChangeOperationResolver implements Resolver {
    private boolean resolved = false;
    private final WsdlProject project;
    private Operation selectedOperation;
    private final String operationType;

    public ChangeOperationResolver(WsdlTestStep testStep, String operationType) {
        project = testStep.getTestCase().getTestSuite().getProject();

        this.operationType = operationType;
    }

    public boolean resolve() {
        PropertyChangeDialog pDialog = new PropertyChangeDialog("Resolve " + operationType);
        pDialog.setVisible(true);
        resolved = update();
        return resolved;
    }

    public boolean isResolved() {
        return resolved;
    }

    public String getResolvedPath() {
        return "";
    }

    public String getDescription() {
        return "Resolve: Select another " + operationType;
    }

    public abstract boolean update();

    protected abstract Interface[] getInterfaces(WsdlProject project);

    @Override
    public String toString() {
        return getDescription();
    }

    public Operation getSelectedOperation() {
        return selectedOperation;
    }

    @SuppressWarnings("serial")
    private class PropertyChangeDialog extends SimpleDialog {
        private JComboBox sourceStepCombo;
        private JComboBox propertiesCombo;

        public PropertyChangeDialog(String title) {
            super(title, getDescription(), null);
        }

        protected Component buildContent() {
            SimpleForm form = new SimpleForm();

            form.addSpace(5);
            Interface[] ifaces = getInterfaces(project);
            DefaultComboBoxModel sourceStepComboModel = new DefaultComboBoxModel();
            sourceStepCombo = form.appendComboBox("Interfaces", sourceStepComboModel, "Target Interface");
            sourceStepCombo.setRenderer(new ModelItemListCellRenderer());
            for (Interface element : ifaces) {
                sourceStepComboModel.addElement(element);
            }

            propertiesCombo = form.appendComboBox(operationType, ((Interface)sourceStepCombo.getSelectedItem()).getOperationList().toArray(), "Target " + operationType);
            propertiesCombo.setRenderer(new ModelItemListCellRenderer());

            sourceStepCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Interface iface = project.getInterfaceByName(((Interface)sourceStepCombo.getSelectedItem()).getName());
                    propertiesCombo.removeAllItems();
                    if (iface != null) {
                        propertiesCombo.setEnabled(true);
                        for (Operation op : iface.getOperationList()) {
                            propertiesCombo.addItem(op);
                        }
                    }
                    else {
                        propertiesCombo.setEnabled(false);
                    }
                }
            });

            form.addSpace(5);
            return form.getPanel();
        }

        protected boolean handleOk() {
            selectedOperation = (Operation)propertiesCombo.getSelectedItem();
            return true;
        }
    }
}
