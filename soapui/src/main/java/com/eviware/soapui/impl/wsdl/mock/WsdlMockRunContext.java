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

package com.eviware.soapui.impl.wsdl.mock;

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.mock.MockResponse;
import com.eviware.soapui.model.mock.MockRunContext;
import com.eviware.soapui.model.mock.MockRunner;
import com.eviware.soapui.model.mock.MockService;
import com.eviware.soapui.model.propertyexpansion.DefaultPropertyExpansionContext;
import com.eviware.soapui.model.propertyexpansion.PropertyExpander;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansion;
import com.eviware.soapui.model.settings.Settings;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.support.types.StringToObjectMap;
import com.eviware.soapui.support.types.StringToStringMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * MockRunContext available during dispatching of a WsdlMockRequest
 *
 * @author ole.matzura
 */

public class WsdlMockRunContext implements MockRunContext, Map<String, Object>, TestCaseRunContext, Cloneable {
    private final MockService mockService;
    private final WsdlTestRunContext context;
    private DefaultPropertyExpansionContext properties;
    private MockResponse mockResponse;

    public WsdlMockRunContext(MockService mockService, WsdlTestRunContext context) {
        this.mockService = mockService;
        this.context = context;

        reset();
    }

    public MockService getMockService() {
        return mockService;
    }

    public MockResponse getMockResponse() {
        return mockResponse;
    }

    public void setMockResponse(MockResponse mockResponse) {
        this.mockResponse = mockResponse;
    }

    public synchronized StringToStringMap toStringToStringMap() {
        synchronized (properties) {
            StringToStringMap result = new StringToStringMap();

            for (String key : properties.keySet()) {
                Object value = properties.get(key);
                if (value != null) {
                    result.put(key, value.toString());
                }
            }

            return result;
        }
    }

    public MockRunner getMockRunner() {
        return mockService.getMockRunner();
    }

    public Object getProperty(String name) {
        return get(name);
    }

    public synchronized void setProperty(String name, Object value) {
        if (context != null) {
            int ix = name.indexOf(PropertyExpansion.PROPERTY_SEPARATOR);
            if (ix > 0) {
                String teststepname = name.substring(0, ix);
                TestStep refTestStep = context.getTestCase().getTestStepByName(teststepname);
                if (refTestStep != null) {
                    TestProperty property = refTestStep.getProperty(name.substring(ix + 1));
                    if (property != null && !property.isReadOnly()) {
                        property.setValue(value.toString());
                        return;
                    }
                }
            }
        }

        synchronized (properties) {
            properties.put(name, value);
        }
    }

    public synchronized boolean hasProperty(String name) {
        synchronized (properties) {
            return properties.containsKey(name);
        }
    }

    public synchronized Object removeProperty(String name) {
        synchronized (properties) {
            return properties.remove(name);
        }
    }

    public synchronized String[] getPropertyNames() {
        synchronized (properties) {
            return properties.keySet().toArray(new String[properties.size()]);
        }
    }

    public ModelItem getModelItem() {
        return mockResponse == null ? mockService : mockResponse;
    }

    public synchronized String expand(String content) {
        synchronized (properties) {
            return PropertyExpander.expandProperties(this, content);
        }
    }

    public synchronized StringToObjectMap getProperties() {
        synchronized (properties) {
            return properties;
        }
    }

    public synchronized int hashCode() {
        synchronized (properties) {
            return properties.hashCode();
        }
    }

    public synchronized boolean equals(Object arg0) {
        synchronized (properties) {
            return properties.equals(arg0);
        }
    }

    public synchronized Object clone() {
        synchronized (properties) {
            return properties.clone();
        }
    }

    public synchronized String toString() {
        synchronized (properties) {
            return properties.toString();
        }
    }

    public synchronized int size() {
        synchronized (properties) {
            return properties.size();
        }
    }

    public synchronized boolean isEmpty() {
        synchronized (properties) {
            return properties.isEmpty();
        }
    }

    public synchronized boolean containsKey(Object arg0) {
        synchronized (properties) {
            return properties.containsKey(arg0);
        }
    }

    public synchronized boolean containsValue(Object arg0) {
        synchronized (properties) {
            return properties.containsValue(arg0);
        }
    }

    public synchronized Object get(Object arg0) {
        if ("mockService".equals(arg0)) {
            return getMockService();
        }

        if ("mockResponse".equals(arg0)) {
            return getMockResponse();
        }

        if ("modelItem".equals(arg0)) {
            return getModelItem();
        }

        if ("currentStep".equals(arg0)) {
            return getCurrentStep();
        }

        if ("currentStepIndex".equals(arg0)) {
            return getCurrentStepIndex();
        }

        if ("settings".equals(arg0)) {
            return getSettings();
        }

        if ("testCase".equals(arg0)) {
            return getTestCase();
        }

        if ("testRunner".equals(arg0)) {
            return getTestRunner();
        }

        synchronized (properties) {
            return properties.get(arg0);
        }
    }

    public synchronized Object put(String arg0, Object arg1) {
        synchronized (properties) {
            return properties.put(arg0, arg1);
        }
    }

    public synchronized Object remove(Object arg0) {
        synchronized (properties) {
            return properties.remove(arg0);
        }
    }

    public synchronized void putAll(Map<? extends String, ? extends Object> arg0) {
        synchronized (arg0) {
            synchronized (properties) {
                properties.putAll(arg0);
            }
        }
    }

    public synchronized void clear() {
        synchronized (properties) {
            properties.clear();
        }
    }

    public synchronized Set<String> keySet() {
        synchronized (properties) {
            return properties.keySet();
        }
    }

    public synchronized Collection<Object> values() {
        synchronized (properties) {
            return properties.values();
        }
    }

    public synchronized Set<Entry<String, Object>> entrySet() {
        synchronized (properties) {
            return properties.entrySet();
        }
    }

    public synchronized TestStep getCurrentStep() {
        return context == null ? null : context.getCurrentStep();
    }

    public synchronized int getCurrentStepIndex() {
        return context == null ? -1 : context.getCurrentStepIndex();
    }

    public synchronized TestCaseRunner getTestRunner() {
        return context == null ? null : context.getTestRunner();
    }

    public synchronized TestCase getTestCase() {
        return context == null ? null : context.getTestCase();
    }

    public synchronized Object getProperty(String testStep, String propertyName) {
        return context == null ? null : context.getProperty(testStep, propertyName);
    }

    public synchronized Settings getSettings() {
        return context == null ? mockService.getSettings() : context.getTestCase().getSettings();
    }

    public void reset() {
        properties = (DefaultPropertyExpansionContext)(context == null ? new DefaultPropertyExpansionContext(mockService) : context.getProperties());
    }
}
