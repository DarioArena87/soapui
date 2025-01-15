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

import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.model.TestModelItem;
import com.eviware.soapui.security.SecurityTest;
import com.eviware.soapui.support.types.StringToObjectMap;

import java.util.List;
import java.util.UUID;

/**
 * A TestCase holding a number of TestSteps
 *
 * @author Ole.Matzura
 */

public interface TestCase extends TestModelItem, ResultContainer, TestRunnable {
    String STATUS_PROPERTY = TestCase.class.getName() + "@status";
    String DISABLED_PROPERTY = TestCase.class.getName() + "@disabled";

    TestSuite getTestSuite();

    TestStep getTestStepAt(int index);

    int getIndexOfTestStep(TestStep testStep);

    int getTestStepCount();

    List<TestStep> getTestStepList();

    LoadTest getLoadTestAt(int index);

    LoadTest getLoadTestByName(String loadTestName);

    int getIndexOfLoadTest(LoadTest loadTest);

    int getLoadTestCount();

    List<LoadTest> getLoadTestList();

    TestCaseRunner run(StringToObjectMap context, boolean async);

    void addTestRunListener(TestRunListener listener);

    void removeTestRunListener(TestRunListener listener);

    int getTestStepIndexByName(String stepName);

    <T extends TestStep> T findPreviousStepOfType(TestStep referenceStep, Class<T> stepClass);

    <T extends TestStep> T findNextStepOfType(TestStep referenceStep, Class<T> stepClass);

    <T extends TestStep> List<T> getTestStepsOfType(Class<T> stepType);

    void moveTestStep(int index, int offset);

    TestStep getTestStepByName(String stepName);

    TestStep getTestStepById(UUID testStepId);

    boolean isDisabled();

    String getLabel();

    SecurityTest getSecurityTestAt(int index);

    SecurityTest getSecurityTestByName(String securityTestName);

    int getIndexOfSecurityTest(SecurityTest securityTest);

    int getSecurityTestCount();

    List<SecurityTest> getSecurityTestList();

    TestStep insertTestStep(TestStepConfig config, int position);
}
