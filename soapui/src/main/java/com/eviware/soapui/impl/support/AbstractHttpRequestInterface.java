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

package com.eviware.soapui.impl.support;

import com.eviware.soapui.config.AbstractRequestConfig;
import com.eviware.soapui.impl.rest.RestRequestInterface;
import com.eviware.soapui.impl.support.AbstractHttpRequest.RequestIconAnimator;
import com.eviware.soapui.impl.wsdl.MutableAttachmentContainer;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.submit.transports.http.HttpResponse;
import com.eviware.soapui.impl.wsdl.submit.transports.http.support.methods.IAfterRequestInjection;
import com.eviware.soapui.impl.wsdl.support.ExternalDependency;
import com.eviware.soapui.model.iface.Attachment;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.iface.SubmitListener;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContainer;
import com.eviware.soapui.support.resolver.ResolveContext;
import com.eviware.soapui.support.types.StringToStringsMap;

import javax.swing.ImageIcon;
import java.util.List;

public interface AbstractHttpRequestInterface<T extends AbstractRequestConfig> extends Request, PropertyExpansionContainer, MutableAttachmentContainer {

    String RESPONSE_PROPERTY = WsdlRequest.class.getName() + "@response";
    String REMOVE_EMPTY_CONTENT = WsdlRequest.class.getName() + "@remove_empty_content";
    String STRIP_WHITESPACES = WsdlRequest.class.getName() + "@strip-whitespaces";
    String REQUEST_HEADERS_PROPERTY = WsdlRequest.class.getName() + "@request-headers";
    String BIND_ADDRESS = WsdlRequest.class.getName() + "@bind_address";
    String DISABLE_MULTIPART_ATTACHMENTS = WsdlRequest.class.getName() + "@disable-multipart-attachments";
    String DUMP_FILE = AbstractHttpRequest.class.getName() + "@dump-file";
    String MAX_SIZE = AbstractHttpRequest.class.getName() + "@max-size";
    String FOLLOW_REDIRECTS = AbstractHttpRequest.class.getName() + "@follow-redirects";

    RestRequestInterface.HttpMethod getMethod();

    void setMultipartEnabled(boolean multipartEnabled);

    boolean isEntitizeProperties();

    void setEntitizeProperties(boolean entitizeProperties);

    void release();

    SubmitListener[] getSubmitListeners();

    void copyAttachmentsTo(WsdlRequest newRequest);

    Attachment importAttachment(Attachment attachment);

    boolean isReadOnly();

    void setRequestContent(String request);

    boolean isPrettyPrint();

    void setPrettyPrint(boolean prettyPrint);

    StringToStringsMap getRequestHeaders();

    void setRequestHeaders(StringToStringsMap map);

    RequestIconAnimator<?> getIconAnimator();

    void setIconAnimator(RequestIconAnimator<?> iconAnimator);

    ImageIcon getIcon();

    String getUsername();

    String getPassword();

    String getAuthType();

    void setPassword(String password);

    void setUsername(String username);

    String getDomain();

    void setDomain(String domain);

    String getSslKeystore();

    void setSslKeystore(String sslKeystore);

    String getBindAddress();

    void setBindAddress(String bindAddress);

    long getMaxSize();

    void setMaxSize(long maxSize);

    String getDumpFile();

    void setDumpFile(String df);

    boolean isRemoveEmptyContent();

    void setRemoveEmptyContent(boolean removeEmptyContent);

    boolean isStripWhitespaces();

    void setStripWhitespaces(boolean stripWhitespaces);

    boolean isFollowRedirects();

    void setFollowRedirects(boolean followRedirects);

    void beforeSave();

    HttpResponse getResponse();

    void setResponse(HttpResponse response, SubmitContext context);

    void resolve(ResolveContext<?> context);

    void addExternalDependencies(List<ExternalDependency> dependencies);

    boolean hasEndpoint();

    IAfterRequestInjection getAfterRequestInjection();
}
