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

package com.eviware.soapui.support.action.swing;

import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * A simple list of actions
 *
 * @author Ole.Matzura
 */

public interface ActionList {
    int getActionCount();

    Action getActionAt(int index);

    Action getDefaultAction();

    void setDefaultAction(Action action);

    boolean hasDefaultAction();

    void performDefaultAction(ActionEvent event);

    void addAction(Action action);

    void addSeparator();

    void insertAction(Action action, int index);

    void insertSeparator(int index);

    String getLabel();

    void clear();

    void dispatchKeyEvent(KeyEvent e);

    void addActions(ActionList defaultActions);

    void removeAction(int index);
}
