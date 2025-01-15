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

package com.eviware.soapui.impl.wsdl;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.iface.Attachment;
import com.eviware.soapui.model.iface.MessagePart.AttachmentPart;

import java.beans.PropertyChangeListener;

/**
 * Behaviour for ModelItems that contain attachments (Requests and
 * MockResponses)
 *
 * @author ole.matzura
 */

public interface AttachmentContainer {
    String ATTACHMENTS_PROPERTY = WsdlRequest.class.getName() + "@attachments";

    int getAttachmentCount();

    Attachment getAttachmentAt(int index);

    Attachment[] getAttachmentsForPart(String partName);

    Attachment[] getAttachments();

    AttachmentPart[] getDefinedAttachmentParts();

    AttachmentPart getAttachmentPart(String partName);

    void addAttachmentsChangeListener(PropertyChangeListener listener);

    void removeAttachmentsChangeListener(PropertyChangeListener listener);

    boolean isMultipartEnabled();

    /**
     * Returns ModelItem associated with this container
     */

    ModelItem getModelItem();
}
