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

import com.eviware.soapui.config.WsrmConfigConfig;
import com.eviware.soapui.config.WsrmVersionTypeConfig;
import com.eviware.soapui.support.PropertyChangeNotifier;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigInteger;

public class WsrmConfig implements PropertyChangeNotifier {

    private final WsrmContainer container;
    private WsrmConfigConfig wsrmConfig;
    private String sequenceIdentifier;
    private Long lastMessageId;
    private String uuid;
    private PropertyChangeSupport propertyChangeSupport;

    public WsrmConfig(WsrmConfigConfig wsrmConfig, WsrmContainer container) {
        setWsrmConfig(wsrmConfig);
        this.container = container;
        setPropertyChangeSupport(new PropertyChangeSupport(this));
        lastMessageId = 1L;

        if (!wsrmConfig.isSetVersion()) {
            wsrmConfig.setVersion(WsrmVersionTypeConfig.X_1_2);
        }
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public WsrmConfigConfig getWsrmConfig() {
        return wsrmConfig;
    }

    public void setWsrmConfig(WsrmConfigConfig wsrmConfig) {
        this.wsrmConfig = wsrmConfig;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
        this.propertyChangeSupport = propertyChangeSupport;
    }

    public WsrmContainer getContainer() {
        return container;
    }

    public String getAckTo() {
        return wsrmConfig.getAckTo();
    }

    public void setAckTo(String newAckTo) {
        String oldValue = wsrmConfig.getAckTo();
        wsrmConfig.setAckTo(newAckTo);
        propertyChangeSupport.firePropertyChange("ackTo", oldValue, newAckTo);
    }

    public String getOfferEndpoint() {
        return wsrmConfig.getOfferEndpoint();
    }

    public void setOfferEndpoint(String endpointUri) {
        String oldValue = wsrmConfig.getOfferEndpoint();
        wsrmConfig.setOfferEndpoint(endpointUri);
        propertyChangeSupport.firePropertyChange("offerEndpoint", oldValue, endpointUri);
    }

    public BigInteger getSequenceExpires() {
        return wsrmConfig.getSequenceExpires();
    }

    public void setSequenceExpires(BigInteger newTimeout) {
        BigInteger oldValue = wsrmConfig.getSequenceExpires();
        wsrmConfig.setSequenceExpires(newTimeout);
        propertyChangeSupport.firePropertyChange("sequenceExpires", oldValue, newTimeout);
    }

    public boolean isWsrmEnabled() {
        return container.isWsrmEnabled();
    }

    public void setWsrmEnabled(boolean enable) {
        boolean oldValue = isWsrmEnabled();
        container.setWsrmEnabled(enable);
        propertyChangeSupport.firePropertyChange("wsrmEnabled", oldValue, enable);
    }

    public String getVersion() {
        return wsrmConfig.getVersion().toString();
    }

    public void setVersion(String arg0) {
        String oldValue = getVersion();
        wsrmConfig.setVersion(WsrmVersionTypeConfig.Enum.forString(arg0));
        propertyChangeSupport.firePropertyChange("version", oldValue, arg0);
    }

    public String getSequenceIdentifier() {
        return sequenceIdentifier;
    }

    public void setSequenceIdentifier(String sequenceIdentifier) {
        this.sequenceIdentifier = sequenceIdentifier;
    }

    public Long nextMessageId() {
        lastMessageId++;
        return lastMessageId;
    }

    public Long getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(long msgId) {
        lastMessageId = msgId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersionNameSpace() {
        return WsrmUtils.getWsrmVersionNamespace(wsrmConfig.getVersion());
    }
}
