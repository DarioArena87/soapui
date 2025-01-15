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

package com.eviware.soapui.impl.wsdl.support;

import com.eviware.soapui.config.AttachmentConfig;
import com.eviware.soapui.model.iface.Attachment;
import org.apache.xmlbeans.XmlObject;

import java.io.File;
import java.io.IOException;

/**
 * WSDL-specific Attachment behaviour
 *
 * @author ole.matzura
 */

public interface WsdlAttachment extends Attachment {
    void updateConfig(AttachmentConfig config);

    XmlObject getConfig();

    void setContentID(String contentID);

    void reload(File file, boolean cache) throws IOException;

    void setName(String value);

    void setUrl(String string);
}
