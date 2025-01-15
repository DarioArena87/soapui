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

package com.eviware.soapui.impl.rest.support;

import com.eviware.soapui.impl.rest.actions.support.NewRestResourceActionBase;
import com.eviware.soapui.impl.wsdl.MutableTestPropertyHolder;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansion;
import com.eviware.soapui.model.testsuite.TestProperty;

import java.util.Map;
import java.util.Properties;

public interface RestParamsPropertyHolder extends MutableTestPropertyHolder, Map<String, TestProperty> {

    RestParamProperty getProperty(String name);

    RestParamProperty getPropertyAt(int index);

    void resetValues();

    int getPropertyIndex(String name);

    void saveTo(Properties props);

    PropertyExpansion[] getPropertyExpansions();

    void setPropertiesLabel(String propertiesLabel);

    RestParamProperty addProperty(String name);

    RestParamProperty removeProperty(String propertyName);

    RestParamProperty get(Object key);

    void addParameter(RestParamProperty prop);

    void setParameterLocation(RestParamProperty parameter, NewRestResourceActionBase.ParamLocation newLocation);

    /**
     * Internal property class
     *
     * @author ole
     */

    enum ParameterStyle {
        MATRIX,
        HEADER,
        QUERY,
        TEMPLATE,
        PLAIN
    }
}
