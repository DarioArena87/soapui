package com.eviware.soapui.impl.wsdl.support.http;

import com.smartbear.soapui.core.Logging;
import org.apache.http.util.Args;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Formats and logs data to the ReadyAPI HTTP LOG.
 * */
public class SoapUIWire {
    private static final Pattern REQUEST_LINE_PATTERN = Pattern.compile("^[POST|GET|PUT|DELETE|PATCH|HEAD|OPTIONS|TRACE].* HTTP\\/1\\.[0|1]");
    private static final Pattern RESPONSE_LINE_PATTERN = Pattern.compile("^HTTP\\/1\\.[0|1] \\d{3} ");
    private final Logger log;

    public SoapUIWire(Logger log) {
        this.log = log;
    }

    private void wire(boolean request, InputStream instream) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int ch;
        while ((ch = instream.read()) != -1) {
            if (ch == 13) {
                //ignore carriage return
            }
            else if (ch == 10) {
                //log if line feed
                String line = buffer.toString();
                log.debug(appendMarkerIfNeeded(request, line), line);
                buffer.setLength(0);
            }
            else if ((ch < 32) || (ch > 127)) {
                // to hex if control code
                buffer.append("[0x");
                buffer.append(Integer.toHexString(ch));
                buffer.append("]");
            }
            else {
                buffer.append((char)ch);
            }
        }
        if (buffer.length() > 0) {
            String line = buffer.toString();
            log.debug(appendMarkerIfNeeded(request, line), line);
        }
    }

    public boolean enabled() {
        return log.isDebugEnabled();
    }

    public void output(InputStream outstream) throws IOException {
        Args.notNull(outstream, "Output");
        wire(true, outstream);
    }

    public void input(InputStream instream) throws IOException {
        Args.notNull(instream, "Input");
        wire(false, instream);
    }

    public void output(byte[] b, int off, int len) throws IOException {
        Args.notNull(b, "Output");
        wire(true, new ByteArrayInputStream(b, off, len));
    }

    public void input(byte[] b, int off, int len) throws IOException {
        Args.notNull(b, "Input");
        wire(false, new ByteArrayInputStream(b, off, len));
    }

    public void output(byte[] b) throws IOException {
        Args.notNull(b, "Output");
        wire(true, new ByteArrayInputStream(b));
    }

    public void input(byte[] b) throws IOException {
        Args.notNull(b, "Input");
        wire(false, new ByteArrayInputStream(b));
    }

    public void output(int b) throws IOException {
        output(new byte[]{(byte)b});
    }

    public void input(int b) throws IOException {
        input(new byte[]{(byte)b});
    }

    public void output(String s) throws IOException {
        Args.notNull(s, "Output");
        output(s.getBytes());
    }

    public void input(String s) throws IOException {
        Args.notNull(s, "Input");
        input(s.getBytes());
    }

    private Marker appendMarkerIfNeeded(boolean request, String line) {
        Matcher matcher;
        if (request) {
            matcher = REQUEST_LINE_PATTERN.matcher(line);
            if (matcher.find()) {
                return Logging.HTTP_CLIENT_WIRE_LOG_TIMESTAMP_MARKER_OUTGOING;
            }
        }
        else {
            matcher = RESPONSE_LINE_PATTERN.matcher(line);
            if (matcher.find()) {
                return Logging.HTTP_CLIENT_WIRE_LOG_TIMESTAMP_MARKER_INCOMING;
            }
        }
        return null;
    }
}
