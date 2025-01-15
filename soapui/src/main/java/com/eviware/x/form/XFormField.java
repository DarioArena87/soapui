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

public interface XFormField {
    String CURRENT_DIRECTORY = XFormField.class.getName() + "@currentDirectory";

    String getValue();

    void setValue(String value);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void setRequired(boolean required, String message);

    boolean isRequired();

    void setToolTip(String tooltip);

    void addFormFieldListener(XFormFieldListener listener);

    void removeFieldListener(XFormFieldListener listener);

    void addFormFieldValidator(XFormFieldValidator validator);

    void removeFormFieldValidator(XFormFieldValidator validator);

    void addComponentEnabler(XFormField tf, String value);

    void setProperty(String name, Object value);

    Object getProperty(String name);

    ValidationMessage[] validate();

    boolean isVisible();
}
