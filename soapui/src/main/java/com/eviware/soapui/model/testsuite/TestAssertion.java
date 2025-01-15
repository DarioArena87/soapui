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

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.testsuite.Assertable.AssertionStatus;

public interface TestAssertion extends ModelItem {
    String DISABLED_PROPERTY = TestAssertion.class.getName() + "@disabled";
    String STATUS_PROPERTY = TestAssertion.class.getName() + "@status";
    String ERRORS_PROPERTY = TestAssertion.class.getName() + "@errors";
    String CONFIGURATION_PROPERTY = TestAssertion.class.getName() + "@configuration";

    AssertionStatus getStatus();

    AssertionError[] getErrors();

    boolean isAllowMultiple();

    boolean isConfigurable();

    boolean isClonable();

    boolean configure();

    Assertable getAssertable();

    String getLabel();

    boolean isDisabled();

    void prepare(TestCaseRunner testRunner, TestCaseRunContext testRunContext) throws Exception;

    int getIndexOfAssertion(TestAssertion assertion);
}
