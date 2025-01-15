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

package com.eviware.soapui.support.components;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class JComponentInspector<T extends JComponent> implements Inspector {
    private final T component;
    private String title;
    private String description;
    private boolean enabled;
    private PropertyChangeSupport propertyChangeSupport;
    private ImageIcon imageIcon;
    private final String id;

    public JComponentInspector(T component, String title, String description, boolean enabled) {
        this.component = component;
        this.title = title;
        id = title;
        this.description = description;
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String old = this.title;
        this.title = title;

        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange(TITLE_PROPERTY, old, title);
        }
    }

    public ImageIcon getIcon() {
        return imageIcon;
    }

    public T getComponent() {
        return component;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (propertyChangeSupport == null) {
            propertyChangeSupport = new PropertyChangeSupport(this);
        }

        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (propertyChangeSupport != null) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    public String getInspectorId() {
        return id;
    }

    public void release() {
    }

    public void activate() {
    }

    public void deactivate() {
    }

    public void setEnabled(boolean enabled) {
        if (enabled == this.enabled) {
            return;
        }

        this.enabled = enabled;
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange(ENABLED_PROPERTY, !enabled, enabled);
        }
    }

    public void setDescription(String description) {
        String old = this.description;
        this.description = description;

        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange(DESCRIPTION_PROPERTY, old, description);
        }
    }

    public void setIcon(ImageIcon imageIcon) {
        ImageIcon old = this.imageIcon;

        this.imageIcon = imageIcon;
        if (propertyChangeSupport != null) {
            propertyChangeSupport.firePropertyChange(ICON_PROPERTY, old, imageIcon);
        }
    }
}
