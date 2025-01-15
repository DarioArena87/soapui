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

package com.eviware.x.form;

import com.eviware.soapui.support.action.swing.ActionList;
import com.eviware.soapui.support.types.StringToStringMap;

import javax.swing.*;

public interface XFormDialog {
    int OK_OPTION = 1;
    int CANCEL_OPTION = 2;

    StringToStringMap getValues();

    void setValues(StringToStringMap values);

    void setVisible(boolean visible);

    int getReturnValue();

    void setValue(String field, String value);

    String getValue(String field);

    boolean show();

    StringToStringMap show(StringToStringMap values);

    boolean validate();

    void setOptions(String field, Object[] options);

    XFormField getFormField(String name);

    void setFormFieldProperty(String name, Object value);

    int getValueIndex(String name);

    int getIntValue(String name, int defaultValue);

    boolean getBooleanValue(String name);

    void setBooleanValue(String name, boolean b);

    void setIntValue(String name, int value);

    void setWidth(int i);

    void release();

    void addAction(Action action);

    XForm[] getForms();

    void setSize(int i, int j);

    ActionList getActionsList();
}
