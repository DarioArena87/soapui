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

package com.eviware.soapui.support.editor.inspectors.amfheader;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.support.editor.inspectors.AbstractXmlInspector;
import com.eviware.soapui.support.types.StringToStringMap;
import org.apache.commons.lang.NotImplementedException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public interface AMFHeadersInspectorModel {
    StringToStringMap getHeaders();

    void setHeaders(StringToStringMap headers);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    boolean isReadOnly();

    void release();

    void setInspector(AbstractXmlInspector inspector);

    abstract class AbstractHeadersModel<T extends ModelItem> implements AMFHeadersInspectorModel, PropertyChangeListener {
        private final T modelItem;
        private final String propertyName;
        private final boolean readOnly;
        private final PropertyChangeSupport propertyChangeSupport;

        protected AbstractHeadersModel(boolean readOnly, T modelItem, String propertyName) {
            this.readOnly = readOnly;
            this.modelItem = modelItem;
            this.propertyName = propertyName;
            propertyChangeSupport = new PropertyChangeSupport(this);
            modelItem.addPropertyChangeListener(propertyName, this);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.addPropertyChangeListener(listener);
        }

        public void setHeaders(StringToStringMap headers) {
            if (!readOnly) {
                throw new NotImplementedException();
            }
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }

        public boolean isReadOnly() {
            return readOnly;
        }

        public void release() {
            modelItem.removePropertyChangeListener(propertyName, this);
        }

        public void setInspector(AbstractXmlInspector inspector) {
        }

        public void propertyChange(PropertyChangeEvent evt) {
            propertyChangeSupport.firePropertyChange(evt);
        }

        public T getModelItem() {
            return modelItem;
        }
    }
}
