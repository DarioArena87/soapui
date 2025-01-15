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
import com.eviware.soapui.model.TestModelItem;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.support.types.StringToObjectMap;

import java.util.List;
import java.util.UUID;

/**
 * A TestSuite holding a number of TestCases
 *
 * @author Ole.Matzura
 */

public interface TestSuite extends TestModelItem, ResultContainer, TestRunnable {
    String RUNTYPE_PROPERTY = ModelItem.class.getName() + "@runtype";
    String DISABLED_PROPERTY = TestSuite.class.getName() + "@disabled";

    Project getProject();

    int getTestCaseCount();

    TestCase getTestCaseAt(int index);

    TestCase getTestCaseByName(String testCaseName);

    TestCase getTestCaseById(UUID id);

    List<TestCase> getTestCaseList();

    void addTestSuiteListener(TestSuiteListener listener);

    void removeTestSuiteListener(TestSuiteListener listener);

    TestSuiteRunType getRunType();

    int getIndexOfTestCase(TestCase testCase);

    boolean isDisabled();

    String getLabel();

    TestSuiteRunner run(StringToObjectMap context, boolean async);

    void addTestSuiteRunListener(TestSuiteRunListener listener);

    void removeTestSuiteRunListener(TestSuiteRunListener listener);

    enum TestSuiteRunType {
        PARALLEL,
        SEQUENTIAL
    }
}
