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

package com.eviware.soapui.impl.wsdl.support.wsrm;

import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.support.soap.SoapVersion;

public class WsrmSequence {
    private final String identifier;
    private long lastMsgNumber;
    private String uuid;
    private SoapVersion soapVersion;
    private String wsrmNameSpace;
    private WsdlOperation operation;

    public WsrmSequence(
        String identifier, String uuid, SoapVersion soapVersion, String namespace, WsdlOperation operation
    ) {
        this.identifier = identifier;
        lastMsgNumber = 0;
        this.soapVersion = soapVersion;
        this.uuid = uuid;
        setWsrmNameSpace(namespace);
        setOperation(operation);
    }

    public String getIdentifier() {
        return identifier;
    }

    public long getLastMsgNumber() {
        return lastMsgNumber;
    }

    public long incrementLastMsgNumber() {
        lastMsgNumber++;
        return lastMsgNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public void setSoapVersion(SoapVersion soapVersion) {
        this.soapVersion = soapVersion;
    }

    public String getWsrmNameSpace() {
        return wsrmNameSpace;
    }

    public void setWsrmNameSpace(String wsrmNameSpace) {
        this.wsrmNameSpace = wsrmNameSpace;
    }

    public WsdlOperation getOperation() {
        return operation;
    }

    public void setOperation(WsdlOperation operation) {
        this.operation = operation;
    }
}
