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

package com.eviware.soapui.support.action;

import com.eviware.soapui.model.ModelItem;

/**
 * The mapping of a SoapUIAction into a SoapUIActionGroup
 *
 * @author ole.matzura
 */

public interface SoapUIActionMapping<T extends ModelItem> {
    SoapUIAction<T> getAction();

    String getActionId();

    String getName();

    SoapUIActionMapping<T> setName(String name);

    String getDescription();

    SoapUIActionMapping<T> setDescription(String description);

    boolean isDefault();

    boolean isEnabled();

    SoapUIActionMapping<T> setEnabled(boolean enabled);

    String getIconPath();

    String getKeyStroke();

    Object getParam();

    SoapUIActionMapping<T> setParam(Object param);

    int getToolbarIndex();
}
