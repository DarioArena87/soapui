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

package com.eviware.soapui.model.project;

import com.eviware.soapui.impl.rest.OAuth1ProfileContainer;
import com.eviware.soapui.impl.rest.OAuth2ProfileContainer;
import com.eviware.soapui.impl.rest.mock.RestMockService;
import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;
import com.eviware.soapui.model.TestModelItem;
import com.eviware.soapui.model.iface.Interface;
import com.eviware.soapui.model.mock.MockService;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.workspace.Workspace;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * A SoapUI project
 *
 * @author Ole.Matzura
 */

public interface Project extends TestModelItem {
    /**
     * The id of the JBossWS project nature
     */
    String JBOSSWS_NATURE_ID = "com.eviware.soapui.jbosside.jbosswsNature";

    /**
     * The id of the SoapUI project nature
     */
    String SOAPUI_NATURE_ID = "com.eviware.soapui.soapuiNature";

    Workspace getWorkspace();

    Interface getInterfaceAt(int index);

    Interface getInterfaceByName(String interfaceName);

    int getInterfaceCount();

    void addProjectListener(ProjectListener listener);

    void removeProjectListener(ProjectListener listener);

    int getTestSuiteCount();

    TestSuite getTestSuiteAt(int index);

    TestSuite getTestSuiteByName(String testSuiteName);

    TestSuite getTestSuiteById(UUID testSuiteId);

    TestSuite addNewTestSuite(String name);

    int getMockServiceCount();

    int getRestMockServiceCount();

    MockService getMockServiceAt(int index);

    MockService getRestMockServiceAt(int index);

    MockService getMockServiceByName(String mockServiceName);

    RestMockService getRestMockServiceByName(String mockServiceName);

    MockService addNewMockService(String name);

    RestMockService addNewRestMockService(String name);

    void removeMockService(MockService service);

    SaveStatus save() throws IOException;

    List<TestSuite> getTestSuiteList();

    List<WsdlMockService> getMockServiceList();

    List<RestMockService> getRestMockServiceList();

    List<Interface> getInterfaceList();

    boolean hasNature(String natureId);

    EndpointStrategy getEndpointStrategy();

    void release();

    boolean isOpen();

    boolean isDisabled();

    String getPath();

    String getResourceRoot();

    String getShadowPassword();

    void setShadowPassword(String password);

    void inspect();

    int getIndexOfTestSuite(TestSuite testSuite);

    OAuth2ProfileContainer getOAuth2ProfileContainer();

    OAuth1ProfileContainer getOAuth1ProfileContainer();
}
