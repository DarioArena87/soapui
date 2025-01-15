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

package com.eviware.soapui.model.mock;

import com.eviware.soapui.impl.wsdl.mock.WsdlMockRunContext;

public interface MockServiceScripts {
    Object runStartScript(WsdlMockRunContext mockContext, MockRunner wsdlMockRunner) throws Exception;

    String getStartScript();

    void setStartScript(String script);

    String getStopScript();

    void setStopScript(String script);

    Object runStopScript(WsdlMockRunContext mockContext, MockRunner mockRunner) throws Exception;

    String getOnRequestScript();

    void setOnRequestScript(String text);

    Object runOnRequestScript(WsdlMockRunContext context, MockRequest request) throws Exception;

    String getAfterRequestScript();

    void setAfterRequestScript(String text);

    Object runAfterRequestScript(WsdlMockRunContext context, MockResult request) throws Exception;
}
