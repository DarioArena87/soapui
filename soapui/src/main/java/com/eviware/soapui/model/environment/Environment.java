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

package com.eviware.soapui.model.environment;

import com.eviware.soapui.config.ServiceConfig;
import com.eviware.soapui.model.project.Project;

public interface Environment {

    Project getProject();

    void setProject(Project project);

    void release();

    Service addNewService(String name, ServiceConfig.Type.Enum serviceType);

    void removeService(Service service);

    String getName();

    void setName(String name);

    Property addNewProperty(String name, String value);

    void removeProperty(Property property);

    void changePropertyName(String name, String value);

    void moveProperty(String name, int idx);
}
