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

package com.eviware.soapui.model.testsuite;

import com.eviware.soapui.impl.wsdl.teststeps.assertions.TestAssertionRegistry.AssertableType;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.iface.Interface;

import java.util.List;
import java.util.Map;

/**
 * Behaviour for an object that can be asserted
 *
 * @author ole.matzura
 */

public interface Assertable {
    TestAssertion addAssertion(String selection);

    void addAssertionsListener(AssertionsListener listener);

    int getAssertionCount();

    TestAssertion getAssertionAt(int c);

    void removeAssertionsListener(AssertionsListener listener);

    void removeAssertion(TestAssertion assertion);

    AssertionStatus getAssertionStatus();

    String getAssertableContentAsXml();

    String getAssertableContent();

    String getDefaultAssertableContent();

    AssertableType getAssertableType();

    List<TestAssertion> getAssertionList();

    TestAssertion getAssertionByName(String name);

    ModelItem getModelItem();

    TestStep getTestStep();

    Interface getInterface();

    TestAssertion cloneAssertion(TestAssertion source, String name);

    Map<String, TestAssertion> getAssertions();

    TestAssertion moveAssertion(int ix, int offset);

    enum AssertionStatus {
        UNKNOWN,
        VALID,
        FAILED
    }
}
