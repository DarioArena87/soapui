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

package com.eviware.soapui.impl.wsdl.support.wss;

import com.eviware.soapui.impl.wsdl.support.wss.crypto.CryptoType;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContainer;

import javax.annotation.Nonnull;
import java.util.List;

public interface WssContainer extends PropertyExpansionContainer {
    ModelItem getModelItem();

    void addWssContainerListener(WssContainerListener listener);

    void removeWssContainerListener(WssContainerListener listener);

    List<WssCrypto> getCryptoList();

    WssCrypto addCrypto(String source, String password, @Nonnull CryptoType type);

    int getCryptoCount();

    void removeCrypto(@Nonnull WssCrypto crypto);

    List<IncomingWss> getIncomingWssList();

    IncomingWss addIncomingWss(String label);

    int getIncomingWssCount();

    IncomingWss getIncomingWssAt(int index);

    void removeIncomingWssAt(int row);

    List<OutgoingWss> getOutgoingWssList();

    OutgoingWss addOutgoingWss(String label);

    int getOutgoingWssCount();

    OutgoingWss getOutgoingWssAt(int index);

    void removeOutgoingWssAt(int row);

    WssCrypto getCryptoByName(String cryptoName);

    WssCrypto getCryptoByName(String cryptoName, boolean outgoingWSSConfig);

    OutgoingWss getOutgoingWssByName(String outgoingName);

    IncomingWss getIncomingWssByName(String incomingName);

    String[] getCryptoNames();

    String[] getOutgoingWssNames();

    String[] getIncomingWssNames();

    void importConfig(WssContainer wssContainer);
}
