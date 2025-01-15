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

package com.eviware.soapui.support.components;

import javax.swing.*;
import java.util.List;

public interface JInspectorPanel {
    <T extends Inspector> T addInspector(T inspector);

    JComponent getComponent();

    void setDefaultDividerLocation(float v);

    void activate(Inspector inspector);

    void setResizeWeight(double v);

    List<Inspector> getInspectors();

    Inspector getCurrentInspector();

    void setCurrentInspector(String s);

    Inspector getInspectorByTitle(String title);

    void deactivate();

    void removeInspector(Inspector inspector);

    void setContentComponent(JComponent component);

    int getDividerLocation();

    void setDividerLocation(int i);

    Inspector getInspector(String inspectorId);

    void setInspectorVisible(Inspector inspector, boolean b);

    void setResetDividerLocation();

    void release();
}
