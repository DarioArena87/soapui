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

import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContext;

public interface TestRunContext extends SubmitContext, PropertyExpansionContext {
    String LOAD_TEST_RUNNER = "LoadTestRunner";
    String THREAD_INDEX = "ThreadIndex";
    String RUN_COUNT = "RunCount";
    String TOTAL_RUN_COUNT = "TotalRunCount";
    String LOAD_TEST_CONTEXT = "LoadTestContext";
    String INTERACTIVE = "Interactive";

    String expand(String content);

    TestRunner getTestRunner();
}
