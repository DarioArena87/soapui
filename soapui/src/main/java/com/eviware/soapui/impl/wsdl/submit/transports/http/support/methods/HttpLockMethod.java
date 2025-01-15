package com.eviware.soapui.impl.wsdl.submit.transports.http.support.methods;

import com.eviware.soapui.impl.rest.RestRequestInterface;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * HTTP LOCK Method https://tools.ietf.org/html/rfc4918
 */
public class HttpLockMethod extends HttpEntityEnclosingRequestBase {

    public HttpLockMethod() {
    }

    public HttpLockMethod(URI uri) {
        setURI(uri);
    }

    /**
     * @throws IllegalArgumentException if the uri is invalid.
     */
    public HttpLockMethod(String uri) {
        this(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return RestRequestInterface.HttpMethod.LOCK.toString();
    }
}