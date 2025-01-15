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
 * Development
 */

public interface HelpUrlsDev {

    /*
    Help Sites
    */ String BASE_URL_PROD = "http://www.soapui.org";

    String BASE_URL_DEV = "http://dev.soapuidocs.sthlm.smartbear.local";

    String BASE_URL_NEXT = "http://next.soapuidocs.sthlm.smartbear.local";

    String MISSING_URL = "/missing_url";

    String THIRD_PARTY_LICENSE_INFO = "/developers_corner/3rd";

    String SMARTBEAR_WEB_SITE_START_PAGE = "http://www.smartbear.com";
    String SMARTBEAR_PRIVACY_POLICY_URL = "/store_info/privacy_policy";

    String ADDMOCKOPERATIONASMOCKRESPONSESTEP_HELP_URL = "/service_mocking/mocking_soap_services";
    String ADDMOCKRESPONSETOTESTCASE_HELP_URL = "/service_mocking/mocking_soap_services";
    String ADDREQUESTASMOCKRESPONSESTEP_HELP_URL = "/service_mocking/mocking_soap_services";
    String ADD_ASSERTION_PANEL = "/functional_testing/validating_messages/getting_started_with_assertions";
    String ADD_AUTHORIZATION = "/oauth/add_authorization";
    String ALERT_SITE_HELP_URL = "http://help.alertsite.com/webhome";
    String AMF_REQUEST_HEADERS_HELP_URL = "/amf/reference/the_amf_request_window";
    String API_TESTING_DOJO_HELP_URL = "/dojo/overview";
    String ATTACHMENTS_HELP_URL = "/soap_and_wsdl/adding_headers_and_attachments";
    String AUTHORIZATION = "/oauth/authorization";
    String AUTHORIZATION_BASIC = "/oauth/basic";
    String AUTHORIZATION_NTLM = "/oauth/ntlm";
    String AUTHORIZATION_OAUTH2 = "/oauth/oauth2";
    String AUTHORIZATION_SPNEGO_KERBEROS = "/oauth/spnego_kerberos";
    String AXIS1X_HELP_URL = "http://ws.apache.org/axis/java/reference.html#wsdl2javareference";
    String AXIS2X_HELP_URL = "http://ws.apache.org/axis2/tools/1_0/codegentoolreference.html";

    String CHANGEMOCKOPERATION_HELP_URL = "/soap_mocking/working_with_mockservices";
    String CHANGEOPERATION_HELP_URL = "/soap_and_wsdl/operations_and_requests";
    String CLONEMOCKSERVICE_HELP_URL = "/service_mocking/mocking_soap_services";
    String CLONETESTCASE_HELP_URL = "/functional_testing/reference/testsuite_reference";
    String CLONETESTSTEP_HELP_URL = "/functional_testing/structuring_and_running_tests";
    String CLONETESTSUITE_HELP_URL = "/functional_testing/structuring_and_running_tests";
    String CREATEMOCKRESPONSESTEP_HELP_URL = "/service_mocking/mocking_soap_services";
    String CRYPTOSWSS_HELP_URL = "/working_with_projects/ws_security";
    String CXFWSDL2JAVA_HELP_URL = "http://cxf.apache.org/docs/wsdl-to-java.html";

    String DEBUGGING_ASSERTION_TEST_STEP = "/functional_testing/assertion_test_step";
    String DEBUGGING_ENVIRONMENT_HANDLING = "/environments/environment_handling_in_soapui";
    String DEBUGGING_TESTCASE_DEBUGGING = "/functional_testing/testcase_debugging";
    String DOTNET_HELP_URL = "https://msdn.microsoft.com/en_us/library/7h3ystb6.aspx";

    String ENDPOINTSEDITOR_HELP_URL = "/soap_and_wsdl/working_with_wsdls";

    String FORUMS_HELP_URL = "http://community.smartbear.com/";
    String FUNCTIONAL_TESTING_SETUP_SCRIPT = "/functional_testing/working_with_scripts";
    String FUNCTIONAL_TESTING_TEARDOWN_SCRIPT = "/functional_testing/working_with_scripts";

    String GENERATE_MOCKSERVICE_HELP_URL = "/service_mocking/mocking_soap_services.html ";
    String GENERATE_REST_MOCKSERVICE = "/rest_testing_mocking/rest_mock_service_creation/rest_mock_from_service";
    String GENERATE_TESTSUITE_HELP_URL = "/functional_testing/structuring_and_running_tests";
    String GETTINGSTARTED_HELP_URL = "/getting_started/your_first_soapui_project";
    String GOTOSTEPEDITOR_HELP_URL = "/functional_testing/teststep_reference/conditional_goto/conditional_goto";
    String GROOVYASSERTION_HELP_URL = "/functional_testing/validating_messages/using_script_assertions";
    String GROOVYSTEPEDITOR_HELP_URL = "/functional_testing/working_with_scripts";
    String GSOAP_HELP_URL = "http://www.cs.fsu.edu/~engelen/soap.html";

    String HTTP_REQUEST_HELP_URL = "/functional_testing/teststep_reference/http_request/http_request";
    String HTTP_REQUEST_PARAMS_HELP_URL = "/functional_testing/teststep_reference/http_request/parameters";
    String HTTP_REQUEST_HEADERS_HELP_URL = "/functional_testing/teststep_reference/http_request/headers";

    String INCOMINGWSS_HELP_URL = "/soapui_projects/ws_security";
    String INTERFACE_HELP_URL = "/soap_and_wsdl/working_with_wsdls";
    String INTERFACE_OVERVIEW_HELP_URL = "/soap_and_wsdl/working_with_wsdls";

    String JABXJC_HELP_URL = "http://java.sun.com/webservices/docs/2.0/jaxb/xjc.html";
    String JBOSSWS_WSCONSUME_HELP_URL = "https://developer.jboss.org/wiki/jbossws_wsconsume";
    String JDBCSTEPEDITOR_HELP_URL = "/jdbc/getting_started";
    String JDBC_CONNECTION_HELP_URL = "/working_with_projects/jdbc_connections";

    String LOADTESTEDITOR_HELP_URL = "/load_testing/getting_started";
    String LOADTESTOPTIONS_HELP_URL = "/load_testing/reference/navigation";
    String LOADTEST_ASSERTIONS_URL = "/load_testing/validating_performance";

    String MANUALTESTSTEP_HELP_URL = "/functional_testing/teststep_reference/manual_teststep";
    String MAX_ERRORS_LOAD_TEST_ASSERTION_HELP_URL = "/load_testing/reference/assertion";
    String MOCKASWAR_HELP_URL = "/service_mocking/deploying_mock_services_as_war_files";
    String MOCKOPERATION_HELP_URL = "/service_mocking/simulating_complex_behaviour";
    String MOCKOPERATION_QUERYMATCHDISPATCH_HELP_URL = "/soap_mocking/working_with_mockservices";
    String MOCKOPERATION_SCRIPTDISPATCH_HELP_URL = "/soap_mocking/working_with_mockservices";
    String MOCKOPERATION_XPATHDISPATCH_HELP_URL = "/soap_mocking/working_with_mockservices";
    String MOCKRESPONSE_SCRIPT_HELP_URL = "/soap_mocking/mockoperations_and_responses";
    String MOCKSERVICEOPTIONS_HELP_URL = "/soap_mocking/working_with_mockservices";
    String MOCKSERVICE_HELP_URL = "/service_mocking/mocking_soap_services";

    String NEWPROJECT_HELP_URL = "/working_with_projects/new_project";
    String NEWRESTPROJECT_HELP_URL = "/rest_testing/getting_started";
    String NEWRESTSERVICE_HELP_URL = "/rest_testing/working_with_rest_services";
    String NEW_WADL_PROJECT_HELP_URL = "/rest_testing/working_with_rest_services";
    String CREATEWADLDOC_HELP_URL = "/rest_testing/working_with_rest_services";

    String OAUTH_ACCESS_TOKEN_FROM_SERVER = "/oauth/access_token_from_server";
    String OAUTH_ACCESS_TOKEN_RETRIEVAL = "/oauth/access_token_retrieval";
    String OAUTH_ADVANCED_OPTIONS = "/oauth/advanced_options";
    String OAUTH_AUTOMATED_TOKEN_PROFILE_EDITOR = "/oauth/automated_token_profile_editor";
    String OAUTH_AUTOMATING_ACCESS_TOKEN_RETRIEVAL = "/oauth/automating_access_token_retrieval";
    String OAUTH_OVERVIEW = "/oauth/oauth2_overview";
    String ORACLEWSA_HELP_URL = "/soap_and_wsdl/soap_code_generation";
    String OUTGOINGWSS_HELP_URL = "/soapui_projects/ws_security";

    String PREFERENCES_HELP_URL = "/working_with_soapui/preferences";
    String PREPAREDPROPERTIES_HELP_URL = "/soapui_projects/jdbc_connections";
    String PROJECT_OVERVIEW_HELP_URL = "/working_with_projects/working_with_soapui_projects";
    String PROPERTIESSTEPEDITOR_HELP_URL = "/functional_testing/properties/working_with_properties";
    String PROPERTY_TRANSFER_HELP_URL = "/functional_testing/transferring_property_values";

    String REQUESTEDITOR_HELP_URL = "/soap_and_wsdl/working_with_messages";
    String RESPONSE_ASSERTIONS_HELP_URL = "/functional_testing/getting_started_with_assertions";
    String RESTMETHODEDITOR_HELP_URL = "/rest_testing/rest_resources_and_methods";
    String RESTREQUESTEDITOR_HELP_URL = "/rest_testing/working_with_rest_requests";
    String RESTRESOURCEEDITOR_HELPURL = "/rest_testing/rest_resources_and_methods";
    String REST_MOCKSERVICE_ACTION = "/rest_service_mocking/mock_action_editor";
    String REST_MOCKSERVICE_HELP_URL = "/rest_service_mocking/mock_service_editor";
    String REST_MOCKSERVICE_OPTIONS = "/rest_service_mocking/mock_service_options";
    String REST_MOCK_RESPONSE_EDITOR = "/rest_service_mocking/mock_response_editor";
    String REST_MOCK_RESPONSE_EDITOR_BODY = "/rest_testing_mocking/reference/mock_response_editor";
    String REST_MOCK_RESPONSE_EDITOR_HEADER = "/rest_testing_mocking/reference/mock_response_editor";
    String REST_MOCK_RESPONSE_SCRIPT = "/rest_testing_mocking/reference/mock_response_editor";
    String REST_MOCK_SCRIPTDISPATCH = "/rest_testing_mocking/reference/mock_response_editor";
    String RUNTESTCASESTEP_HELP_URL = "/functional_testing/modularizing_your_tests";

    String SECURITYTESTEDITOR_HELP_URL = "/security/working_with_security_tests";
    String SECURITY_ASSERTION_HELP = "/security_testing/overview_of_security_scans";
    String SECURITY_INVALID_HTTP_CODES_ASSERTION_HELP = "/security_testing/overview_of_security_scans";
    String SECURITY_MALICIOUS_ATTACHMENT_HELP = "/security/malicious_attachment";
    String SECURITY_SCANS_OVERVIEW = "/security/security_scans_overview";
    String SECURITY_SENSITIVE_INFORMATION_EXPOSURE_ASSERTION_HELP = "/security_testing/overview_of_security_scans";
    String SECURITY_VALID_HTTP_CODES_ASSERTION_HELP = "/security_testing/overview_of_security_scans";
    String SECURITY_XSS_ASSERTION_HELP = "/security/cross_site_scripting";
    String SETMOCKOPERATION_HELP_URL = "/soap_mocking/reference/mockresponse";
    String SIMPLE_CONTAINS_HELP_URL = "/functional_testing/validating_messages/getting_started_with_assertions";
    String SIMPLE_NOT_CONTAINS_HELP_URL = "/functional_testing/validating_messages/getting_started_with_assertions";
    String SOAPMONITOR_GENERAL_OPTIONS = "/http_recording/general_options";
    String SOAPMONITOR_MONITOR = "/http_recording/monitor";
    String SOAPMONITOR_MONITOR_OPTIONS = "/http_recording/reference/general_options";
    String STATISTICSGRAPH_HELP_URL = "/load_testing/reference/loadtest_graph";
    String STEP_AVERAGE_LOAD_TEST_ASSERTION_HELP_URL = "/load_testing/reference/assertion";
    String STEP_MAXIMUM_LOAD_TEST_ASSERTION_HELP_URL = "/load_testing/reference/assertion";
    String STEP_STATUS_LOAD_TEST_ASSERTION_HELP_URL = "/load_testing/reference/assertion";
    String STEP_TPS_LOAD_TEST_ASSERTION_HELP_URL = "/load_testing/reference/assertion";
    String STAY_TUNED = "/getting_started/help_in_soapui/stay_tuned";

    String TCPMON_HELP_URL = "http://ws.apache.org/commons/tcpmon/";
    String TEST_AMF_REQUEST_EDITOR_HELP_URL = "/amf/getting_started";
    String TESTCASEEDITOR_HELP_URL = "/functional_testing/structuring_and_running_tests";
    String TESTCASEOPTIONS_HELP_URL = "/functional_testing/reference/testcase_window";
    String TESTREQUESTEDITOR_HELP_URL = "/soap_and_wsdl/reference/request_interface";
    String TESTRUNNER_HELP_URL = "/test_automation/launch_testrunner";
    String TESTSUITEEDITOR_HELP_URL = "/functional_testing/reference/testsuite_reference";
    String TESTSUITELIST_HELP_URL = "/functional_testing/project_testsuites_tab";
    String TESTSUITE_HELP_URL = "/functional_testing/structuring_and_running_tests";
    String TRANSFERSTEPEDITOR_HELP_URL = "/functional_testing/transferring_property_values";
    String TRIAL_URL = "/downloads/download_soapui_pro_trial";

    String UPDATE_INTERFACE_HELP_URL = "/soap_and_wsdl/wsdl_refactoring";
    String USERGUIDE_HELP_URL = "/getting_started/help_in_soapui/help_in_soapui";

    String WADL2JAVA_HELP_URL = "https://wadl.java.net/wadl2java.html";
    String WADL_PARAMS_HELP_URL = "/rest_testing/rest_resources_and_methods";
    String WSCOMPILE_HELP_URL = "http://java.sun.com/webservices/docs/2.0/jaxrpc/jaxrpc_tools.html#wp80809";
    String WSDL_CONTENT_HELP_URL = "/soap_and_wsdl/working_with_wsdls";
    String WSIMPORT_HELP_URL = "http://java.sun.com/webservices/docs/2.0/jaxws/wsimport.html";
    String WSI_COMPLIANCE_HELP_URL = "/soap_and_wsdl/working_with_wsdls";
    String WSS_HELP_URL = "/soap_and_wsdl/applying_ws_security";
    String WSTOOLS_HELP_URL = "http://jbossas.jboss.org/docs";

    String XFIRE_HELP_URL = "http://xfire.codehaus.org/client+and+server+stub+generation+from+wsdl";
    String XMLBEANS_HELP_URL = "http://xmlbeans.apache.org/docs/2.0.0/guide/tools.html#scomp";
    String XPATHASSERTIONEDITOR_HELP_URL = "/functional_testing/validating_messages/validating_xml_messages";
    String XQUERYASSERTIONEDITOR_HELP_URL = "/functional_testing/validating_messages/validating_xml_messages";

    String REST_DISCOVERY_WITH_INTERNAL_BROWSER = "/rest_discovery/api_with_internal_browser";
    String SOAPUI_WELCOME_PAGE = "/downloads/thank_you_for_downloading_soapui";
    String STARTER_PAGE_URL = "http://soapui.org/appindex/soapui_starterpage.html?version=" + urlEncodeWithUtf8(SoapUI.SOAPUI_VERSION);
} 

