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

package com.eviware.soapui.model.support;

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.propertyexpansion.DefaultPropertyExpansionContext;
import com.eviware.soapui.model.propertyexpansion.PropertyExpander;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.support.types.StringToObjectMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Base-class for submit contexts
 *
 * @author ole.matzura
 */

public abstract class AbstractSubmitContext<T extends ModelItem> implements SubmitContext, Map<String, Object> {
    private final T modelItem;
    private DefaultPropertyExpansionContext properties;

    public AbstractSubmitContext(T modelItem) {
        this.modelItem = modelItem;
        properties = new DefaultPropertyExpansionContext(modelItem);

        setProperty(TestCaseRunContext.RUN_COUNT, 0);
        setProperty(TestCaseRunContext.THREAD_INDEX, 0);
    }

    public AbstractSubmitContext(T modelItem, StringToObjectMap properties) {
        this(modelItem);

        if (properties != null && properties.size() > 0) {
            if (this.properties == null) {
                this.properties = new DefaultPropertyExpansionContext(modelItem);
            }

            this.properties.putAll(properties);
        }
    }

    public Object getProperty(String name, TestStep testStep, WsdlTestCase testCase) {
        if (properties != null && properties.containsKey(name)) {
            return properties.get(name);
        }

        if (testCase != null) {
            int ix = name.indexOf(PROPERTY_SEPARATOR);
            if (ix > 0) {
                String teststepname = name.substring(0, ix);
                TestStep refTestStep = testCase.getTestStepByName(teststepname);
                if (refTestStep != null) {
                    TestProperty property = refTestStep.getProperty(name.substring(ix + 1));
                    return property == null ? null : property.getValue();
                }
            }

            if (testCase.getSearchProperties()) {
                ix = testStep == null ? testCase.getTestStepCount() - 1 : testCase.getIndexOfTestStep(testStep);
                if (ix >= testCase.getTestStepCount()) {
                    ix = testCase.getTestStepCount() - 1;
                }

                while (ix >= 0) {
                    TestProperty property = testCase.getTestStepAt(ix).getProperty(name);
                    if (property != null) {
                        return property.getValue();
                    }

                    ix--;
                }
            }
        }

        return null;
    }

    public void setProperty(String name, Object value) {
        if (properties == null) {
            properties = new DefaultPropertyExpansionContext(modelItem);
        }

        properties.put(name, value);
    }

    public boolean hasProperty(String name) {
        return properties != null && properties.containsKey(name);
    }

    public Object removeProperty(String name) {
        return properties == null ? null : properties.remove(name);
    }

    public String[] getPropertyNames() {
        return properties.keySet().toArray(new String[properties.size()]);
    }

    public T getModelItem() {
        return modelItem;
    }

    public String expand(String content) {
        return PropertyExpander.expandProperties(this, content);
    }

    public StringToObjectMap getProperties() {
        return properties;
    }

    public void setProperty(String name, Object value, TestCase testCase) {
        int ix = name.indexOf(PROPERTY_SEPARATOR);
        if (ix > 0) {
            String teststepname = name.substring(0, ix);
            TestStep refTestStep = testCase.getTestStepByName(teststepname);
            if (refTestStep != null) {
                TestProperty property = refTestStep.getProperty(name.substring(ix + 1));
                if (property != null && !property.isReadOnly()) {
                    property.setValue(value.toString());
                    return;
                }
            }
        }

        if (properties == null) {
            properties = new DefaultPropertyExpansionContext(modelItem);
        }

        properties.put(name, value);
    }

    public void resetProperties() {
        if (properties != null) {
            properties.clear();
        }
    }

    public int hashCode() {
        return properties.hashCode();
    }

    public boolean equals(Object o) {
        return properties.equals(o);
    }

    public Object clone() {
        return properties.clone();
    }

    public String toString() {
        return properties.toString();
    }

    public int size() {
        return properties.size();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return properties.containsValue(value);
    }

    public Object get(Object key) {
        return properties.get(key);
    }

    public Object put(String key, Object value) {
        return properties.put(key, value);
    }

    public Object remove(Object key) {
        return properties.remove(key);
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        properties.putAll(m);
    }

    public void clear() {
        properties.clear();
    }

    public Set<String> keySet() {
        return properties.keySet();
    }

    public Collection<Object> values() {
        return properties.values();
    }

    public Set<Entry<String, Object>> entrySet() {
        return properties.entrySet();
    }
}
