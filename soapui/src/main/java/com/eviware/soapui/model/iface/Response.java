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

import com.eviware.soapui.support.types.StringToStringsMap;

/**
 * Request Response behaviour
 *
 * @author Ole.Matzura
 */

public interface Response extends TypedContent {
    Request getRequest();

    String getRequestContent();

    long getTimeTaken();

    Attachment[] getAttachments();

    Attachment[] getAttachmentsForPart(String partName);

    StringToStringsMap getRequestHeaders();

    StringToStringsMap getResponseHeaders();

    long getTimestamp();

    byte[] getRawRequestData();

    byte[] getRawResponseData();

    String getContentAsXml();

    String getProperty(String name);

    void setProperty(String name, String value);

    String[] getPropertyNames();
}
