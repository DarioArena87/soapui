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

package com.eviware.soapui.monitor;

import com.eviware.soapui.model.mock.MockRunner;
import com.eviware.soapui.model.testsuite.LoadTestRunner;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.security.SecurityTestRunner;

/**
 * Listener for TestMonitor events
 *
 * @author Ole.Matzura
 */

public interface TestMonitorListener {
    void loadTestStarted(LoadTestRunner runner);

    void loadTestFinished(LoadTestRunner runner);

    void securityTestStarted(SecurityTestRunner runner);

    void securityTestFinished(SecurityTestRunner runner);

    void testCaseStarted(TestCaseRunner runner);

    void testCaseFinished(TestCaseRunner runner);

    void mockServiceStarted(MockRunner runner);

    void mockServiceStopped(MockRunner runner);
}
