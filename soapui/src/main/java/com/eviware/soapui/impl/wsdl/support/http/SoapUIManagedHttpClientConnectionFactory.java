package com.eviware.soapui.impl.wsdl.support.http;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.DefaultManagedHttpClientConnection;
import org.apache.http.impl.entity.LaxContentLengthStrategy;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Provide SoapUILoggingManagedHttpClientConnection which logs messages in ReadyAPI HTTP Log
 * */
public class SoapUIManagedHttpClientConnectionFactory implements HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> {
    private static final AtomicLong COUNTER = new AtomicLong();

    private final Logger log = LogManager.getLogger(DefaultManagedHttpClientConnection.class);
    private final Logger headerlog = LogManager.getLogger("org.apache.http.headers");
    private final Logger wirelog = LogManager.getLogger("org.apache.http.wire");

    private final HttpMessageWriterFactory<HttpRequest> requestWriterFactory;
    private final HttpMessageParserFactory<HttpResponse> responseParserFactory;
    private final ContentLengthStrategy incomingContentStrategy;
    private final ContentLengthStrategy outgoingContentStrategy;

    public SoapUIManagedHttpClientConnectionFactory(
        HttpMessageWriterFactory<HttpRequest> requestWriterFactory,
        HttpMessageParserFactory<HttpResponse> responseParserFactory,
        ContentLengthStrategy incomingContentStrategy,
        ContentLengthStrategy outgoingContentStrategy
    ) {
        this.requestWriterFactory = requestWriterFactory != null ? requestWriterFactory : DefaultHttpRequestWriterFactory.INSTANCE;
        this.responseParserFactory = responseParserFactory != null ? responseParserFactory : DefaultHttpResponseParserFactory.INSTANCE;
        this.incomingContentStrategy = incomingContentStrategy != null ? incomingContentStrategy : LaxContentLengthStrategy.INSTANCE;
        this.outgoingContentStrategy = outgoingContentStrategy != null ? outgoingContentStrategy : StrictContentLengthStrategy.INSTANCE;
    }

    public SoapUIManagedHttpClientConnectionFactory(
        HttpMessageWriterFactory<HttpRequest> requestWriterFactory, HttpMessageParserFactory<HttpResponse> responseParserFactory
    ) {
        this(requestWriterFactory, responseParserFactory, null, null);
    }

    public SoapUIManagedHttpClientConnectionFactory(
        HttpMessageParserFactory<HttpResponse> responseParserFactory
    ) {
        this(null, responseParserFactory);
    }

    public SoapUIManagedHttpClientConnectionFactory() {
        this(null, null);
    }

    @Override
    public ManagedHttpClientConnection create(HttpRoute route, ConnectionConfig config) {
        ConnectionConfig cconfig = config != null ? config : ConnectionConfig.DEFAULT;
        CharsetDecoder chardecoder = null;
        CharsetEncoder charencoder = null;
        Charset charset = cconfig.getCharset();
        CodingErrorAction malformedInputAction = cconfig.getMalformedInputAction() != null ? cconfig.getMalformedInputAction() : CodingErrorAction.REPORT;
        CodingErrorAction unmappableInputAction = cconfig.getUnmappableInputAction() != null ? cconfig.getUnmappableInputAction() : CodingErrorAction.REPORT;
        if (charset != null) {
            chardecoder = charset.newDecoder();
            chardecoder.onMalformedInput(malformedInputAction);
            chardecoder.onUnmappableCharacter(unmappableInputAction);
            charencoder = charset.newEncoder();
            charencoder.onMalformedInput(malformedInputAction);
            charencoder.onUnmappableCharacter(unmappableInputAction);
        }
        String id = "http-outgoing-" + COUNTER.getAndIncrement();
        return new SoapUILoggingManagedHttpClientConnection(
            id,
            log,
            headerlog,
            wirelog,
            cconfig.getBufferSize(),
            cconfig.getFragmentSizeHint(),
            chardecoder,
            charencoder,
            cconfig.getMessageConstraints(),
            incomingContentStrategy,
            outgoingContentStrategy,
            requestWriterFactory,
            responseParserFactory
        );
    }
}
