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

package com.eviware.soapui.model.mock;

import com.eviware.soapui.impl.support.HasHelpUrl;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.Releasable;
import com.eviware.soapui.model.TestModelItem;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.model.support.AnimatableItem;

import java.util.List;

/**
 * ModelItem for mocking a number of Interfaces and their Operations
 *
 * @author ole.matzura
 */

public interface MockService extends TestModelItem, Releasable, AnimatableItem, HasHelpUrl, MockServer, MockServiceScripts {
    String PATH_PROPERTY = MockService.class.getName() + "@path";
    String PORT_PROPERTY = MockService.class.getName() + "@port";

    WsdlProject getProject();

    List<MockOperation> getMockOperationList();

    int getMockOperationCount();

    MockOperation getMockOperationAt(int index);

    MockOperation getMockOperationByName(String name);

    MockOperation addNewMockOperation(Operation operation);

    void removeMockOperation(MockOperation mockOperation);

    void addMockServiceListener(MockServiceListener listener);

    void removeMockServiceListener(MockServiceListener listener);

    void fireMockOperationAdded(MockOperation mockOperation);

    void fireMockOperationRemoved(MockOperation mockOperation);

    void fireMockResponseAdded(MockResponse mockResponse);

    void fireMockResponseRemoved(MockResponse mockResponse);

    String getStringID();
}
