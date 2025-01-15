package com.eviware.soapui.impl.wsdl.support.http;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.conn.DefaultManagedHttpClientConnection;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/*
 * Provide SoapUIWire to log HTTP messages in ReadyAPI HTTP log
 * */
public class SoapUILoggingManagedHttpClientConnection extends DefaultManagedHttpClientConnection {
    private final Logger log;
    private final Logger headerlog;
    private final SoapUIWire wire;

    public SoapUILoggingManagedHttpClientConnection(
        String id,
        Logger log,
        Logger headerlog,
        Logger wirelog,
        int buffersize,
        int fragmentSizeHint,
        CharsetDecoder chardecoder,
        CharsetEncoder charencoder,
        MessageConstraints constraints,
        ContentLengthStrategy incomingContentStrategy,
        ContentLengthStrategy outgoingContentStrategy,
        HttpMessageWriterFactory<HttpRequest> requestWriterFactory,
        HttpMessageParserFactory<HttpResponse> responseParserFactory
    ) {
        super(id,
              buffersize,
              fragmentSizeHint,
              chardecoder,
              charencoder,
              constraints,
              incomingContentStrategy,
              outgoingContentStrategy,
              requestWriterFactory,
              responseParserFactory
        );
        this.log = log;
        this.headerlog = headerlog;
        wire = new SoapUIWire(wirelog);
    }

    @Override
    public void shutdown() throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(getId() + ": Shutdown connection");
        }
        super.shutdown();
    }

    @Override
    protected InputStream getSocketInputStream(Socket socket) throws IOException {
        InputStream in = super.getSocketInputStream(socket);
        if (wire.enabled()) {
            in = new SoapUILoggingInputStream(in, wire);
        }
        return in;
    }

    @Override
    protected OutputStream getSocketOutputStream(Socket socket) throws IOException {
        OutputStream out = super.getSocketOutputStream(socket);
        if (wire.enabled()) {
            out = new SoapUILoggingOutputStream(out, wire);
        }
        return out;
    }

    @Override
    public void setSocketTimeout(int timeout) {
        if (log.isDebugEnabled()) {
            log.debug(getId() + ": set socket timeout to " + timeout);
        }
        super.setSocketTimeout(timeout);
    }

    @Override
    public void close() throws IOException {

        if (isOpen()) {
            if (log.isDebugEnabled()) {
                log.debug(getId() + ": Close connection");
            }
            super.close();
        }
    }

    @Override
    protected void onResponseReceived(HttpResponse response) {
        if (response != null && headerlog.isDebugEnabled()) {
            headerlog.debug(getId() + " << " + response.getStatusLine().toString());
            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                headerlog.debug(getId() + " << " + header.toString());
            }
        }
    }

    @Override
    protected void onRequestSubmitted(HttpRequest request) {
        if (request != null && headerlog.isDebugEnabled()) {
            headerlog.debug(getId() + " >> " + request.getRequestLine().toString());
            Header[] headers = request.getAllHeaders();
            for (Header header : headers) {
                headerlog.debug(getId() + " >> " + header.toString());
            }
        }
    }
}
