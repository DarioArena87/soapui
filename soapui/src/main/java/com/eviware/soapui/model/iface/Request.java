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

/**
 * Request interface
 *
 * @author Ole.Matzura
 */

public interface Request extends ModelItem {
    String REQUEST_PROPERTY = "request";
    String ENDPOINT_PROPERTY = "endpoint";
    String ENCODING_PROPERTY = "encoding";
    String MEDIA_TYPE = "mediaType";

    String getRequestContent();

    String getEndpoint();

    void setEndpoint(String string);

    String getEncoding();

    void setEncoding(String string);

    String getTimeout();

    Operation getOperation();

    void addSubmitListener(SubmitListener listener);

    void removeSubmitListener(SubmitListener listener);

    Submit submit(SubmitContext submitContext, boolean async) throws SubmitException;

    Attachment[] getAttachments();

    MessagePart[] getRequestParts();

    MessagePart[] getResponseParts();

    String getUsername();

    String getPassword();

    String getAuthType();

    boolean dependsOn(ModelItem modelItem);

    @SuppressWarnings("serial")
    class SubmitException extends Exception {
        public SubmitException(String msg) {
            super(msg);
        }

        public SubmitException(String message, Throwable cause) {
            super(message, cause);
        }

        public SubmitException(Throwable cause) {
            super(cause);
        }
    }
}
