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

package com.eviware.soapui.impl.wsdl.monitor.jettyproxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;

public class CapturedExchange {

    private boolean startCapture;
    private boolean stopCapture;

    private long operationStarted;
    private long timeTaken;

    private String requestHost;
    private String targetHost;

    private String wsInterface;
    private String wsOperation;
    private int requestSize;
    private int responseSize;
    private byte[] request;
    private byte[] response;

    private String requestHeader;
    private String responseHeader;

    public boolean isStartCapture() {
        return startCapture;
    }

    public void startCapture() {
        startCapture = true;
        stopCapture = false;
        setOperationStarted(System.currentTimeMillis());
    }

    public boolean isStopCapture() {
        return stopCapture;
    }

    public void stopCapture() {
        startCapture = false;
        stopCapture = true;
        setTimeTaken(System.currentTimeMillis());
    }

    public long getOperationStarted() {
        return operationStarted;
    }

    private void setOperationStarted(long operationStarted) {
        this.operationStarted = operationStarted;
    }

    public String getRequestHost() {
        return requestHost;
    }

    public void setRequestHost(String requestHost) {
        this.requestHost = requestHost;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public String getWsInterface() {
        return wsInterface;
    }

    public void setWsInterface(String wsInterface) {
        this.wsInterface = wsInterface;
    }

    public String getWsOperation() {
        return wsOperation;
    }

    public void setWsOperation(String wsOperation) {
        this.wsOperation = wsOperation;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    private void setTimeTaken(long endTime) {
        timeTaken = -operationStarted + endTime;
    }

    public int getRequestSize() {
        return requestSize;
    }

    private void setRequestSize(int requestSizeInCharacters) {
        requestSize = requestSizeInCharacters;
    }

    public int getResponseSize() {
        return responseSize;
    }

    private void setResponseSize() {
        int length = response.length;
        responseSize = length;
    }

    public byte[] getRequest() {
        return request;
    }

    public void setRequest(byte[] request) {
        // this.request = request;
        if (this.request == null) {
            this.request = request;
        }
        else {
            byte[] newRequest = new byte[this.request.length + request.length];
            System.arraycopy(this.request, 0, newRequest, 0, this.request.length);
            if (newRequest.length - this.request.length >= 0)
                System.arraycopy(request, this.request.length - response.length, newRequest, this.request.length, newRequest.length - this.request.length);
            this.request = newRequest;
        }
        setRequestSize(this.request.length);
    }

    public byte[] getResponse() {
        return response;
    }

    public void setResponse(byte[] response) {
        if (this.response == null) {
            this.response = response;
        }
        else {
            byte[] newResponse = new byte[this.response.length + response.length];
            System.arraycopy(this.response, 0, newResponse, 0, this.response.length);
            if (newResponse.length - this.response.length >= 0)
                System.arraycopy(response, this.response.length - this.response.length, newResponse, this.response.length, newResponse.length - this.response.length);
            this.response = newResponse;
        }
        setResponseSize();
    }

    @Override
    public String toString() {

        String toString = "Request host: " + requestHost + "\n";
        toString += "Request header : \n" + requestHeader + "\n";
        toString += "Request: " + new String(request) + "\n";
        toString += "Request size: " + requestSize + "\n";
        toString += "Response host:" + targetHost + "\n";
        toString += "Response header: \n" + responseHeader + "\n";
        toString += "Response: " + new String(response) + "\n";
        toString += "Response size:" + responseSize + "\n";
        toString += "Started: " + new Date(operationStarted) + "\n";
        toString += "Time Taken: " + timeTaken + "ms\n";
        return toString;
    }

    @SuppressWarnings("unchecked")
    public void setRequestHeader(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        String headerValue = null;
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();

            if (ProxyServlet.dontProxyHeaders.contains(name.toLowerCase())) {
                continue;
            }

            headerValue = name + "::";
            Enumeration<String> header = httpRequest.getHeaders(name);
            while (header.hasMoreElements()) {
                String value = header.nextElement();
                if (value != null) {
                    headerValue += value;
                }
            }
            requestHeader = requestHeader == null ? headerValue : requestHeader + "\n" + headerValue;
        }
    }

    public void addResponseHeader(String responseHeader) {
        this.responseHeader = this.responseHeader == null ? responseHeader : this.responseHeader + "\n" + responseHeader;
    }
}
