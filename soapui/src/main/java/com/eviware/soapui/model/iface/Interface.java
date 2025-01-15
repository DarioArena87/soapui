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

package com.eviware.soapui.model.iface;

import com.eviware.soapui.impl.support.DefinitionContext;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.project.Project;

import java.util.List;

/**
 * An Interface exposing operations
 *
 * @author Ole.Matzura
 */

public interface Interface extends ModelItem {
    String ENDPOINT_PROPERTY = Interface.class.getName() + "@endpoint";

    String DEFINITION_PROPERTY = Interface.class.getName() + "@definition";

    String UPDATING_PROPERTY = Interface.class.getName() + "@updating";

    String[] getEndpoints();

    Operation getOperationAt(int index);

    int getOperationCount();

    Operation getOperationByName(String name);

    Project getProject();

    void addInterfaceListener(InterfaceListener listener);

    void removeInterfaceListener(InterfaceListener listener);

    String getTechnicalId();

    List<Operation> getOperationList();

    String getInterfaceType();

    void addEndpoint(String endpoint);

    void removeEndpoint(String ep);

    void changeEndpoint(String endpoint, String string);

    DefinitionContext<?> getDefinitionContext();

    Operation[] getAllOperations();
}
