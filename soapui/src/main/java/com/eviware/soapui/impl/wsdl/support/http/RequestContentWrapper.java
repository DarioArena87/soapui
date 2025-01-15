package com.eviware.soapui.impl.wsdl.support.http;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.RequestContent;

import java.io.IOException;

public class RequestContentWrapper implements HttpRequestInterceptor {
    private final RequestContent requestContent;

    RequestContentWrapper(boolean overwrite) {
        requestContent = new RequestContent(overwrite);
    }

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        requestContent.process(request, context);
        boolean removeEmptyContentLength = !Boolean.valueOf(System.getProperty("soapui.send.zero.content.length", "true"));
        if (removeEmptyContentLength) {
            for (Header header : request.getAllHeaders()) {
                if (header.getName().equals("Content-Length") && header.getValue().equals("0")) {
                    request.removeHeader(header);
                    break;
                }
            }
        }
    }
}
