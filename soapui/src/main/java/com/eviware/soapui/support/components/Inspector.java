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
import java.beans.PropertyChangeListener;

public interface Inspector {
    String TITLE_PROPERTY = Inspector.class.getName() + "@title";
    String ICON_PROPERTY = Inspector.class.getName() + "@icon";
    String DESCRIPTION_PROPERTY = Inspector.class.getName() + "@description";
    String ENABLED_PROPERTY = Inspector.class.getName() + "@enabled";

    String getTitle();

    ImageIcon getIcon();

    JComponent getComponent();

    String getDescription();

    boolean isEnabled();

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    String getInspectorId();

    void release();

    void activate();

    void deactivate();
}
