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

package com.eviware.soapui.settings;

/**
 * Tools/Integration-related settings constants
 *
 * @author Ole.Matzura
 */

public interface ToolsSettings {
    String JWSDP_WSCOMPILE_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "jwsdp_wscompile";
    String JWSDP_WSIMPORT_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "jwsdp_wsimport";
    String JBOSSWS_WSTOOLS_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "jbossws_wstools";
    String JAVAC_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "javac";
    String AXIS_1_X_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "axis_1_X";
    String AXIS_2_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "axis_2";
    String DOTNET_WSDL_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "dotnet_wsdl";
    String XFIRE_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "xfire";
    String CXF_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "cxf";
    String ANT_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "ant";
    String GSOAP_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "gsoap";
    String XMLBEANS_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "xmlbeans";
    String JAXB_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "jaxb";
    String TCPMON_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "tcpmon";
    String ORACLE_WSA_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "wsa";
    String WADL2JAVA_LOCATION = ToolsSettings.class.getSimpleName() + "@" + "wadl2java";
    String HERMES_JMS = ToolsSettings.class.getSimpleName() + "@" + "hermesjms";
    // public static final String SCRIPT_LIBRARIES =
    // ToolsSettings.class.getSimpleName() + "@" + "libraries";
}
