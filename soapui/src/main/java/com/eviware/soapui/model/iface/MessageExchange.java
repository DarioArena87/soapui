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

package com.eviware.soapui.model.iface;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.testsuite.ResultContainer;
import com.eviware.soapui.support.types.StringToStringMap;
import com.eviware.soapui.support.types.StringToStringsMap;

/**
 * An exchange of a request and response message
 *
 * @author ole.matzura
 */

public interface MessageExchange extends ResultContainer {
    Operation getOperation();

    ModelItem getModelItem();

    long getTimestamp();

    long getTimeTaken();

    String getEndpoint();

    StringToStringMap getProperties();

    String getRequestContent();

    String getResponseContent();

    String getRequestContentAsXml();

    String getResponseContentAsXml();

    StringToStringsMap getRequestHeaders();

    StringToStringsMap getResponseHeaders();

    Attachment[] getRequestAttachments();

    Attachment[] getResponseAttachments();

    String[] getMessages();

    boolean isDiscarded();

    boolean hasRawData();

    byte[] getRawRequestData();

    byte[] getRawResponseData();

    Attachment[] getRequestAttachmentsForPart(String partName);

    Attachment[] getResponseAttachmentsForPart(String partName);

    boolean hasRequest(boolean ignoreEmpty);

    boolean hasResponse();

    Response getResponse();

    String getProperty(String name);
}
