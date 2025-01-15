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

package com.eviware.soapui.impl.wsdl.teststeps;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.config.JdbcRequestTestStepConfig;
import com.eviware.soapui.config.TestAssertionConfig;
import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.impl.wsdl.MutableTestPropertyHolder;
import com.eviware.soapui.impl.wsdl.panels.teststeps.JdbcRequest;
import com.eviware.soapui.impl.wsdl.panels.teststeps.JdbcResponse;
import com.eviware.soapui.impl.wsdl.panels.teststeps.JdbcSubmit;
import com.eviware.soapui.impl.wsdl.support.JdbcMessageExchange;
import com.eviware.soapui.impl.wsdl.support.XmlBeansPropertiesTestPropertyHolder;
import com.eviware.soapui.impl.wsdl.support.assertions.AssertableConfig;
import com.eviware.soapui.impl.wsdl.support.assertions.AssertedXPathsContainer;
import com.eviware.soapui.impl.wsdl.support.assertions.AssertionsSupport;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext;
import com.eviware.soapui.impl.wsdl.teststeps.assertions.TestAssertionRegistry.AssertableType;
import com.eviware.soapui.model.iface.Interface;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Submit;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionUtils;
import com.eviware.soapui.model.support.TestStepBeanProperty;
import com.eviware.soapui.model.testsuite.Assertable;
import com.eviware.soapui.model.testsuite.AssertionError;
import com.eviware.soapui.model.testsuite.AssertionsListener;
import com.eviware.soapui.model.testsuite.SamplerTestStep;
import com.eviware.soapui.model.testsuite.TestAssertion;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestPropertyListener;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus;
import com.eviware.soapui.support.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlAnyTypeImpl;

import javax.swing.*;
import javax.xml.namespace.QName;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * WsdlTestStep that executes a WsdlTestRequest
 *
 * @author dragica.soldo
 */

public class JdbcRequestTestStep extends WsdlTestStepWithProperties implements Assertable, MutableTestPropertyHolder, PropertyChangeListener, SamplerTestStep {
    public final static String JDBCREQUEST = JdbcRequestTestStep.class.getName() + "@jdbcrequest";
    public static final String STATUS_PROPERTY = WsdlTestRequest.class.getName() + "@status";
    public static final String RESPONSE_PROPERTY = "response";
    public static final String PASS_TEMPLATE = "PASS_VALUE";
    public static final String QUERY_FIELD = "SQL Query";
    protected static final String DRIVER_FIELD = "Driver";
    protected static final String CONNSTR_FIELD = "Connection String";
    protected static final String PASS_FIELD = "Password";
    protected static final String STOREDPROCEDURE_FIELD = "Stored Procedure";
    protected static final String DATA_CONNECTION_FIELD = "Connection";
    protected static final String QUERY_ELEMENT = "query";
    protected static final String STOREDPROCEDURE_ELEMENT = "stored-procedure";
    @SuppressWarnings("unused")
    private final static Logger log = LogManager.getLogger(WsdlTestRequestStep.class);
    private AssertionsSupport assertionsSupport;
    private PropertyChangeNotifier notifier;
    private final XmlBeansPropertiesTestPropertyHolder propertyHolderSupport;
    private JdbcRequestTestStepConfig jdbcRequestTestStepConfig;
    private final JdbcRequest jdbcRequest;
    private JdbcSubmit submit;

    public static boolean isNeededPassword(String connStr) {
        return !StringUtils.isNullOrEmpty(connStr) && connStr.contains(PASS_TEMPLATE);
    }

    public JdbcRequestTestStep(WsdlTestCase testCase, TestStepConfig config, boolean forLoadTest) {
        super(testCase, config, true, forLoadTest);

        if (getConfig().getConfig() != null) {
            jdbcRequestTestStepConfig = (JdbcRequestTestStepConfig)getConfig().getConfig().changeType(JdbcRequestTestStepConfig.type);
        }
        else {
            jdbcRequestTestStepConfig = (JdbcRequestTestStepConfig)getConfig().addNewConfig().changeType(JdbcRequestTestStepConfig.type);
        }

        if (jdbcRequestTestStepConfig.getProperties() == null) {
            jdbcRequestTestStepConfig.addNewProperties();
        }

        if (!jdbcRequestTestStepConfig.isSetConvertColumnNamesToUpperCase()) {
            jdbcRequestTestStepConfig.setConvertColumnNamesToUpperCase(true);
        }

        jdbcRequest = new JdbcRequest(this, forLoadTest);

        propertyHolderSupport = new XmlBeansPropertiesTestPropertyHolder(this, jdbcRequestTestStepConfig.getProperties());

        addResponseAsXmlVirtualProperty();

        initAssertions();
    }

    private void addResponseAsXmlVirtualProperty() {
        TestStepBeanProperty responseProperty = new TestStepBeanProperty(RESPONSE_AS_XML, true, this, "responseContent", this) {
            @Override
            public QName getType() {
                return getSchemaType().getName();
            }

            @Override
            public String getDefaultValue() {
                return "</no-response>";
            }

            @Override
            public SchemaType getSchemaType() {
                return XmlAnyTypeImpl.type;
            }
        };

        propertyHolderSupport.addVirtualProperty(RESPONSE_AS_XML, responseProperty);
    }

    public JdbcRequestTestStepConfig getJdbcRequestTestStepConfig() {
        return jdbcRequestTestStepConfig;
    }

    public TestStepResult run(TestCaseRunner runner, TestCaseRunContext runContext) {
        JdbcTestStepResult testStepResult = new JdbcTestStepResult(this);
        testStepResult.startTimer();
        runContext.setProperty(AssertedXPathsContainer.ASSERTEDXPATHSCONTAINER_PROPERTY, testStepResult);

        try {
            submit = jdbcRequest.submit(runContext, false);
            JdbcResponse response = submit.getResponse();

            if (submit.getStatus() != Submit.Status.CANCELED) {
                if (submit.getStatus() == Submit.Status.ERROR) {
                    testStepResult.setStatus(TestStepStatus.FAILED);
                    testStepResult.addMessage(submit.getError().toString());

                    jdbcRequest.setResponse(null);
                }
                else if (response == null) {
                    testStepResult.setStatus(TestStepStatus.FAILED);
                    testStepResult.addMessage("Request is missing response");

                    jdbcRequest.setResponse(null);
                }
                else {
                    runContext.setProperty(AssertedXPathsContainer.ASSERTEDXPATHSCONTAINER_PROPERTY, testStepResult);
                    jdbcRequest.setResponse(response);

                    testStepResult.setTimeTaken(response.getTimeTaken());
                    testStepResult.setSize(response.getContentLength());

                    switch (jdbcRequest.getAssertionStatus()) {
                        case FAILED:
                            testStepResult.setStatus(TestStepStatus.FAILED);
                            break;
                        case VALID:
                            testStepResult.setStatus(TestStepStatus.OK);
                            break;
                        case UNKNOWN:
                            testStepResult.setStatus(TestStepStatus.UNKNOWN);
                            break;
                    }

                    testStepResult.setResponse(response, testStepResult.getStatus() != TestStepStatus.FAILED);
                }
            }
            else {
                testStepResult.setStatus(TestStepStatus.CANCELED);
                testStepResult.addMessage("Request was canceled");
            }

            if (response != null) {
                testStepResult.setRequestContent(response.getRequestContent());
            }
            else {
                testStepResult.setRequestContent(jdbcRequest.getRequestContent());
            }

            testStepResult.stopTimer();
        }
        catch (SubmitException e) {
            testStepResult.setStatus(TestStepStatus.FAILED);
            testStepResult.addMessage("SubmitException: " + e);
            testStepResult.stopTimer();
        }
        finally {
            submit = null;
        }

        if (testStepResult.getStatus() != TestStepStatus.CANCELED) {
            assertResponse(runContext);

            AssertionStatus assertionStatus = jdbcRequest.getAssertionStatus();
            switch (assertionStatus) {
                case FAILED: {
                    testStepResult.setStatus(TestStepStatus.FAILED);
                    if (getAssertionCount() == 0) {
                        testStepResult.addMessage("Invalid/empty response");
                    }
                    else {
                        for (int c = 0; c < getAssertionCount(); c++) {
                            TestAssertion assertion = getAssertionAt(c);
                            AssertionError[] errors = assertion.getErrors();
                            if (errors != null) {
                                for (AssertionError error : errors) {
                                    testStepResult.addMessage("[" + assertion.getName() + "] " + error.getMessage());
                                }
                            }
                        }
                    }

                    break;
                }
                // default : testStepResult.setStatus( TestStepStatus.OK ); break;
            }
        }

        if (isDiscardResponse() && !SoapUI.getDesktop().hasDesktopPanel(this)) {
            jdbcRequest.setResponse(null);
        }

        // FIXME This should not be hard coded
        // FIXME This should not fire if the response is the same. Could we implement a property changed event handler instead?
        firePropertyValueChanged("ResponseAsXml", null, testStepResult.getResponseContentAsXml());
        return testStepResult;
    }

    private void initAssertions() {
        assertionsSupport = new AssertionsSupport(this, new AssertableConfig() {

            public List<TestAssertionConfig> getAssertionList() {
                return getJdbcRequestTestStepConfig().getAssertionList();
            }

            public void removeAssertion(int ix) {
                getJdbcRequestTestStepConfig().removeAssertion(ix);
            }

            public TestAssertionConfig addNewAssertion() {
                return getJdbcRequestTestStepConfig().addNewAssertion();
            }

            public TestAssertionConfig insertAssertion(TestAssertionConfig source, int ix) {
                TestAssertionConfig conf = getJdbcRequestTestStepConfig().insertNewAssertion(ix);
                conf.set(source);
                return conf;
            }
        });
    }

    public TestAssertion addAssertion(String assertionLabel) {
        PropertyChangeNotifier notifier = new PropertyChangeNotifier();

        try {
            WsdlMessageAssertion assertion = assertionsSupport.addWsdlAssertion(assertionLabel);
            if (assertion == null) {
                return null;
            }

            if (getJdbcRequest().getResponse() != null) {
                assertion.assertResponse(new JdbcMessageExchange(this, getJdbcRequest().getResponse()), new WsdlTestRunContext(this));
                notifier.notifyChange();
            }

            return assertion;
        }
        catch (Exception e) {
            SoapUI.logError(e);
            return null;
        }
    }

    public void addAssertionsListener(AssertionsListener listener) {
        assertionsSupport.addAssertionsListener(listener);
    }

    public int getAssertionCount() {
        return assertionsSupport.getAssertionCount();
    }

    public TestAssertion getAssertionAt(int c) {
        return assertionsSupport.getAssertionAt(c);
    }

    public void removeAssertionsListener(AssertionsListener listener) {
        assertionsSupport.removeAssertionsListener(listener);
    }

    public void removeAssertion(TestAssertion assertion) {
        PropertyChangeNotifier notifier = new PropertyChangeNotifier();

        try {
            assertionsSupport.removeAssertion((WsdlMessageAssertion)assertion);
        }
        finally {
            ((WsdlMessageAssertion)assertion).release();
            notifier.notifyChange();
        }
    }

    public AssertionStatus getAssertionStatus() {
        return jdbcRequest.getAssertionStatus();
    }

    public String getAssertableContentAsXml() {
        return getAssertableContent();
    }

    public String getAssertableContent() {
        TestProperty property = getProperty("ResponseAsXml");
        String value = property.getValue();
        return StringUtils.hasContent(value) ? value : property.getDefaultValue();
    }

    public String getDefaultAssertableContent() {
        return null;
    }

    public AssertableType getAssertableType() {
        return AssertableType.RESPONSE;
    }

    public List<TestAssertion> getAssertionList() {
        return new ArrayList<TestAssertion>(assertionsSupport.getAssertionList());
    }

    public TestAssertion getAssertionByName(String name) {
        return assertionsSupport.getAssertionByName(name);
    }

    public TestStep getTestStep() {
        return this;
    }

    public Interface getInterface() {
        return null;
    }

    public TestAssertion cloneAssertion(TestAssertion source, String name) {
        return assertionsSupport.cloneAssertion(source, name);
    }

    public Map<String, TestAssertion> getAssertions() {
        return assertionsSupport.getAssertions();
    }

    public TestAssertion moveAssertion(int ix, int offset) {
        PropertyChangeNotifier notifier = new PropertyChangeNotifier();
        TestAssertion assertion = getAssertionAt(ix);
        try {
            return assertionsSupport.moveAssertion(ix, offset);
        }
        finally {
            ((WsdlMessageAssertion)assertion).release();
            notifier.notifyChange();
        }
    }

    public boolean isConvertColumnNamesToUpperCase() {
        return jdbcRequestTestStepConfig.getConvertColumnNamesToUpperCase();
    }

    public void setConvertColumnNamesToUpperCase(boolean sp) {
        jdbcRequestTestStepConfig.setConvertColumnNamesToUpperCase(sp);
    }

    public String getResponseContent() {
        return getJdbcRequest().getResponse() == null ? "" : getJdbcRequest().getResponse().getContentAsString();
    }

    public WsdlMessageAssertion importAssertion(
        WsdlMessageAssertion source, boolean overwrite, boolean createCopy, String newName
    ) {
        return assertionsSupport.importAssertion(source, overwrite, createCopy, newName);
    }

    public void propertyChange(PropertyChangeEvent arg0) {
        if (arg0.getPropertyName().equals(TestAssertion.CONFIGURATION_PROPERTY) || arg0.getPropertyName().equals(TestAssertion.DISABLED_PROPERTY)) {
            if (getJdbcRequest().getResponse() != null) {
                assertResponse(new WsdlTestRunContext(this));
            }
        }
    }

    @Override
    public ImageIcon getIcon() {
        return jdbcRequest.getIcon();
    }

    @Override
    public void release() {
        super.release();
    }

    public void assertResponse(SubmitContext context) {
        try {
            if (notifier == null) {
                notifier = new PropertyChangeNotifier();
            }

            JdbcMessageExchange messageExchange = new JdbcMessageExchange(this, getJdbcRequest().getResponse());

            if (getJdbcRequest().getResponse() != null) {
                // assert!
                for (WsdlMessageAssertion assertion : assertionsSupport.getAssertionList()) {
                    assertion.assertResponse(messageExchange, context);
                }
            }

            notifier.notifyChange();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TestProperty addProperty(String name) {
        return propertyHolderSupport.addProperty(name);
    }

    public TestProperty removeProperty(String propertyName) {
        return propertyHolderSupport.removeProperty(propertyName);
    }

    public boolean renameProperty(String name, String newName) {
        return PropertyExpansionUtils.renameProperty(propertyHolderSupport.getProperty(name), newName, getTestCase()) != null;
    }

    public void removeAllProperties() {
        for (String propertyName : propertyHolderSupport.getPropertyNames()) {
            propertyHolderSupport.removeProperty(propertyName);
        }
    }

    @Override
    public String[] getPropertyNames() {
        return propertyHolderSupport.getPropertyNames();
    }

    @Override
    public TestProperty getProperty(String name) {
        return propertyHolderSupport.getProperty(name);
    }

    @Override
    public String getPropertyValue(String name) {
        return propertyHolderSupport.getPropertyValue(name);
    }

    @Override
    public void setPropertyValue(String name, String value) {
        propertyHolderSupport.setPropertyValue(name, value);
    }

    // FIXME Remove the overridden methods in TestPropertyHolder

    //	public void addTestPropertyListener( TestPropertyListener listener )
    //	{
    //		propertyHolderSupport.addTestPropertyListener( listener );
    //	}

    @Override
    public void addTestPropertyListener(TestPropertyListener listener) {
        propertyHolderSupport.addTestPropertyListener(listener);
    }

    @Override
    public void removeTestPropertyListener(TestPropertyListener listener) {
        propertyHolderSupport.removeTestPropertyListener(listener);
    }

    @Override
    public Map<String, TestProperty> getProperties() {
        return propertyHolderSupport.getProperties();
    }

    @Override
    public boolean hasProperty(String name) {
        return propertyHolderSupport.hasProperty(name);
    }

    @Override
    public TestProperty getPropertyAt(int index) {
        return propertyHolderSupport.getPropertyAt(index);
    }

    @Override
    public int getPropertyCount() {
        return propertyHolderSupport.getPropertyCount();
    }

    @Override
    public List<TestProperty> getPropertyList() {
        return propertyHolderSupport.getPropertyList();
    }

    @Override
    public void moveProperty(String propertyName, int targetIndex) {
        propertyHolderSupport.moveProperty(propertyName, targetIndex);
    }

    public void setPropertyValue(String name, Object value) {
        setPropertyValue(name, String.valueOf(value));
    }

    public String getDriver() {
        return jdbcRequestTestStepConfig.getDriver();
    }

    public void setDriver(String d) {
        String old = getDriver();
        jdbcRequestTestStepConfig.setDriver(d);
        notifyPropertyChanged("driver", old, d);
    }

    public String getConnectionString() {
        return jdbcRequestTestStepConfig.getConnectionString();
    }

    public void setConnectionString(String c) {
        String old = getConnectionString();
        jdbcRequestTestStepConfig.setConnectionString(c);
        notifyPropertyChanged("connectionString", old, c);
    }

    public String getQuery() {
        return jdbcRequestTestStepConfig.getQuery();
    }

    public void setQuery(String q) {
        String old = getQuery();
        jdbcRequestTestStepConfig.setQuery(q);
        notifyPropertyChanged("query", old, q);
    }

    public String getPassword() {
        return jdbcRequestTestStepConfig.getPassword();
    }

    public void setPassword(String p) {
        String old = getPassword();
        jdbcRequestTestStepConfig.setPassword(p);
        notifyPropertyChanged("password", old, p);
    }

    public boolean isStoredProcedure() {
        return jdbcRequestTestStepConfig.getStoredProcedure();
    }

    public void setStoredProcedure(boolean sp) {
        String old = getPassword();
        jdbcRequestTestStepConfig.setStoredProcedure(sp);
        notifyPropertyChanged("password", old, sp);
    }

    public JdbcRequest getJdbcRequest() {
        return jdbcRequest;
    }

    public String getQueryTimeout() {
        return jdbcRequestTestStepConfig.getQueryTimeout();
    }

    public void setQueryTimeout(String queryTimeout) {
        String old = getQueryTimeout();
        jdbcRequestTestStepConfig.setQueryTimeout(queryTimeout);
        notifyPropertyChanged("queryTimeout", old, queryTimeout);
    }

    public String getMaxRows() {
        return jdbcRequestTestStepConfig.getMaxRows();
    }

    public void setMaxRows(String maxRows) {
        String old = getMaxRows();
        jdbcRequestTestStepConfig.setMaxRows(maxRows);
        notifyPropertyChanged("maxRows", old, maxRows);
    }

    public String getFetchSize() {
        return jdbcRequestTestStepConfig.getFetchSize();
    }

    public void setFetchSize(String fetchSize) {
        String old = getFetchSize();
        jdbcRequestTestStepConfig.setFetchSize(fetchSize);
        notifyPropertyChanged("fetchSize", old, fetchSize);
    }

    public void setResponse(JdbcResponse response, SubmitContext context) {
        JdbcResponse oldResponse = jdbcRequest.getResponse();
        jdbcRequest.setResponse(response);

        notifyPropertyChanged(RESPONSE_PROPERTY, oldResponse, response);
        assertResponse(context);
    }

    public boolean isDiscardResponse() {
        return jdbcRequest.isDiscardResponse();
    }

    public void setDiscardResponse(boolean discardResponse) {
        jdbcRequest.setDiscardResponse(discardResponse);
    }

    public TestRequest getTestRequest() {
        return jdbcRequest;
    }

    @Override
    public void prepare(TestCaseRunner testRunner, TestCaseRunContext testRunContext) throws Exception {
        super.prepare(testRunner, testRunContext);

        setResponse(null, testRunContext);

        for (TestAssertion assertion : jdbcRequest.getAssertionList()) {
            assertion.prepare(testRunner, testRunContext);
        }
    }

    @Override
    public boolean cancel() {
        if (submit == null) {
            return false;
        }

        submit.cancel();

        return true;
    }

    @Override
    public void resetConfigOnMove(TestStepConfig config) {
        super.resetConfigOnMove(config);

        jdbcRequestTestStepConfig = (JdbcRequestTestStepConfig)config.getConfig().changeType(JdbcRequestTestStepConfig.type);
        propertyHolderSupport.resetPropertiesConfig(jdbcRequestTestStepConfig.getProperties());
        // addResponseAsXmlVirtualProperty();
        assertionsSupport.refresh();
    }

    @Override
    public WsdlTestStep clone(WsdlTestCase targetTestCase, String name) {
        beforeSave();

        TestStepConfig config = (TestStepConfig)getConfig().copy();
        JdbcRequestTestStep result = (JdbcRequestTestStep)targetTestCase.addTestStep(config);

        return result;
    }

    @Override
    public String getDefaultSourcePropertyName() {
        return "Response";
    }

    private class PropertyChangeNotifier {
        private AssertionStatus oldStatus;
        private ImageIcon oldIcon;

        public PropertyChangeNotifier() {
            oldStatus = getAssertionStatus();
            oldIcon = getIcon();
        }

        public void notifyChange() {
            AssertionStatus newStatus = getAssertionStatus();
            ImageIcon newIcon = getIcon();

            if (oldStatus != newStatus) {
                notifyPropertyChanged(STATUS_PROPERTY, oldStatus, newStatus);
            }

            if (oldIcon != newIcon) {
                notifyPropertyChanged(ICON_PROPERTY, oldIcon, getIcon());
            }

            oldStatus = newStatus;
            oldIcon = newIcon;
        }
    }
}
