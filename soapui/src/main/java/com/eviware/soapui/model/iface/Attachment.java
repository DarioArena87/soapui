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

import java.io.InputStream;

/**
 * Attachment for Requests and their responses
 *
 * @author Ole.Matzura
 */

public interface Attachment {
    String getName();

    String getContentType();

    void setContentType(String contentType);

    long getSize();

    String getPart();

    void setPart(String part);

    InputStream getInputStream() throws Exception;

    String getUrl();

    boolean isCached();

    AttachmentType getAttachmentType();

    String getContentID();

    AttachmentEncoding getEncoding();

    String getContentEncoding();

    String getId();

    enum AttachmentType {
        MIME,
        XOP,
        CONTENT,
        SWAREF,
        UNKNOWN
    }

    enum AttachmentEncoding {
        BASE64,
        HEX,
        NONE
    }
}
