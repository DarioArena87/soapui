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

package com.eviware.soapui.impl.wsdl.support;

import com.eviware.soapui.SoapUI;

import static com.eviware.soapui.impl.support.HttpUtils.urlEncodeWithUtf8;

/**
 * Help URLS in SoapUI documentation
 *
 * @author ole.matzura
 */
public interface HelpUrls {

    /*
    Help Sites
    */ String BASE_URL_PROD = "http://www.soapui.org";

    String BASE_URL_DEV = "http://dev.soapuidocs.sthlm.smartbear.local";

    String BASE_URL_NEXT = "http://next.soapuidocs.sthlm.smartbear.local";

    String MISSING_URL = "/missing-url.html";

    String THIRD_PARTY_LICENSE_INFO = "/developers-corner/3rd.html";

    String SMARTBEAR_WEB_SITE_START_PAGE = "http://www.smartbear.com";
    String SMARTBEAR_PRIVACY_POLICY_URL = "/Store-Info/privacy-policy.html";

    String ADDMOCKOPERATIONASMOCKRESPONSESTEP_HELP_URL = "/Service-Mocking/mocking-soap-services.html";
    String ADDMOCKRESPONSETOTESTCASE_HELP_URL = "/Service-Mocking/mocking-soap-services.html";
    String ADDREQUESTASMOCKRESPONSESTEP_HELP_URL = "/Service-Mocking/mocking-soap-services.html";
    String ADD_ASSERTION_PANEL = "/functional-testing/validating-messages/getting-started-with-assertions.html";
    String ADD_AUTHORIZATION = "/OAuth/add-authorization.html";
    String ALERT_SITE_HELP_URL = "http://help.alertsite.com/WebHome";
    String AMF_REQUEST_HEADERS_HELP_URL = "/amf/reference/the-amf-request-window.html";
    String API_TESTING_DOJO_HELP_URL = "/dojo/overview.html";
    String ATTACHMENTS_HELP_URL = "/SOAP-and-WSDL/adding-headers-and-attachments.html";
    String AUTHORIZATION = "/OAuth/authorization.html";
    String AUTHORIZATION_BASIC = "/OAuth/Basic.html";
    String AUTHORIZATION_NTLM = "/OAuth/NTLM.html";
    String AUTHORIZATION_OAUTH2 = "/OAuth/OAuth2.html";
    String AUTHORIZATION_SPNEGO_KERBEROS = "/OAuth/SPNEGO-Kerberos.html";
    String AXIS1X_HELP_URL = "http://ws.apache.org/axis/java/reference.html#WSDL2JavaReference";
    String AXIS2X_HELP_URL = "http://ws.apache.org/axis2/tools/1_0/CodegenToolReference.html";

    String CHANGEMOCKOPERATION_HELP_URL = "/soap-mocking/working-with-mockservices.html";
    String CHANGEOPERATION_HELP_URL = "/soap-and-wsdl/operations-and-requests.html";
    String CLONEMOCKSERVICE_HELP_URL = "/Service-Mocking/mocking-soap-services.html";
    String CLONETESTCASE_HELP_URL = "/functional-testing/reference/testsuite-reference.html";
    String CLONETESTSTEP_HELP_URL = "/functional-testing/structuring-and-running-tests.html";
    String CLONETESTSUITE_HELP_URL = "/Functional-Testing/structuring-and-running-tests.html";

    String COMMUNITY_HELP_URL = "http://community.smartbear.com";
    String COMMUNITY_SEARCH_URL = "http://community.smartbear.com";

    String CREATEMOCKRESPONSESTEP_HELP_URL = "/Service-Mocking/mocking-soap-services.html";
    String CRYPTOSWSS_HELP_URL = "/soapui-projects/ws-security.html";
    String CXFWSDL2JAVA_HELP_URL = "http://cxf.apache.org/docs/wsdl-to-java.html";

    String DEBUGGING_ASSERTION_TEST_STEP = "/Functional-Testing/assertion-test-step.html";
    String DEBUGGING_ENVIRONMENT_HANDLING = "/Environments/environment-handling-in-soapui.html";
    String DEBUGGING_TESTCASE_DEBUGGING = "/functional-testing/testcase-debugging.html";
    String DOTNET_HELP_URL = "https://msdn.microsoft.com/en-us/library/7h3ystb6.aspx";

    String ENDPOINTSEDITOR_HELP_URL = "/SOAP-and-WSDL/working-with-wsdls.html";

    String FUNCTIONAL_TESTING_SETUP_SCRIPT = "/functional-testing/working-with-scripts.html";
    String FUNCTIONAL_TESTING_TEARDOWN_SCRIPT = "/functional-testing/working-with-scripts.html";

    String GENERATE_MOCKSERVICE_HELP_URL = "/Service-Mocking/mocking-soap-services.html ";
    String GENERATE_REST_MOCKSERVICE = "/rest-testing-mocking/rest-mock-service-creation/rest-mock-from-service.html";
    String GENERATE_TESTSUITE_HELP_URL = "/Functional-Testing/structuring-and-running-tests.html";
    String GETTINGSTARTED_HELP_URL = "/Getting-Started/your-first-soapui-project.html";
    String GOTOSTEPEDITOR_HELP_URL = "/functional-testing/teststep-reference/conditional-goto/conditional-goto.html";
    String GROOVYASSERTION_HELP_URL = "/functional-testing/validating-messages/using-script-assertions.html";
    String GROOVYSTEPEDITOR_HELP_URL = "/functional-testing/working-with-scripts.html";
    String ASSERTION_JMS_TIMEOUT_EDITOR_HELP_URL = "/jms/validating-jms-responses.html";
    String GSOAP_HELP_URL = "http://www.cs.fsu.edu/~engelen/soap.html";

    String HTTP_REQUEST_HELP_URL = "/functional-testing/teststep-reference/http-request/http-request.html";
    String HTTP_REQUEST_PARAMS_HELP_URL = "/functional-testing/teststep-reference/http-request/parameters.html";
    String HTTP_REQUEST_HEADERS_HELP_URL = "/functional-testing/teststep-reference/http-request/headers.html";

    String INCOMINGWSS_HELP_URL = "/soapui-projects/ws-security.html";
    String INTERFACE_HELP_URL = "/SOAP-and-WSDL/working-with-wsdls.html";
    String INTERFACE_OVERVIEW_HELP_URL = "/SOAP-and-WSDL/working-with-wsdls.html";

    String JABXJC_HELP_URL = "http://java.sun.com/webservices/docs/2.0/jaxb/xjc.html";
    String JBOSSWS_WSCONSUME_HELP_URL = "https://developer.jboss.org/wiki/JBossWS-wsconsume";
    String JDBCSTEPEDITOR_HELP_URL = "/JDBC/getting-started.html";
    String JDBC_CONNECTION_HELP_URL = "/Working-with-Projects/jdbc-connections.html";

    String LOADTESTEDITOR_HELP_URL = "/load-testing/getting-started.html";
    String LOADTESTOPTIONS_HELP_URL = "/load-testing/reference/navigation.html";
    String LOADTEST_ASSERTIONS_URL = "/Load-Testing/validating-performance.html";

    String MANUALTESTSTEP_HELP_URL = "/functional-testing/teststep-reference/manual-teststep.html";
    String MAX_ERRORS_LOAD_TEST_ASSERTION_HELP_URL = "/load-testing/reference/assertion.html";
    String MOCKASWAR_HELP_URL = "/Service-Mocking/deploying-mock-services-as-war-files.html";
    String MOCKOPERATION_HELP_URL = "/Service-Mocking/simulating-complex-behaviour.html";
    String MOCKOPERATION_QUERYMATCHDISPATCH_HELP_URL = "/soap-mocking/working-with-mockservices.html";
    String MOCKOPERATION_SCRIPTDISPATCH_HELP_URL = "/soap-mocking/working-with-mockservices.html";
    String MOCKOPERATION_XPATHDISPATCH_HELP_URL = "/soap-mocking/working-with-mockservices.html";
    String MOCKRESPONSE_SCRIPT_HELP_URL = "/soap-mocking/mockoperations-and-responses.html";
    String MOCKSERVICEOPTIONS_HELP_URL = "/soap-mocking/working-with-mockservices.html";
    String MOCKSERVICE_HELP_URL = "/Service-Mocking/mocking-soap-services.html";

    String NEWPROJECT_HELP_URL = "/Working-with-Projects/new-project.html";
    String NEWRESTPROJECT_HELP_URL = "/REST-Testing/getting-started.html";
    String NEWRESTSERVICE_HELP_URL = "/REST-Testing/working-with-rest-services.html";
    String NEW_WADL_PROJECT_HELP_URL = "/REST-Testing/working-with-rest-services.html";
    String CREATEWADLDOC_HELP_URL = "/REST-Testing/working-with-rest-services.html";
    String NEW_REST_REQUEST_HELP_URL = "/REST-testing/working-with-rest-requests.html";

    String OAUTH_ACCESS_TOKEN_FROM_SERVER = "/OAuth/access-token-from-server.html";
    String OAUTH_ACCESS_TOKEN_RETRIEVAL = "/OAuth/access-token-retrieval.html";
    String OAUTH_ADVANCED_OPTIONS = "/OAuth/advanced-options.html";
    String OAUTH_AUTOMATED_TOKEN_PROFILE_EDITOR = "/OAuth/automated-token-profile-editor.html";
    String OAUTH_AUTOMATING_ACCESS_TOKEN_RETRIEVAL = "/OAuth/automating-access-token-retrieval.html";
    String OAUTH_OVERVIEW = "/OAuth/oauth2-overview.html";
    String ORACLEWSA_HELP_URL = "/soap-and-wsdl/soap-code-generation.html";
    String OUTGOINGWSS_HELP_URL = "/soapui-projects/ws-security.html";

    String PREFERENCES_HELP_URL = "/Working-with-soapUI/preferences.html";
    String PREPAREDPROPERTIES_HELP_URL = "/soapui-projects/jdbc-connections.html";
    String PROJECT_OVERVIEW_HELP_URL = "/Working-with-Projects/working-with-soapui-projects.html";
    String PROPERTIESSTEPEDITOR_HELP_URL = "/functional-testing/properties/working-with-properties.html";
    String PROPERTY_TRANSFER_HELP_URL = "/Functional-Testing/transferring-property-values.html";

    String REQUESTEDITOR_HELP_URL = "/soap-and-wsdl/working-with-messages.html";
    String RESPONSE_ASSERTIONS_HELP_URL = "/Functional-Testing/getting-started-with-assertions.html";
    String RESTMETHODEDITOR_HELP_URL = "/REST-Testing/rest-resources-and-methods.html";
    String RESTREQUESTEDITOR_HELP_URL = "/REST-Testing/working-with-rest-requests.html";
    String RESTRESOURCEEDITOR_HELPURL = "/REST-Testing/rest-resources-and-methods.html";
    String REST_MOCKSERVICE_ACTION = "/REST-Service-Mocking/mock-action-editor.html";
    String REST_MOCKSERVICE_HELP_URL = "/REST-Service-Mocking/mock-service-editor.html";
    String REST_MOCKSERVICE_OPTIONS = "/REST-Service-Mocking/mock-service-options.html";
    String REST_MOCK_RESPONSE_EDITOR = "/REST-Service-Mocking/mock-response-editor.html";
    String REST_MOCK_RESPONSE_EDITOR_BODY = "/rest-testing-mocking/reference/mock-response-editor.html";
    String REST_MOCK_RESPONSE_EDITOR_HEADER = "/rest-testing-mocking/reference/mock-response-editor.html";
    String REST_MOCK_RESPONSE_SCRIPT = "/rest-testing-mocking/reference/mock-response-editor.html";
    String REST_MOCK_SCRIPTDISPATCH = "/rest-testing-mocking/reference/mock-response-editor.html";
    String RUNTESTCASESTEP_HELP_URL = "/Functional-Testing/modularizing-your-tests.html";

    String SECURITYTESTEDITOR_HELP_URL = "/Security/working-with-security-tests.html";
    String SECURITY_ASSERTION_HELP = "/security-testing/overview-of-security-scans.html";
    String SECURITY_INVALID_HTTP_CODES_ASSERTION_HELP = "/security-testing/overview-of-security-scans.html";
    String SECURITY_MALICIOUS_ATTACHMENT_HELP = "/Security/malicious-attachment.html";
    String SECURITY_SCANS_OVERVIEW = "/Security/security-scans-overview.html";
    String SECURITY_SENSITIVE_INFORMATION_EXPOSURE_ASSERTION_HELP = "/security-testing/overview-of-security-scans.html";
    String SECURITY_VALID_HTTP_CODES_ASSERTION_HELP = "/security-testing/overview-of-security-scans.html";
    String SECURITY_XSS_ASSERTION_HELP = "/Security/cross-site-scripting.html";
    String SETMOCKOPERATION_HELP_URL = "/soap-mocking/reference/mockresponse.html";
    String SIMPLE_CONTAINS_HELP_URL = "/functional-testing/validating-messages/getting-started-with-assertions.html";
    String SIMPLE_NOT_CONTAINS_HELP_URL = "/functional-testing/validating-messages/getting-started-with-assertions.html";
    String SOAPMONITOR_GENERAL_OPTIONS = "/HTTP-Recording/general-options.html";
    String SOAPMONITOR_MONITOR = "/HTTP-Recording/monitor.html";
    String SOAPMONITOR_MONITOR_OPTIONS = "/http-recording/reference/general-options.html";
    String STATISTICSGRAPH_HELP_URL = "/load-testing/reference/loadtest-graph.html";
    String STEP_AVERAGE_LOAD_TEST_ASSERTION_HELP_URL = "/load-testing/reference/assertion.html";
    String STEP_MAXIMUM_LOAD_TEST_ASSERTION_HELP_URL = "/load-testing/reference/assertion.html";
    String STEP_STATUS_LOAD_TEST_ASSERTION_HELP_URL = "/load-testing/reference/assertion.html";
    String STEP_TPS_LOAD_TEST_ASSERTION_HELP_URL = "/load-testing/reference/assertion.html";
    String STAY_TUNED = "/getting-started/help-in-soapui/help-in-soapui.html";
    String START_HERMES_HELP_URL = "/jms/getting-started.html";

    String TCPMON_HELP_URL = "http://ws.apache.org/commons/tcpmon/";
    String TEST_AMF_REQUEST_EDITOR_HELP_URL = "/amf/getting-started.html";
    String TESTCASEEDITOR_HELP_URL = "/functional-testing/structuring-and-running-tests.html";
    String TESTCASEOPTIONS_HELP_URL = "/functional-testing/reference/testcase-window.html";
    String TESTREQUESTEDITOR_HELP_URL = "/soap-and-wsdl/reference/request-interface.html";
    String TESTRUNNER_HELP_URL = "/Test-Automation/launch-testrunner.html";
    String TESTRUNNER_SECURITY_HELP_URL = "/test-automation/running-from-soapui/security-tests.html";
    String TESTSUITEEDITOR_HELP_URL = "/functional-testing/reference/testsuite-reference.html";
    String TESTSUITELIST_HELP_URL = "/Functional-Testing/project-testsuites-tab.html";
    String TESTSUITE_HELP_URL = "/Functional-Testing/structuring-and-running-tests.html";
    String TRANSFERSTEPEDITOR_HELP_URL = "/Functional-Testing/transferring-property-values.html";
    String TRIAL_URL = "/Downloads/download-soapui-pro-trial.html?utm_source=soapui&utm_medium=inproductupgrade&utm_campaign=protrial";

    String UPDATE_INTERFACE_HELP_URL = "/soap-and-wsdl/wsdl-refactoring.html";
    String USERGUIDE_HELP_URL = "/getting-started/help-in-soapui/help-in-soapui.html";

    String WADL2JAVA_HELP_URL = "https://wadl.java.net/wadl2java.html";
    String WADL_PARAMS_HELP_URL = "/rest-testing/rest-resources-and-methods.html";
    String WSCOMPILE_HELP_URL = "http://java.sun.com/webservices/docs/2.0/jaxrpc/jaxrpc-tools.html#wp80809";
    String WSDL_CONTENT_HELP_URL = "/SOAP-and-WSDL/working-with-wsdls.html";
    String WSIMPORT_HELP_URL = "http://java.sun.com/webservices/docs/2.0/jaxws/wsimport.html";
    String WSI_COMPLIANCE_HELP_URL = "/SOAP-and-WSDL/working-with-wsdls.html";
    String WSS_HELP_URL = "/SOAP-and-WSDL/applying-ws-security.html";
    String WSTOOLS_HELP_URL = "http://jbossas.jboss.org/docs";

    String XFIRE_HELP_URL = "http://xfire.codehaus.org/Client+and+Server+Stub+Generation+from+WSDL";
    String XMLBEANS_HELP_URL = "http://xmlbeans.apache.org/docs/2.0.0/guide/tools.html#scomp";
    String ASSERTION_XPATH_CONTENT = "/functional-testing/validating-messages/validating-xml-messages.html";
    String ASSERTION_XQUERY = "/functional-testing/validating-messages/validating-xml-messages.html";

    String ASSERTION_JSON_CONTENT = "/functional-testing/validating-messages/validating-json-messages.html";
    String ASSERTION_JSON_COUNT = "/functional-testing/validating-messages/validating-json-messages.html";
    String ASSERTION_JSON_EXIST = "/functional-testing/validating-messages/validating-json-messages.html";
    String ASSERTION_JSON_REGEX = "/functional-testing/validating-messages/validating-json-messages.html";
    String ASSERTION_JSON_REGEX_CONFIG = "/functional-testing/validating-messages/validating-json-messages.html";

    String REST_DISCOVERY_WITH_INTERNAL_BROWSER = "/REST-Discovery/api-with-internal-browser.html";
    String STARTER_PAGE_URL = "http://soapui.org/Appindex/soapui-starterpage-new.html?version=" + urlEncodeWithUtf8(SoapUI.SOAPUI_VERSION);

    String GRAPHQL_REQUEST_HELP_URL = "/docs/graphql-testing";
}
