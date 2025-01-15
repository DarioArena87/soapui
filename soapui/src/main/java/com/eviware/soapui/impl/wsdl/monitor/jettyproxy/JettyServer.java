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

import com.eviware.soapui.SoapUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;
import org.mortbay.jetty.Server;
import org.mortbay.util.IO;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class JettyServer extends Server {
    private final Logger log = LogManager.getLogger(JettyServer.class);

    public JettyServer() {
        if (SoapUI.getLogMonitor() == null || SoapUI.getLogMonitor().getLogArea("jetty log") == null) {
            return;
        }
        SoapUI.getLogMonitor().getLogArea("jetty log").addLogger(log.getName(), true);
    }

    @Override
    public void handle(HttpConnection connection) throws IOException, ServletException {
        Request request = connection.getRequest();

        if (request.getMethod().equals("CONNECT")) {
            String uri = request.getUri().toString();

            int c = uri.indexOf(':');
            String port = uri.substring(c + 1);
            String host = uri.substring(0, c);

            InetSocketAddress inetAddress = new InetSocketAddress(host, Integer.parseInt(port));

            Socket clientSocket = connection.getEndPoint().getTransport() instanceof Socket
                                        ? (Socket)connection.getEndPoint().getTransport()
                                        : ((SocketChannel)connection.getEndPoint().getTransport()).socket();
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            SSLSocket socket = (SSLSocket)SSLSocketFactory.getDefault().createSocket(inetAddress.getAddress(), inetAddress.getPort());

            Response response = connection.getResponse();
            response.setStatus(200);
            // response.setHeader("Connection", "close");
            response.flushBuffer();

            IO.copyThread(socket.getInputStream(), out);

            IO.copyThread(in, socket.getOutputStream());
        }
        else {
            super.handle(connection);
        }
    }
}
