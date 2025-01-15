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

package com.eviware.soapui.model.settings;

/**
 * Base interface for settings available in the soapui model
 *
 * @author Ole.Matzura
 */

public interface Settings {
    String getString(String id, String defaultValue);

    void setString(String id, String value);

    /**
     * Defaults to false.
     */
    boolean getBoolean(String id);

    boolean getBoolean(String id, boolean defaultValue);

    void setBoolean(String id, boolean value);

    void addSettingsListener(SettingsListener listener);

    void removeSettingsListener(SettingsListener listener);

    void clearSetting(String id);

    long getLong(String id, long defaultValue);

    boolean isSet(String id);

    void setLong(String id, long value);

    void reloadSettings();
}
