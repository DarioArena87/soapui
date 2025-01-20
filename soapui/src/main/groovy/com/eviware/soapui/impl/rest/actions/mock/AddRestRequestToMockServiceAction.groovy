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

package com.eviware.soapui.impl.rest.actions.mock

import com.eviware.soapui.SoapUI
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.rest.mock.RestMockAction
import com.eviware.soapui.impl.rest.mock.RestMockResponse
import com.eviware.soapui.impl.rest.mock.RestMockService
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.model.mock.MockOperation
import com.eviware.soapui.model.mock.MockService
import com.eviware.soapui.model.support.ModelSupport
import com.eviware.soapui.support.MessageSupport
import com.eviware.soapui.support.UISupport
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import com.eviware.soapui.support.types.StringToStringsMap

class AddRestRequestToMockServiceAction extends AbstractSoapUIAction<RestRequest> {

    public static final String SOAPUI_ACTION_ID = "AddRestRequestToMockServiceAction"
    private static final String SELECT_MOCKSERVICE_OPTION = "Create new.."
    private static final MessageSupport messages = MessageSupport.getMessages(AddRestRequestToMockServiceAction)
    private static final List<String> HEADERS_TO_IGNORE = ["#status#", "Content-Type", "Content-Length"]

    AddRestRequestToMockServiceAction() {
        super(messages.get("Title"), messages.get("Description"))
    }

    @Override
    void perform(RestRequest restRequest, Object param) {
        RestMockService mockService = null
        WsdlProject project = restRequest.operation.interface.project

        while (mockService == null) {

            if (project.restMockServiceCount > 0) {
                String option = promptForMockServiceSelection(name, project)
                if (!option) {
                    return
                }

                mockService = project.getRestMockServiceByName(option)
            }

            if (mockService == null) {
                mockService = createNewMockService(name, project)
                UISupport.showDesktopPanel(mockService)
                maybeStart(mockService)
            }
        }

        addRequestToMockService(restRequest, mockService)
        restRequest.operation.service.addEndpoint(mockService.localEndpoint)
    }

    private void maybeStart(MockService mockService) {
        try {
            mockService.startIfConfigured()
        }
        catch (Exception e) {
            SoapUI.logError(e)
            UISupport.showErrorMessage(e.message)
        }
    }

    private String promptForMockServiceSelection(String title, WsdlProject project) {
        String[] mockServices = ModelSupport.getNames(project.restMockServiceList, new String[]{SELECT_MOCKSERVICE_OPTION})

        // prompt
        return UISupport.prompt("Select RESTMockService for adding REST request", title, mockServices)
    }

    private RestMockService createNewMockService(String title, WsdlProject project) {
        return project.addNewRestMockService(promptForServiceName(title, project))
    }

    private String promptForServiceName(String title, WsdlProject project) {
        String defaultName = "REST MockService ${project.restMockServiceCount + 1}"
        return UISupport.prompt("Enter name of new MockService", title, defaultName)
    }

    private void addRequestToMockService(RestRequest restRequest, RestMockService mockService) {
        MockOperation matchedOperation = mockService.findOrCreateNewOperation(restRequest)

        String responseName = "Response ${matchedOperation.mockResponseCount + 1}"

        RestMockResponse mockResponse = ((RestMockAction)matchedOperation).addNewMockResponse(responseName)
        // add expected response if available
        if (restRequest && restRequest.response) {
            copyResponseContent(restRequest, mockResponse)
            copyHeaders(restRequest, mockResponse)
        }
    }

    private void copyHeaders(RestRequest restRequest, RestMockResponse mockResponse) {
        StringToStringsMap requestHeaders = restRequest.response.responseHeaders
        for (String header : HEADERS_TO_IGNORE) {
            requestHeaders.remove(header)
        }
        mockResponse.responseHeaders = requestHeaders
    }

    private void copyResponseContent(RestRequest restRequest, RestMockResponse mockResponse) {
        if (restRequest.response.contentAsString) {
            mockResponse.responseContent = restRequest.response.contentAsString
            mockResponse.contentType = restRequest.response.contentType
        }
    }
}
