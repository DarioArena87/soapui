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

package com.eviware.soapui.impl.wsdl.support.soap;

import com.eviware.soapui.support.StringUtils;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlValidationError;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.List;

/**
 * Public behaviour for a SOAP Version
 *
 * @author ole.matzura
 */

public interface SoapVersion {
    SoapVersion11 Soap11 = SoapVersion11.instance;
    SoapVersion12 Soap12 = SoapVersion12.instance;

    QName getEnvelopeQName();

    QName getBodyQName();

    QName getHeaderQName();

    void validateSoapEnvelope(String soapMessage, List<XmlError> errors);

    String getContentTypeHttpHeader(String encoding, String soapAction);

    String getEnvelopeNamespace();

    String getFaultDetailNamespace();

    String getEncodingNamespace();

    XmlObject getSoapEncodingSchema() throws XmlException, IOException;

    XmlObject getSoapEnvelopeSchema() throws XmlException, IOException;

    /**
     * Checks if the specified validation error should be ignored for a message
     * with this SOAP version. (The SOAP-spec may allow some constructions not
     * allowed by the corresponding XML-Schema)
     */

    boolean shouldIgnore(XmlValidationError xmlError);

    String getContentType();

    SchemaType getEnvelopeType();

    SchemaType getFaultType();

    String getName();

    String getSoapActionHeader(String soapAction);

    /**
     * Utilities
     *
     * @author ole.matzura
     */

    class Utils {
        public static SoapVersion getSoapVersionForContentType(String contentType, SoapVersion def) {
            if (StringUtils.isNullOrEmpty(contentType)) {
                return def;
            }

            SoapVersion soapVersion = contentType.startsWith(Soap11.getContentType()) ? Soap11 : null;
            soapVersion = soapVersion == null && contentType.startsWith(Soap12.getContentType()) ? Soap12 : soapVersion;

            return soapVersion == null ? def : soapVersion;
        }
    }
}
