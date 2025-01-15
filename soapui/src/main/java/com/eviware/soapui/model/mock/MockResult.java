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

import com.eviware.soapui.support.action.swing.ActionList;
import com.eviware.soapui.support.types.StringToStringsMap;

import java.io.IOException;

/**
 * Resulting MessageExchange for a request to a MockService
 *
 * @author ole.matzura
 */

public interface MockResult {
    MockRequest getMockRequest();

    StringToStringsMap getResponseHeaders();

    String getResponseContent();

    void setResponseContent(String responseContent);

    MockResponse getMockResponse();

    MockOperation getMockOperation();

    ActionList getActions();

    long getTimeTaken();

    long getTimestamp();

    void finish();

    byte[] getRawResponseData();

    void addHeader(String name, String value);

    boolean isCommitted();

    void setContentType(String contentTypeHttpHeader);

    void writeRawResponseData(byte[] data) throws IOException;
}
