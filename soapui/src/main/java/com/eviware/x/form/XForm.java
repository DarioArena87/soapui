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

import com.eviware.soapui.model.iface.Interface;
import com.eviware.soapui.support.types.StringToStringMap;

public interface XForm {
    XFormTextField addTextField(String name, String description, FieldType type);

    XFormField addCheckBox(String name, String description);

    XFormOptionsField addComboBox(String name, Object[] values, String description);

    void setOptions(String name, Object[] values);

    void addSeparator(String label);

    XFormField addComponent(String name, XFormField component);

    StringToStringMap getValues();

    void setValues(StringToStringMap values);

    String getComponentValue(String name);

    XFormField getComponent(String name);

    String getName();

    void setName(String name);

    XFormField addNameSpaceTable(String label, Interface modelItem);

    void addLabel(String name, String label);

    XFormField[] getFormFields();

    void setFormFieldProperty(String name, Object value);

    void addSeparator();

    Object[] getOptions(String name);

    XFormField getFormField(String name);

    enum FieldType {
        TEXT,
        FOLDER,
        FILE,
        FILE_OR_FOLDER,
        URL,
        JAVA_PACKAGE,
        JAVA_CLASS,
        PASSWORD,
        PROJECT_FILE,
        PROJECT_FOLDER,
        TEXTAREA
    }

    enum ToolkitType {
        SWING,
        SWT
    }
}
