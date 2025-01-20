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

package com.eviware.soapui

import com.eviware.soapui.actions.SaveAllProjectsAction
import com.eviware.soapui.actions.ShowSystemPropertiesAction
import com.eviware.soapui.actions.SoapUIPreferencesAction
import com.eviware.soapui.actions.SumbitUserInfoAction
import com.eviware.soapui.actions.SwitchDesktopPanelAction
import com.eviware.soapui.actions.VersionUpdateAction
import com.eviware.soapui.autoupdate.SoapUIAutoUpdaterUtils
import com.eviware.soapui.autoupdate.SoapUIUpdateProvider
import com.eviware.soapui.impl.RoundButton
import com.eviware.soapui.impl.WorkspaceImpl
import com.eviware.soapui.impl.actions.ImportWsdlProjectAction
import com.eviware.soapui.impl.actions.NewEmptyProjectAction
import com.eviware.soapui.impl.actions.NewRestProjectAction
import com.eviware.soapui.impl.actions.NewWsdlProjectAction
import com.eviware.soapui.impl.rest.actions.explorer.EndpointExplorerAction
import com.eviware.soapui.impl.rest.actions.project.NewRestServiceAction
import com.eviware.soapui.impl.support.actions.ShowOnlineHelpAction
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.impl.wsdl.actions.iface.tools.axis1.Axis1XWSDL2JavaAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.axis2.Axis2WSDL2CodeAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.cxf.CXFAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.dotnet.DotNetWsdlAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.gsoap.GSoapAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.jaxb.JaxbXjcAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.jbossws.JBossWSConsumeAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.jbossws.WSToolsWsdl2JavaAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.oracle.OracleWsaGenProxyAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.tcpmon.TcpMonAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.wscompile.WSCompileAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.wsimport.WSImportAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.xfire.XFireAction
import com.eviware.soapui.impl.wsdl.actions.iface.tools.xmlbeans.XmlBeans2Action
import com.eviware.soapui.impl.wsdl.actions.support.OpenUrlAction
import com.eviware.soapui.impl.wsdl.panels.teststeps.support.PropertyHolderTable
import com.eviware.soapui.impl.wsdl.support.HelpUrls
import com.eviware.soapui.impl.wsdl.support.http.ProxyUtils
import com.eviware.soapui.model.ModelItem
import com.eviware.soapui.model.PanelBuilder
import com.eviware.soapui.model.TestPropertyHolder
import com.eviware.soapui.model.environment.EnvironmentListener
import com.eviware.soapui.model.project.SaveStatus
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionUtils
import com.eviware.soapui.model.settings.Settings
import com.eviware.soapui.model.settings.SettingsListener
import com.eviware.soapui.model.tree.SoapUITreeNode
import com.eviware.soapui.model.util.PanelBuilderRegistry
import com.eviware.soapui.model.workspace.Workspace
import com.eviware.soapui.model.workspace.WorkspaceFactory
import com.eviware.soapui.monitor.MockEngine
import com.eviware.soapui.monitor.TestMonitor
import com.eviware.soapui.settings.ProxySettings
import com.eviware.soapui.settings.UISettings
import com.eviware.soapui.settings.VersionUpdateSettings
import com.eviware.soapui.support.DefaultHyperlinkListener
import com.eviware.soapui.support.SoapUIException
import com.eviware.soapui.support.StringUtils
import com.eviware.soapui.support.Tools
import com.eviware.soapui.support.UISupport
import com.eviware.soapui.support.action.SoapUIAction
import com.eviware.soapui.support.action.SoapUIActionRegistry
import com.eviware.soapui.support.action.swing.ActionList
import com.eviware.soapui.support.action.swing.ActionListBuilder
import com.eviware.soapui.support.action.swing.ActionSupport
import com.eviware.soapui.support.action.swing.SwingActionDelegate
import com.eviware.soapui.support.components.JComponentInspector
import com.eviware.soapui.support.components.JInspectorPanel
import com.eviware.soapui.support.components.JInspectorPanelFactory
import com.eviware.soapui.support.components.JPropertiesTable
import com.eviware.soapui.support.components.JXToolBar
import com.eviware.soapui.support.dnd.DropType
import com.eviware.soapui.support.dnd.NavigatorDragAndDropable
import com.eviware.soapui.support.dnd.SoapUIDragAndDropHandler
import com.eviware.soapui.support.factory.SoapUIFactoryRegistry
import com.eviware.soapui.support.jnlp.WebstartUtilCore
import com.eviware.soapui.support.listener.SoapUIListenerRegistry
import com.eviware.soapui.support.log.InspectorLog4JMonitor
import com.eviware.soapui.support.log.JLogList
import com.eviware.soapui.support.log.Log4JMonitor
import com.eviware.soapui.support.log.LogDisablingTestMonitorListener
import com.eviware.soapui.support.monitor.MonitorPanel
import com.eviware.soapui.support.monitor.RuntimeMemoryMonitorSource
import com.eviware.soapui.support.preferences.UserPreferences
import com.eviware.soapui.support.swing.MenuBuilderHelper
import com.eviware.soapui.support.swing.MenuScroller
import com.eviware.soapui.support.types.StringToStringMap
import com.eviware.soapui.tools.CmdLineRunner
import com.eviware.soapui.tools.SecureTools
import com.eviware.soapui.ui.JDesktopPanelsList
import com.eviware.soapui.ui.URLDesktopPanel
import com.eviware.soapui.ui.desktop.DesktopPanel
import com.eviware.soapui.ui.desktop.DesktopRegistry
import com.eviware.soapui.ui.desktop.NullDesktop
import com.eviware.soapui.ui.desktop.SoapUIDesktop
import com.eviware.soapui.ui.desktop.standalone.StandaloneDesktop
import com.eviware.soapui.ui.navigator.Navigator
import com.eviware.soapui.ui.navigator.NavigatorListener
import com.eviware.soapui.ui.support.DesktopListenerAdapter
import com.eviware.x.impl.swing.SwingDialogs
import com.jgoodies.forms.factories.ButtonBarFactory
import com.jgoodies.looks.HeaderStyle
import com.jgoodies.looks.Options
import javafx.application.Platform
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.CommandLineParser
import org.apache.commons.cli.PosixParser
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import javax.swing.*
import java.awt.*
import java.awt.dnd.DnDConstants
import java.awt.dnd.DragSource
import java.awt.event.ActionEvent
import java.awt.event.InputEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.lang.management.ManagementFactory
import java.util.List
import java.util.Timer
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.prefs.BackingStoreException

import static com.eviware.soapui.impl.support.HttpUtils.urlEncodeWithUtf8
import static com.eviware.soapui.settings.UISettings.SHOW_ENDPOINT_EXPLORER_ON_START
import static com.eviware.soapui.settings.UISettings.SHOW_STAY_TUNED_DIALOG
import static org.apache.commons.lang3.StringUtils.defaultString

/**
 * Main SoapUI entry point.
 */
class SoapUI {
    // ------------------------------ CONSTANTS ------------------------------
    public static final String DEFAULT_DESKTOP = "Default"
    public static final String CURRENT_SOAPUI_WORKSPACE = SoapUI.class.name + "@workspace"
    public final static Logger log = LogManager.getLogger(SoapUI.class)
    public final static String PRODUCT_NAME = "SoapUI"
    public static final String DEFAULT_WORKSPACE_FILE = "default-soapui-workspace.xml"
    public static final String SOAPUI_SPLASH = "SoapUI-Spashscreen.png"
    public static final String SOAPUI_ABOUT = "SoapUI-blank.png"
    public static final String SOAPUI_TITLE = "/branded/branded.properties"
    public static final String PROPERTIES_TAB_PANEL_NAME = "PropertiesTabPanel"
    public static final String BUILDINFO_PROPERTIES = "/buildinfo.properties"
    public final static String SOAPUI_VERSION = version
    public static final String STARTER_PAGE_HEADER = "SoapUI Start Page"
    public static final String STARTER_PAGE_TOOL_TIP = "Info on SoapUI"
    public static final String PROJECT = "Project"
    public static final String SUITE = "Suite"
    public static final String STEP = "Step"
    public static final String CASE = "Case"
    public static final String ENABLED_PROJECT_ACTIONS = "EnabledWsdlProjectActions"
    public static final String TEST_SUITE_ACTIONS = "WsdlTestSuiteActions"
    public static final String TEST_CASE_ACTIONS = "WsdlTestCaseActions"
    public static final String TEST_STEP_ACTIONS = "WsdlTestStepActions"
    public static final String BACKUP_STARTER_PAGE_URL = "/starter-page/starter-page.html"
    private static final String PROXY_ENABLED_ICON = "/Proxy_Turned-on.png"
    private static final String PROXY_DISABLED_ICON = "/Proxy_Turned-off.png"
    private static final int DEFAULT_DESKTOP_ACTIONS_COUNT = 3
    private static final int DEFAULT_MAX_THREADPOOL_SIZE = 200
    private static final String BROWSER_DISABLED_SYSTEM_PROPERTY = "soapui.browser.disabled"
    final static ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(maxThreadpoolSize, new SoapUIThreadCreator())
    private static final List<Object> logCache = new ArrayList<>()
    static final Timer soapUITimer = new Timer()
    static final Logger errorLog = LogManager.getLogger("soapui.errorlog")
    private static final StringToStringMap projectOptions = new StringToStringMap()
    // ------------------------------ FIELDS ------------------------------
    public static String FRAME_ICON = "/SoapUI-OS_16-16.png;/SoapUI-OS_24-24.png;/SoapUI-OS_32-32.png;/SoapUI-OS_48-48.png;/SoapUI-OS_256-256.png"
    public static String STARTER_PAGE_ERROR_URL = "file://" + System.getProperty("soapui.home", ".") + "/starter-page.html"
    static SoapUICore soapUICore
    static JFrame frame
    static Navigator navigator
    static SoapUIDesktop desktop
    static Workspace workspace
    static Log4JMonitor logMonitor
    private static boolean isStandalone
    private static boolean isCommandLine
    private static TestMonitor testMonitor
    static JMenuBar menuBar
    private static Boolean checkedGroovyLogMonitor = false
    private static Boolean launchedTestRunner = false
    private static AutoSaveTimerTask autoSaveTimerTask
    private static String workspaceName
    private static URLDesktopPanel starterPageDesktopPanel
    static JXToolBar mainToolbar
    static String[] mainArgs
    private static GCTimerTask gcTimerTask
    private static JPanel endpointExplorerButtonPanel
    private static JButton endpointExplorerButton
    private static JToggleButton applyProxyButton
    private static Logger groovyLogger
    private static CmdLineRunner soapUIRunner

    static {
        try {
            Platform.implicitExit = false
        }
        catch (NoClassDefFoundError e) {
            log.warn("Could not find jfxrt.jar. Internal browser will be disabled.")
            System.setProperty(BROWSER_DISABLED_SYSTEM_PROPERTY, Boolean.TRUE.toString())
        }
    }

    private final InternalDesktopListener internalDesktopListener = new InternalDesktopListener()
    private JMenu desktopMenu
    private JDesktopPanelsList desktopPanelsList
    private JPanel overviewPanel
    private boolean saveOnExit = true
    private JInspectorPanel mainInspector
    private JTextField searchField

    // --------------------------- CONSTRUCTORS ---------------------------

    static boolean usingGraphicalEnvironment() {
        return !UISupport.headless && !commandLine
    }

    //TODO Replace with the community API-based search
    static void doCommunitySearch(String text) {

        String prefix = "/t5/forums/searchpage/tab/message?include_forums=true"
        String forum = "location=board%3ASoapUI_OS"
        String suffix = "&search_type=thread&filter=labels%2Clocation"

        String searchText = "&q=" + urlEncodeWithUtf8(text.trim())

        String searchUrl = HelpUrls.COMMUNITY_SEARCH_URL + prefix + forum + searchText + suffix

        if (StringUtils.hasContent(text)) {
            Tools.openURL(searchUrl)
        }
        else {
            Tools.openURL(HelpUrls.COMMUNITY_SEARCH_URL)
        }
    }

    static JComponent initLogMonitor(boolean hasDefault, String defaultName, Log4JMonitor logMonitor) {
        SoapUI.logMonitor = logMonitor
        logMonitor.addLogArea(defaultName, "com.eviware.soapui", hasDefault).level = Level.DEBUG
        logMonitor.addLogArea("http log", "org.apache.http.wire", false).level = Level.DEBUG
        logMonitor.addLogArea("jetty log", "jetty", false).level = Level.INFO
        logMonitor.addLogArea("error log", "soapui.errorlog", false).level = Level.DEBUG
        logMonitor.addLogArea("wsrm log", "wsrm", false).level = Level.INFO

        for (Object message : logCache) {
            logMonitor.logEvent(message)
        }

        return logMonitor.component
    }

    static boolean isSelectingMostRecentlyUsedDesktopPanelOnClose() {
        return settings.getBoolean(UISettings.MRU_PANEL_SELECTOR, true)
    }

    static synchronized void log(Object msg) {
        if (logMonitor == null) {
            if (!isCommandLine && logCache.size() < 1000) {
                logCache.add(msg)
            }

            return
        }

        if (SwingUtilities.eventDispatchThread) {
            logMonitor.logEvent(msg)
        }
        else {
            SwingUtilities.invokeLater(() -> logMonitor.logEvent(msg))
        }
    }

    static void main(String[] args) throws Exception {
        WebstartUtilCore.init()
        setBackgroundsToWhite()
        mainArgs = args

        SecureTools.setTrustSSL()
        SoapUIRunner soapuiRunner = new SoapUIRunner()
        SwingUtilities.invokeLater(soapuiRunner)
    }

    static void startSoapUI(String[] args, String title, SwingSoapUICore core) throws Exception {
        System.setProperty("apple.laf.useScreenMenuBar", "true")
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "SoapUI")

        frame = new JFrame(title)

        frame.iconImages = frameIcons

        JPopupMenu.defaultLightWeightPopupEnabled = false
        ToolTipManager.sharedInstance().lightWeightPopupEnabled = false

        isStandalone = true
        soapUICore = core

        SoapUI soapUI = new SoapUI()
        Workspace workspace = null

        org.apache.commons.cli.Options options = initSoapUIOptions()
        CommandLineParser parser = new PosixParser()
        CommandLine cmd = parser.parse(options, args)

        if (!processCommandLineArgs(cmd)) {
            System.exit(1)
        }
        if (workspaceName) {
            workspace = WorkspaceFactory.instance.openWorkspace(workspaceName, projectOptions)
            soapUICore.settings.setString(CURRENT_SOAPUI_WORKSPACE, workspaceName)
        }
        else {
            String workspaceFile = soapUICore.settings.getString(CURRENT_SOAPUI_WORKSPACE, System.getProperty("user.home") + File.separatorChar + DEFAULT_WORKSPACE_FILE)
            UISupport.dialogs = new SwingDialogs(null)
            try {
                workspace = WorkspaceFactory.instance.openWorkspace(workspaceFile, projectOptions)
            }
            catch (Exception e) {
                if (UISupport.confirm("Failed to open workspace: [" + e + "], create new one instead?", "Error")) {
                    new File(workspaceFile).renameTo(new File(workspaceFile + ".bak"))
                    workspace = WorkspaceFactory.instance.openWorkspace(workspaceFile, projectOptions)
                }
                else {
                    System.exit(1)
                }
            }
        }

        core.prepareUI()
        soapUI.show(workspace)
        new WindowInitializationTask().run()
        core.afterStartup(workspace)

        String[] args2 = cmd.args
        if (args2 && args2.length > 0) {
            String arg = args2[0]
            if (arg.toUpperCase().endsWith(".WSDL") || arg.toUpperCase().endsWith(".WADL")) {
                SwingUtilities.invokeLater(new WsdlProjectCreator(arg))
            }
            else {
                try {
                    URL url = new URL(arg)
                    SwingUtilities.invokeLater(new RestProjectCreator(url))
                }
                catch (Exception ignore) {
                }
            }
        }

        if (usingGraphicalEnvironment()) {
            if (workspace.supportInformationDialog || settings.getBoolean(SHOW_STAY_TUNED_DIALOG, true)) {
                SumbitUserInfoAction collector = new SumbitUserInfoAction()
                collector.show()
                settings.setBoolean(SHOW_STAY_TUNED_DIALOG, false)
                workspace.supportInformationDialog = false
            }
            if (settings.getBoolean(SHOW_ENDPOINT_EXPLORER_ON_START, true)) {
                showEndpointExplorer()
            }
        }
    }

    static List<Image> getFrameIcons() {
        List<Image> iconList = new ArrayList<>()
        for (String iconPath : FRAME_ICON.split(";")) {
            iconList.add(UISupport.createImageIcon(iconPath).image)
        }
        return iconList
    }

    static void setSoapUICore(SoapUICore soapUICore) {
        setSoapUICore(soapUICore, false)
    }

    static TestPropertyHolder getGlobalProperties() {
        return PropertyExpansionUtils.globalProperties
    }

    static void setSoapUICore(SoapUICore soapUICore, boolean isCommandLine) {
        this.soapUICore = soapUICore
        this.isCommandLine = isCommandLine
    }

    static boolean isStandalone() {
        return isStandalone
    }

    static void setStandalone(boolean standalone) {
        isStandalone = standalone
    }

    static boolean isCommandLine() {
        return isCommandLine
    }

    static void shutdown() {
        soapUITimer.cancel()
    }

    static void logError(Throwable e) {
        logError(e, null)
    }

    static void logError(Throwable e, String message) {
        String msg = e.message
        if (msg == null) {
            msg = e.toString()
        }

        log.error("An error occurred [{}], see error log for details", msg)

        try {
            if (message) {
                errorLog.error(message)
            }

            errorLog.error(e.toString(), e)
        }
        catch (OutOfMemoryError e1) {
            e1.printStackTrace()
            System.gc()
        }
        if (!standalone || "true" == System.getProperty("soapui.stacktrace")) {
            e.printStackTrace()
        }
    }

    static Logger ensureGroovyLog() {
        synchronized (threadPool) {
            if (!checkedGroovyLogMonitor || launchedTestRunner) {
                groovyLogger = LogManager.getLogger("groovy.log")

                Log4JMonitor logMonitor = logMonitor
                if (logMonitor && !logMonitor.hasLogArea("groovy.log")) {
                    logMonitor.addLogArea("script log", "groovy.log", false)
                    checkedGroovyLogMonitor = true
                }
                else if (logMonitor == null && launchedTestRunner) {
                    checkedGroovyLogMonitor = true
                    launchedTestRunner = false
                }
            }
        }

        return groovyLogger
    }

    static boolean isBrowserDisabled() {
        return Boolean.parseBoolean(System.getProperty(BROWSER_DISABLED_SYSTEM_PROPERTY))
    }

    static void updateProxyButtonAndTooltip() {
        if (applyProxyButton == null) {
            return
        }
        applyProxyButton.verticalTextPosition = SwingConstants.BOTTOM
        applyProxyButton.horizontalTextPosition = SwingConstants.CENTER
        if (ProxyUtils.proxyEnabled) {
            applyProxyButton.icon = UISupport.createImageIcon(PROXY_ENABLED_ICON)
            if (ProxyUtils.autoProxy) {
                applyProxyButton.action.putValue(Action.SHORT_DESCRIPTION, "Proxy Setting: Automatic")
            }
            else {
                applyProxyButton.action.putValue(Action.SHORT_DESCRIPTION, "Proxy Setting: Manual")
            }
        }
        else {
            applyProxyButton.icon = UISupport.createImageIcon(PROXY_DISABLED_ICON)
            applyProxyButton.action.putValue(Action.SHORT_DESCRIPTION, "Proxy Setting: None")
        }
        applyProxyButton.selected = ProxyUtils.proxyEnabled
        UIManager.put("ToggleButton.select", Color.WHITE)
        SwingUtilities.updateComponentTreeUI(applyProxyButton)
    }

    static void showStarterPage() {
        if (starterPageDesktopPanel == null || starterPageDesktopPanel.closed) {
            try {
                starterPageDesktopPanel = new URLDesktopPanel(STARTER_PAGE_HEADER, STARTER_PAGE_TOOL_TIP, null, SoapUI.class.getResource(BACKUP_STARTER_PAGE_URL).toString())
            }
            catch (Exception e) {
                logError(e)
                return
            }
        }

        UISupport.showDesktopPanel(starterPageDesktopPanel)
        starterPageDesktopPanel.navigate(HelpUrls.STARTER_PAGE_URL, SoapUI.class.getResource(BACKUP_STARTER_PAGE_URL).toString(), true)
    }

    static TestMonitor getTestMonitor() {
        if (testMonitor == null) {
            testMonitor = new TestMonitor()
        }

        return testMonitor
    }

    // -------------------------- OTHER METHODS --------------------------

    static void setTestMonitor(TestMonitor monitor) {
        testMonitor = monitor
    }

    // -------------------------- INNER CLASSES --------------------------

    // instance is null in Eclipse. /Lars
    // eclipse-version(s) should provide SoapUIDesktop implementation
    static SoapUIDesktop getDesktop() {
        if (desktop == null) {
            desktop = new NullDesktop()
        }

        return desktop
    }

    static SoapUIActionRegistry getActionRegistry() {
        if (soapUICore == null) {
            soapUICore = new DefaultSoapUICore()
        }

        return soapUICore.actionRegistry
    }

    static SoapUIListenerRegistry getListenerRegistry() {
        if (soapUICore == null) {
            soapUICore = DefaultSoapUICore.createDefault()
        }

        return soapUICore.listenerRegistry
    }

    static SoapUIFactoryRegistry getFactoryRegistry() {
        if (soapUICore == null) {
            soapUICore = DefaultSoapUICore.createDefault()
        }

        return soapUICore.factoryRegistry
    }

    static Settings getSettings() {
        if (soapUICore == null) {
            soapUICore = DefaultSoapUICore.createDefault()
        }

        return soapUICore.settings
    }

    static void importPreferences(File file) throws Exception {
        if (soapUICore) {
            soapUICore.importSettings(file)
        }
    }

    static MockEngine getMockEngine() {
        if (soapUICore == null) {
            soapUICore = DefaultSoapUICore.createDefault()
        }

        return soapUICore.mockEngine
    }

    static String saveSettings() throws Exception {
        return soapUICore?.saveSettings()
    }

    static void initDefaultCore() {
        if (soapUICore == null) {
            soapUICore = DefaultSoapUICore.createDefault()
        }
    }

    static void initAutoSaveTimer() {
        long interval = settings.getLong(UISettings.AUTO_SAVE_INTERVAL, 0)

        if (autoSaveTimerTask) {
            if (interval == 0) {
                log("Cancelling AutoSave Timer")
            }

            autoSaveTimerTask.cancel()
            autoSaveTimerTask = null
        }

        if (interval > 0) {
            autoSaveTimerTask = new AutoSaveTimerTask()

            log("Scheduling autosave every " + interval + " minutes")

            soapUITimer.schedule(autoSaveTimerTask, interval * 1000 * 60, interval * 1000 * 60)
        }
    }

    static void initGCTimer() {
        long interval = settings.getLong(UISettings.GC_INTERVAL, 60)

        if (gcTimerTask) {
            if (interval == 0) {
                log("Cancelling GC Timer")
            }

            gcTimerTask.cancel()
            gcTimerTask = null
        }

        if (interval > 0) {
            gcTimerTask = new GCTimerTask()
            log("Scheduling garbage collection every " + interval + " seconds")
            soapUITimer.schedule(gcTimerTask, interval * 1000, interval * 1000)
        }
    }

    static void setLaunchedTestRunner(Boolean launchedTestRunner) {
        SoapUI.launchedTestRunner = launchedTestRunner
    }

    static void updateProxyFromSettings() {
        ProxyUtils.proxyEnabled = settings.getBoolean(ProxySettings.ENABLE_PROXY)
        ProxyUtils.autoProxy = settings.getBoolean(ProxySettings.AUTO_PROXY)
        ProxyUtils.globalProxy = settings
        updateProxyButtonAndTooltip()
    }

    static CmdLineRunner getCmdLineRunner() {
        return soapUIRunner
    }

    static void setCmdLineRunner(CmdLineRunner abstractSoapUIRunner) {
        soapUIRunner = abstractSoapUIRunner
    }

    static boolean isAutoUpdateVersion() {
        return settings.getBoolean(VersionUpdateSettings.AUTO_CHECK_VERSION_UPDATE)
    }

    private SoapUI() {
    }

    static String getVersion() {
        String version = System.getProperty(SoapUISystemProperties.VERSION)
        if (version) {
            return version
        }
        version = SoapUI.class.package.implementationVersion
        if (version) {
            return version
        }
        try {
            Properties buildInfoProperties = new Properties()
            buildInfoProperties.load(SoapUI.class.getResourceAsStream(BUILDINFO_PROPERTIES))
            version = buildInfoProperties.getProperty("version")
            if (!StringUtils.isNullOrEmpty(version)) {
                return version
            }
        }
        catch (Exception exception) {
            //ignore
        }
        return "UNKNOWN VERSION"
    }

    private void buildUI() {
        log.info("Used java version: {}", System.getProperty("java.version"))
        frame.addWindowListener(new MainFrameWindowListener())
        UISupport.mainFrame = frame
        navigator = new Navigator(workspace)
        navigator.addNavigatorListener(new InternalNavigatorListener())
        desktopPanelsList = new JDesktopPanelsList(desktop)

        mainInspector = JInspectorPanelFactory.build(buildContentPanel(), SwingConstants.LEFT)
        mainInspector.addInspector(new JComponentInspector<>(buildMainPanel(), "Navigator", "The SoapUI Navigator", true))
        mainInspector.currentInspector = "Navigator"
        frame.JMenuBar = buildMainMenu()
        frame.contentPane.add(buildToolbar(), BorderLayout.NORTH)
        frame.contentPane.add(mainInspector.component, BorderLayout.CENTER)
        frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE

        mainInspector.dividerLocation = 250
        mainInspector.resizeWeight = 0.1
        navigator.selectModelItem(workspace)

        desktop.addDesktopListener(internalDesktopListener)

        ToolTipManager.sharedInstance().initialDelay = 200

        JTree mainTree = navigator.mainTree
        DragSource dragSource = DragSource.defaultDragSource
        SoapUIDragAndDropHandler navigatorDragAndDropHandler = new SoapUIDragAndDropHandler(new NavigatorDragAndDropable(mainTree), DropType.ON + DropType.AFTER)

        dragSource.createDefaultDragGestureRecognizer(mainTree, DnDConstants.ACTION_COPY_OR_MOVE, navigatorDragAndDropHandler)

        desktop.init()
    }

    private JComponent buildToolbar() {
        mainToolbar = new JXToolBar()
        UISupport.setPreferredHeight(mainToolbar, JXToolBar.MAIN_COMPONENT_HEIGHT)
        mainToolbar.floatable = false
        mainToolbar.rollover = true
        mainToolbar.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH)
        mainToolbar.addSpace(20)
        mainToolbar.add(new NewProjectActionDelegate("/new-empty-project-icon.png", "Empty", NewEmptyProjectAction.SOAPUI_ACTION_ID))
        mainToolbar.add(new NewProjectActionDelegate("/new-soap-project-icon.png", "SOAP", NewWsdlProjectAction.SOAPUI_ACTION_ID))
        mainToolbar.add(new NewProjectActionDelegate("/new-rest-project-icon.png", "REST", NewRestProjectAction.SOAPUI_ACTION_ID))
        mainToolbar.add(new ImportWsdlProjectActionDelegate())
        mainToolbar.add(new SaveAllActionDelegate())
        mainToolbar.addSpace(2)
        mainToolbar.add(new ShowOnlineHelpAction("Forum", HelpUrls.COMMUNITY_HELP_URL, "Opens the SoapUI Forum in a browser", "/forum.png"))
        mainToolbar.addSpace(2)
        mainToolbar.add(new ShowOnlineHelpAction("Trial", HelpUrls.TRIAL_URL, "Apply for ReadyAPI Trial License", "/Trial_20-20.png"))
        mainToolbar.add(new PreferencesActionDelegate())
        applyProxyButton = (JToggleButton)mainToolbar.add(new JToggleButton(new ApplyProxyButtonAction()))
        updateProxyButtonAndTooltip()
        mainToolbar.addSpace(15)
        createToolbarSeparator()
        mainToolbar.addSpace(10)
        createEndpointExplorerButton()
        endpointExplorerButtonPanel.add(endpointExplorerButton)
        mainToolbar.add(endpointExplorerButtonPanel)
        mainToolbar.addSpace(10)
        createToolbarSeparator()

        mainToolbar.addGlue()
        searchField = new JTextField(20) {
            @Override
            void paintComponent(Graphics g) {
                super.paintComponent(g)
                g.color = Color.LIGHT_GRAY
                g.drawRect(0, 0, width - 1, height - 1)
            }
        }
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            void keyTyped(KeyEvent e) {
                if (e.keyChar == '\n') {
                    doCommunitySearch(searchField.text)
                }
            }
        })

        JLabel searchLabel = new JLabel("Search Forum")
        // Extra width to avoid label to be truncated
        searchLabel.preferredSize = new Dimension((int)(searchLabel.preferredSize.width * 1.1), (int)searchLabel.preferredSize.height)
        mainToolbar.addFixed(searchLabel)
        mainToolbar.addSeparator(new Dimension(3, 3))
        mainToolbar.addFixed(searchField)
        mainToolbar.add(new ToolbarForumSearchAction())
        mainToolbar.add(new ShowOnlineHelpAction(HelpUrls.USERGUIDE_HELP_URL))
        for (int i = 0; i < mainToolbar.componentCount; i++) {
            if (mainToolbar.getComponent(i) instanceof JComponent) {
                ((JComponent)mainToolbar.getComponent(i)).border = BorderFactory.createEmptyBorder(4, 2, 4, 2)
            }
        }
        mainToolbar.border = BorderFactory.createEmptyBorder()
        return mainToolbar
    }

    private void createEndpointExplorerButton() {
        endpointExplorerButtonPanel = new JPanel(new BorderLayout())
        endpointExplorerButtonPanel.preferredSize = new Dimension(130, 32)
        endpointExplorerButtonPanel.maximumSize = new Dimension(130, 32)
        endpointExplorerButton = new RoundButton(6)
        endpointExplorerButton.foreground = Color.WHITE
        endpointExplorerButton.background = new Color(52, 137, 209)
        endpointExplorerButton.text = "Endpoint Explorer"
        if (UISupport.mac) {
            endpointExplorerButton.opaque = false
        }
        EndpointExplorerAction action = new EndpointExplorerAction()
        endpointExplorerButton.addActionListener(action)
        endpointExplorerButton.addMouseListener(new MouseListener() {

            void mouseClicked(MouseEvent e) {
            }

            void mousePressed(MouseEvent e) {
            }

            void mouseReleased(MouseEvent e) {
            }

            @Override
            void mouseEntered(MouseEvent e) {
                endpointExplorerButton.background = new Color(39, 104, 158)
                endpointExplorerButton.repaint()
            }

            @Override
            void mouseExited(MouseEvent e) {
                endpointExplorerButton.background = new Color(52, 137, 209)
                endpointExplorerButton.repaint()
            }
        })
    }

    private void createToolbarSeparator() {
        JPanel separatorPanel = new JPanel(new BorderLayout())
        separatorPanel.preferredSize = new Dimension(5, 40)
        separatorPanel.maximumSize = new Dimension(5, 40)
        JSeparator separator = new JSeparator(orientation: JSeparator.VERTICAL, background: new Color(112, 112, 112))
        separator.setLocation(10, 0)
        separatorPanel.add(separator)
        mainToolbar.add(separatorPanel)
    }

    private JMenuBar buildMainMenu() {
        menuBar = new JMenuBar() {
            @Override
            void paintComponent(Graphics g) {
                super.paintComponent(g)
                g.color = Color.WHITE
                g.fillRect(0, 0, width, height)
                g.color = Color.LIGHT_GRAY
                g.drawLine(0, height - 1, width, height - 1)
            }
        }
        menuBar.border = BorderFactory.createEmptyBorder()
        menuBar.add(buildFileMenu())
        menuBar.add(buildProjectMenu())
        menuBar.add(buildSuiteMenu())
        menuBar.add(buildCaseMenu())
        menuBar.add(buildStepMenu())
        menuBar.add(buildToolsMenu())
        menuBar.add(buildDesktopMenu())
        menuBar.add(buildHelpMenu())
        return menuBar
    }

    private JMenu buildDesktopMenu() {
        desktopMenu = new JMenu("Desktop")
        desktopMenu.mnemonic = KeyEvent.VK_D
        desktopMenu.add(new SwitchDesktopPanelAction(desktopPanelsList))
        desktopMenu.add(new MaximizeDesktopAction((InspectorLog4JMonitor)logMonitor))
        desktopMenu.addSeparator()
        ActionSupport.addActions(desktop.actions, desktopMenu)
        return desktopMenu
    }

    private JMenu buildProjectMenu() {
        return MenuBuilderHelper.buildMenuForWorkspace(new JMenu(PROJECT), ENABLED_PROJECT_ACTIONS)
    }

    private JMenu buildSuiteMenu() {
        return MenuBuilderHelper.buildMenuForWorkspace(new JMenu(SUITE), TEST_SUITE_ACTIONS)
    }

    private JMenu buildCaseMenu() {
        return MenuBuilderHelper.buildMenuForWorkspace(new JMenu(CASE), TEST_CASE_ACTIONS)
    }

    private JMenu buildStepMenu() {
        return MenuBuilderHelper.buildMenuForWorkspace(new JMenu(STEP), TEST_STEP_ACTIONS)
    }

    private JMenu buildHelpMenu() {
        JMenu helpMenu = new JMenu("Help")
        helpMenu.mnemonic = KeyEvent.VK_H

        helpMenu.add(new ShowStarterPageAction())
        helpMenu.addSeparator()
        helpMenu.add(new ShowOnlineHelpAction("API Testing Dojo", HelpUrls.API_TESTING_DOJO_HELP_URL))
        helpMenu.add(new ShowOnlineHelpAction("Getting Started", HelpUrls.GETTINGSTARTED_HELP_URL))
        helpMenu.add(new SearchForumAction())
        helpMenu.addSeparator()
        helpMenu.add(new ShowSystemPropertiesAction())
        helpMenu.addSeparator()
        helpMenu.add(new VersionUpdateAction())
        helpMenu.addSeparator()
        helpMenu.add(new ShowOnlineHelpAction("ReadyAPI Trial", HelpUrls.TRIAL_URL, "Apply for ReadyAPI Trial License", "/Trial_16-16.png"))
        helpMenu.add(new OpenUrlAction("Privacy Policy", "http://www.soapui.org" + HelpUrls.SMARTBEAR_PRIVACY_POLICY_URL))
        helpMenu.addSeparator()
        helpMenu.add(new OpenUrlAction("soapui.org", "http://www.soapui.org"))
        helpMenu.add(new OpenUrlAction("smartbear.com", HelpUrls.SMARTBEAR_WEB_SITE_START_PAGE))
        helpMenu.addSeparator()
        helpMenu.add(new AboutAction())
        return helpMenu
    }

    private JMenu buildToolsMenu() {
        JMenu toolsMenu = new JMenu("Tools")
        toolsMenu.mnemonic = KeyEvent.VK_T

        toolsMenu.add(SwingActionDelegate.createDelegate(WSToolsWsdl2JavaAction.SOAPUI_ACTION_ID))
        toolsMenu.add(SwingActionDelegate.createDelegate(JBossWSConsumeAction.SOAPUI_ACTION_ID))
        toolsMenu.addSeparator()
        toolsMenu.add(SwingActionDelegate.createDelegate(WSCompileAction.SOAPUI_ACTION_ID))
        toolsMenu.add(SwingActionDelegate.createDelegate(WSImportAction.SOAPUI_ACTION_ID))
        toolsMenu.addSeparator()
        toolsMenu.add(SwingActionDelegate.createDelegate(Axis1XWSDL2JavaAction.SOAPUI_ACTION_ID))
        toolsMenu.add(SwingActionDelegate.createDelegate(Axis2WSDL2CodeAction.SOAPUI_ACTION_ID))
        toolsMenu.add(SwingActionDelegate.createDelegate(CXFAction.SOAPUI_ACTION_ID))
        toolsMenu.add(SwingActionDelegate.createDelegate(XFireAction.SOAPUI_ACTION_ID))
        toolsMenu.add(SwingActionDelegate.createDelegate(OracleWsaGenProxyAction.SOAPUI_ACTION_ID))
        toolsMenu.addSeparator()
        toolsMenu.add(SwingActionDelegate.createDelegate(XmlBeans2Action.SOAPUI_ACTION_ID))
        toolsMenu.add(SwingActionDelegate.createDelegate(JaxbXjcAction.SOAPUI_ACTION_ID))
        toolsMenu.addSeparator()
        toolsMenu.add(SwingActionDelegate.createDelegate(DotNetWsdlAction.SOAPUI_ACTION_ID))
        toolsMenu.add(SwingActionDelegate.createDelegate(GSoapAction.SOAPUI_ACTION_ID))
        toolsMenu.addSeparator()
        toolsMenu.add(SwingActionDelegate.createDelegate(TcpMonAction.SOAPUI_ACTION_ID))

        return toolsMenu
    }

    private JMenu buildFileMenu() {
        JMenu fileMenu = new JMenu("File")
        fileMenu.mnemonic = KeyEvent.VK_F

        ActionList actions = ActionListBuilder.buildActions(workspace)
        actions.removeAction(actions.actionCount - 1)

        ActionSupport.addActions(actions, fileMenu)

        fileMenu.add(SoapUIPreferencesAction.instance)
        fileMenu.add(new SavePreferencesAction())
        fileMenu.add(new ImportPreferencesAction())

        fileMenu.addSeparator()
        fileMenu.add(buildRecentMenu())
        fileMenu.addSeparator()
        fileMenu.add(new ExitAction())
        fileMenu.add(new ExitWithoutSavingAction())

        return fileMenu
    }

    private JMenuItem buildRecentMenu() {
        JMenu recentMenu = new JMenu("Recent")

        JMenu recentProjectsMenu = new JMenu("Projects")
        JMenu recentWorkspacesMenu = new JMenu("Workspaces")
        JMenu recentEditorsMenu = new JMenu("Editors")

        recentMenu.add(recentEditorsMenu)
        recentMenu.add(recentProjectsMenu)
        recentMenu.add(recentWorkspacesMenu)

        MenuScroller.setScrollerFor(recentEditorsMenu, 24, 125, 0, 1)
        MenuScroller.setScrollerFor(recentProjectsMenu, 24, 125, 0, 1)
        MenuScroller.setScrollerFor(recentWorkspacesMenu, 24, 125, 0, 1)

        RecentItemsListener recentItemsListener = new RecentItemsListener(recentWorkspacesMenu, recentProjectsMenu, recentEditorsMenu)
        workspace.addWorkspaceListener(recentItemsListener)
        desktop.addDesktopListener(recentItemsListener)

        return recentMenu
    }

    static void addStandardPreferencesShortcutOnMac() {
        if (UISupport.mac) {
            KeyboardFocusManager.currentKeyboardFocusManager.addKeyEventDispatcher(e -> {
                int modifiers = e.modifiers
                if (e.keyChar == ',' && (modifiers == InputEvent.META_DOWN_MASK || modifiers == InputEvent.META_MASK)) {
                    SoapUIPreferencesAction.instance.actionPerformed(new ActionEvent(frame, 1, "ShowPreferences"))
                }
                return false
            })
        }
    }

    private JComponent buildMainPanel() {
        JInspectorPanel inspectorPanel = JInspectorPanelFactory.build(navigator)
        inspectorPanel.addInspector(new JComponentInspector<>(buildOverviewPanel(), "Properties", "Properties for the currently selected item", true))
        inspectorPanel.dividerLocation = 500
        inspectorPanel.resizeWeight = 0.6
        inspectorPanel.currentInspector = "Properties"
        return inspectorPanel.component
    }

    private JComponent buildOverviewPanel() {
        overviewPanel = new JPanel(new BorderLayout())
        return overviewPanel
    }

    private void setOverviewPanel(Component panel) {
        if (overviewPanel.componentCount == 0 && panel == null) {
            return
        }

        overviewPanel.removeAll()
        if (panel) {
            overviewPanel.add(panel, BorderLayout.CENTER)
        }
        overviewPanel.revalidate()
        overviewPanel.repaint()
    }

    private JComponent buildContentPanel() {
        return buildLogPanel()
    }

    private JComponent buildLogPanel() {
        InspectorLog4JMonitor inspectorLog4JMonitor = new InspectorLog4JMonitor(desktop.desktopComponent)

        JComponent monitor = initLogMonitor(true, "SoapUI log", inspectorLog4JMonitor)

        if (!settings.getBoolean(UISettings.SHOW_LOGS_AT_STARTUP)) {
            inspectorLog4JMonitor.activate(null)
        }

        MonitorPanel monitorPanel = new MonitorPanel(new RuntimeMemoryMonitorSource())
        monitorPanel.start()
        inspectorLog4JMonitor.addInspector(new JComponentInspector<JComponent>(monitorPanel, "memory log", "Shows runtime memory consumption", true))

        return monitor
    }

    private void show(Workspace workspace) {
        this.workspace = workspace

        String desktopType = soapUICore.settings.getString(UISettings.DESKTOP_TYPE, DEFAULT_DESKTOP)
        desktop = DesktopRegistry.instance.createDesktop(desktopType, workspace)

        if (desktop == null) {
            desktop = new StandaloneDesktop(workspace)
        }

        if (testMonitor == null) {
            testMonitor = new TestMonitor()
        }

        soapUICore.settings.addSettingsListener(new SettingsListener() {
            void settingChanged(String name, String newValue, String oldValue) {
                if (name == UISettings.DESKTOP_TYPE) {
                    changeDesktop(DesktopRegistry.instance.createDesktop(newValue, workspace))
                }
            }

            void settingsReloaded() {
                // TODO Auto-generated method stub

            }
        })

        buildUI()

        testMonitor.addTestMonitorListener(new LogDisablingTestMonitorListener())
        testMonitor.init(workspace)

        initAutoSaveTimer()
        initGCTimer()
    }

    private void changeDesktop(SoapUIDesktop newDesktop) {
        desktopPanelsList.desktop = newDesktop
        desktop.removeDesktopListener(internalDesktopListener)

        desktop.transferTo(newDesktop)
        desktop.release()

        desktop = newDesktop

        if (logMonitor instanceof InspectorLog4JMonitor) {
            ((InspectorLog4JMonitor)logMonitor).contentComponent = desktop.desktopComponent
        }

        desktop.addDesktopListener(internalDesktopListener)

        while (desktopMenu.itemCount > DEFAULT_DESKTOP_ACTIONS_COUNT) {
            desktopMenu.remove(DEFAULT_DESKTOP_ACTIONS_COUNT)
        }

        ActionSupport.addActions(desktop.actions, desktopMenu)

        desktop.init()
    }

    protected boolean onExit() {
        if (saveOnExit) {
            String question = "Exit SoapUI?"

            if (testMonitor.hasRunningTests()) {
                question += "\n(Projects with running tests will not be saved)"
            }

            if (!UISupport.confirm(question, "Question")) {
                return false
            }

            try {
                soapUICore.saveSettings()
                SaveStatus saveStatus = workspace.onClose()
                if (saveStatus == SaveStatus.CANCELLED || saveStatus == SaveStatus.FAILED) {
                    return false
                }
            }
            catch (Exception e1) {
                logError(e1)
            }
        }
        else {
            if (!UISupport.confirm("Exit SoapUI without saving?", "Question")) {
                saveOnExit = true
                return false
            }
        }

        shutdown()

        return true
    }

    private static int getMaxThreadpoolSize() {
        try {
            return Integer.parseInt(System.getProperty("soapui.threadpool.max"))
        }
        catch (Exception e) {
            return DEFAULT_MAX_THREADPOOL_SIZE
        }
    }

    private static void setBackgroundsToWhite() {
        UIManager.put("Button.background", Color.WHITE)
        UIManager.put("Panel.background", Color.WHITE)
        UIManager.put("MenuBar.background", Color.WHITE)
        UIManager.put("ComboBox.background", Color.WHITE)
        UIManager.put("TableHeader.background", Color.WHITE)
        UIManager.put("ToolBar.background", Color.WHITE)
        UIManager.put("TabbedPane.background", Color.LIGHT_GRAY)
        UIManager.put("TabbedPane.selected", Color.WHITE)
        UIManager.put("Label.background", Color.WHITE)
        UIManager.put("CheckBox.background", Color.WHITE)
        UIManager.put("Desktop.background", Color.WHITE)
        UIManager.put("ProgressBar.background", Color.WHITE)
        UIManager.put("InternalFrame.background", Color.WHITE)
        UIManager.put("SplitPane.background", Color.WHITE)
        UIManager.put("ScrollBar.background", Color.WHITE)
        UIManager.put("Spinner.background", Color.WHITE)
        UIManager.put("OptionPane.background", Color.WHITE)
        UIManager.put("ToggleButton.background", Color.WHITE)
        UIManager.put("Slider.background", Color.WHITE)
        UIManager.put("RadioButton.background", Color.WHITE)
        UIManager.put("ScrollPane.background", Color.WHITE)
    }

    private static boolean processCommandLineArgs(CommandLine cmd) {
        if (cmd.hasOption('w')) {
            workspaceName = cmd.getOptionValue('w')
        }

        if (cmd.hasOption('p')) {
            for (String projectNamePassword : cmd.getOptionValues('p')) {
                String[] nameAndPassword = projectNamePassword.split(":")
                projectOptions[nameAndPassword[0]] = nameAndPassword[1]
            }
        }

        return true
    }

    private static org.apache.commons.cli.Options initSoapUIOptions() {

        org.apache.commons.cli.Options options = new org.apache.commons.cli.Options()
        options.addOption("w", true, "Specified the name of the workspace xml file")
        options.addOption("p", true, "Sets project name and its password in format <project name>:<password>")

        return options
    }

    private static void showEndpointExplorer() {
        new EndpointExplorerAction().actionPerformed(null)
    }

    private static final class SoapUIRunner implements Runnable {
        void run() {
            boolean isDebug = ManagementFactory.runtimeMXBean.inputArguments.toString().indexOf("-agentlib:jdwp") > 0
            StandaloneSoapUICore standaloneSoapUICore = new StandaloneSoapUICore(true)
            SoapUIUpdateProvider updateProvider = SoapUIAutoUpdaterUtils.provider
            if (!isDebug && settings.getBoolean(VersionUpdateSettings.AUTO_CHECK_VERSION_UPDATE)) {
                updateProvider.start()
            }

            addStandardPreferencesShortcutOnMac()
            Properties props = new Properties()
            try {
                props.load(SoapUI.getResourceAsStream(SOAPUI_TITLE))
                String brandedTitleExt = props.getProperty("soapui.app.title")
                if (!StringUtils.isNullOrEmpty(brandedTitleExt)) {
                    brandedTitleExt = " - " + brandedTitleExt
                }
                else {
                    brandedTitleExt = ""
                }

                startSoapUI(mainArgs, "SoapUI " + SOAPUI_VERSION + " " + brandedTitleExt, standaloneSoapUICore)

                if (settings.getBoolean(UISettings.SHOW_STARTUP_PAGE) && !browserDisabled) {
                    SwingUtilities.invokeLater(SoapUI::showStarterPage)
                }
            }
            catch (Exception e) {
                e.printStackTrace()
                System.exit(1)
            }
        }
    }

    private static final class WsdlProjectCreator implements Runnable {
        private final String arg

        WsdlProjectCreator(String arg) {
            this.arg = arg
        }

        void run() {
            SoapUIAction<ModelItem> action = actionRegistry.getAction(NewWsdlProjectAction.SOAPUI_ACTION_ID)
            if (action) {
                action.perform(workspace, arg)
            }
        }
    }

    private static final class RestProjectCreator implements Runnable {
        private final URL arg

        RestProjectCreator(URL arg) {
            this.arg = arg
        }

        void run() {
            try {
                WsdlProject project = (WsdlProject)workspace.createProject(arg.host, null)
                SoapUIAction<ModelItem> action = actionRegistry.getAction(NewRestServiceAction.SOAPUI_ACTION_ID)
                if (action) {
                    action.perform(project, arg)
                }
            }
            catch (SoapUIException e) {
                e.printStackTrace()
            }
        }
    }

    private static class ShowStarterPageAction extends AbstractAction {
        ShowStarterPageAction() {
            super("Starter Page")
            putValue(SHORT_DESCRIPTION, "Shows the starter page")
        }

        void actionPerformed(ActionEvent e) {
            showStarterPage()
        }
    }

    private static class AboutAction extends AbstractAction {
        private static final String COPYRIGHT = "2004-" + Calendar.instance.get(Calendar.YEAR) + " smartbear.com"
        private static final String SOAPUI_WEBSITE = "http://www.soapui.org"
        private static final String SMARTBEAR_WEBSITE = "http://www.smartbear.com"

        AboutAction() {
            super("About SoapUI")
            putValue(SHORT_DESCRIPTION, "Shows information on SoapUI")
        }

        void actionPerformed(ActionEvent e) {
            URI splashURI = null
            try {
                splashURI = UISupport.findSplash(SOAPUI_ABOUT).toURI()
            }
            catch (URISyntaxException e1) {
                logError(e1)
            }

            Properties buildInfoProperties = new Properties()
            try {
                buildInfoProperties.load(SoapUI.class.getResourceAsStream(BUILDINFO_PROPERTIES))
            }
            catch (Exception exception) {
                logError(exception, "Could not read build info properties")
            }

            String info = """
            |<html>
            |   <body style=\"margin:0;padding:0;\">
            |       <div style=\"flex: 1;background-image: url($splashURI); background-repeat: no-repeat;width: 457px;height: 301px;\">
            |           <p style=\"margin-top: 85px;margin-left: 30px;color:black;\">
            |               <font size=\"13px\" face=\"$UISupport.editorFont.family\">
            |                   SoapUI $SOAPUI_VERSION<br>Copyright (C) $COPYRIGHT<br>
            |                   <a href=\"$SOAPUI_WEBSITE\">$SOAPUI_WEBSITE</a> | <a href=\"$SMARTBEAR_WEBSITE\">$SMARTBEAR_WEBSITE</a><br>
            |                   <br>
            |                   Build Date: ${defaultString(buildInfoProperties.getProperty("build.date"), "UNKNOWN BUILD DATE")}<br>
            |               </font>
            |           </p>
            |       </div>
            |   </body>
            |</html>""".stripMargin('|')

            JDialog dialog = new JDialog(iconImages: frameIcons, title: "About SoapUI", modal: true, resizable: false)
            JPanel panel = new JPanel(new BorderLayout())
            JEditorPane editorPane = new JEditorPane("text/html", info)
            editorPane.border = BorderFactory.createEmptyBorder()
            editorPane.caretPosition = 0
            editorPane.editable = false
            editorPane.addHyperlinkListener(new DefaultHyperlinkListener(editorPane))
            JPanel buttonBar = ButtonBarFactory.buildRightAlignedBar(new JButton(new OkAction("OK", dialog)))
            buttonBar.border = BorderFactory.createEmptyBorder(5, 0, 5, 5)
            panel.add(buttonBar, BorderLayout.SOUTH)

            dialog.rootPane.contentPane = panel
            dialog.size = new Dimension(493, 310)
            dialog.add(editorPane)
            UISupport.showDialog(dialog)
        }

        private final class OkAction extends AbstractAction {

            private final JDialog dialog

            OkAction(String name, JDialog dialog) {
                super(name)
                this.dialog = dialog
            }

            void actionPerformed(ActionEvent e) {
                dialog.visible = false
            }
        }
    }

    static class NewProjectActionDelegate extends AbstractAction {
        String actionId

        NewProjectActionDelegate(String icon, String name, String actionId) {
            putValue(SMALL_ICON, UISupport.createImageIcon(icon))
            if (name == "Empty") {
                putValue(SHORT_DESCRIPTION, "Creates an empty project")
            }
            else {
                putValue(SHORT_DESCRIPTION, "Creates a new " + name + " project")
            }
            putValue(NAME, name)
            this.actionId = actionId
        }

        void setShortDescription(String description) {
            putValue(SHORT_DESCRIPTION, description)
        }

        void setName(String name) {
            putValue(NAME, name)
        }

        void actionPerformed(ActionEvent e) {
            actionRegistry.getAction(actionId).perform(workspace, null)
        }
    }

    private static class ImportWsdlProjectActionDelegate extends AbstractAction {
        ImportWsdlProjectActionDelegate() {
            putValue(SMALL_ICON, UISupport.createImageIcon("/import_toolbar_icon.png"))
            putValue(SHORT_DESCRIPTION, "Imports an existing SoapUI Project into the current workspace")
            putValue(NAME, "Import")
        }

        void actionPerformed(ActionEvent e) {
            actionRegistry.getAction(ImportWsdlProjectAction.SOAPUI_ACTION_ID).perform(workspace, null)
        }
    }

    private static class SaveAllActionDelegate extends AbstractAction {
        SaveAllActionDelegate() {
            putValue(SMALL_ICON, UISupport.createImageIcon("/Save-all.png"))
            putValue(SHORT_DESCRIPTION, "Saves all projects in the current workspace")
            putValue(NAME, "Save All")
        }

        void actionPerformed(ActionEvent e) {
            actionRegistry.getAction(SaveAllProjectsAction.SOAPUI_ACTION_ID).perform(workspace, null)
        }
    }

    static class ImportPreferencesAction extends AbstractAction {
        public static final String IMPORT_PREFERENCES_ACTION_NAME = "Import Preferences"

        ImportPreferencesAction() {
            super(IMPORT_PREFERENCES_ACTION_NAME)
            putValue(SHORT_DESCRIPTION, "Imports SoapUI Settings from another settings-file")
        }

        void actionPerformed(ActionEvent e) {
            try {
                // prompt for import
                File file = UISupport.fileDialogs.open(null, IMPORT_PREFERENCES_ACTION_NAME, ".xml", "SoapUI Settings XML (*.xml)", null)
                if (file) {
                    soapUICore.importSettings(file)
                }
            }
            catch (Exception e1) {
                UISupport.showErrorMessage(e1)
            }
        }
    }

    private static class AutoSaveTimerTask extends TimerTask {
        @Override
        void run() {
            SwingUtilities.invokeLater(() -> {
                log("Autosaving Workspace")
                WorkspaceImpl workspaceImplementation = (WorkspaceImpl)workspace
                if (workspaceImplementation) {
                    workspaceImplementation.save(false, true)
                }
            })
        }
    }

    private static class GCTimerTask extends TimerTask {
        @Override
        void run() {
            System.gc()
        }
    }

    protected static class WindowInitializationTask implements Runnable {
        void run() {
            expandWindow(frame)
            frame.visible = true
        }

        private void expandWindow(JFrame frame) {
            UserPreferences userPreferences = new UserPreferences()
            Rectangle savedWindowBounds = userPreferences.soapUIWindowBounds
            if (savedWindowBounds == null || !windowFullyVisibleOnScreen(savedWindowBounds)) {
                frame.bounds = GraphicsEnvironment.localGraphicsEnvironment.maximumWindowBounds
            }
            else {
                frame.bounds = savedWindowBounds
                if (!UISupport.mac) {
                    frame.extendedState = userPreferences.soapUIExtendedState
                }
            }
            frame.addWindowListener(new WindowAdapter() {
                @Override
                void windowClosing(WindowEvent event) {
                    try {
                        JFrame f = (JFrame)event.window
                        new UserPreferences(
                            soapUIWindowBounds: f.bounds, 
                            soapUIExtendedState: f.extendedState
                        )
                    }
                    catch (BackingStoreException e) {
                        logError(e, "Could not save SoapUI window bounds")
                    }
                }
            })
        }

        private boolean windowFullyVisibleOnScreen(Rectangle windowBounds) {
            Rectangle bargainBounds = new Rectangle(windowBounds.x + 12 as int, windowBounds.y + 12 as int, windowBounds.width * 4 / 5 as int, windowBounds.height * 4 / 5 as int)
            return bargainBounds in GraphicsEnvironment.localGraphicsEnvironment.screenDevices*.defaultConfiguration.bounds
        }
    }

    private final class InternalDesktopListener extends DesktopListenerAdapter {
        @Override
        void desktopPanelSelected(DesktopPanel desktopPanel) {
            if (desktopPanel.modelItem) {
                navigator.selectModelItem(desktopPanel.modelItem)
            }
        }
    }

    private final class MainFrameWindowListener extends WindowAdapter {
        @Override
        void windowClosing(WindowEvent e) {
            if (onExit()) {
                frame.dispose()
            }
        }

        @Override
        void windowClosed(WindowEvent event) {
            threadPool.shutdown()
            try {
                threadPool.awaitTermination(1500, TimeUnit.MILLISECONDS)
            }
            catch (InterruptedException e) {
                threadPool.shutdownNow()
                // Preserve interrupt status
                Thread.currentThread().interrupt()
            }
            System.out.println("exiting..")
            soapUITimer.cancel()
            System.exit(0)
        }
    }

    class InternalNavigatorListener implements NavigatorListener {
        private PropertyHolderTable selectedPropertyHolderTable = null

        void nodeSelected(SoapUITreeNode treeNode) {
            if (treeNode == null) {
                overviewPanel = null
            }
            else {
                ModelItem modelItem = treeNode.modelItem

                if (selectedPropertyHolderTable) {
                    selectedPropertyHolderTable.release()
                    selectedPropertyHolderTable = null
                }

                if (modelItem instanceof TestPropertyHolder) {
                    // check for closed project -> this should be solved with a
                    // separate ClosedWsdlProject modelItem
                    if (!(modelItem instanceof WsdlProject) || ((WsdlProject)modelItem).open) {
                        selectedPropertyHolderTable = new PropertyHolderTable((TestPropertyHolder)modelItem)

                        if (modelItem instanceof WsdlProject) {
                            WsdlProject project = (WsdlProject)modelItem
                            EnvironmentListener environmentListener = property -> selectedPropertyHolderTable.propertiesModel.fireTableDataChanged()
                            project.addEnvironmentListener(environmentListener)
                            selectedPropertyHolderTable.environmentListener = environmentListener
                            project.addProjectListener(selectedPropertyHolderTable.projectListener)
                        }
                    }
                }

                PanelBuilder<ModelItem> panelBuilder = PanelBuilderRegistry.getPanelBuilder(modelItem)
                if (panelBuilder && panelBuilder.hasOverviewPanel()) {
                    Component overviewPanel = panelBuilder.buildOverviewPanel(modelItem)
                    if (selectedPropertyHolderTable) {
                        JTabbedPane tabs = new JTabbedPane()
                        tabs.name = PROPERTIES_TAB_PANEL_NAME
                        if (overviewPanel instanceof JPropertiesTable<?>) {
                            JPropertiesTable<?> t = (JPropertiesTable<?>)overviewPanel
                            tabs.addTab(t.title, overviewPanel)
                            t.title = null
                        }
                        else {
                            tabs.addTab("Overview", overviewPanel)
                        }

                        tabs.addTab(((TestPropertyHolder)modelItem).propertiesLabel, selectedPropertyHolderTable)
                        overviewPanel = UISupport.createTabPanel(tabs, false)
                    }

                    setOverviewPanel(overviewPanel)
                }
                else {
                    overviewPanel = null
                }
            }
        }
    }

    private class ExitAction extends AbstractAction {
        ExitAction() {
            super("Exit")
            putValue(SHORT_DESCRIPTION, "Saves all projects and exits SoapUI")
            putValue(ACCELERATOR_KEY, UISupport.getKeyStroke("menu Q"))
        }

        void actionPerformed(ActionEvent e) {
            saveOnExit = true
            WindowEvent windowEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)
            frame.dispatchEvent(windowEvent)
        }
    }

    private class ApplyProxyButtonAction extends AbstractAction {

        ApplyProxyButtonAction() {
            putValue(NAME, "Proxy")
        }

        void actionPerformed(ActionEvent e) {
            if (ProxyUtils.proxyEnabled) {
                settings.setBoolean(ProxySettings.ENABLE_PROXY, false)
            }
            else {
                if (!ProxyUtils.autoProxy && emptyManualSettings()) {
                    settings.setBoolean(ProxySettings.AUTO_PROXY, true)
                }
                settings.setBoolean(ProxySettings.ENABLE_PROXY, true)
            }

            updateProxyFromSettings()
        }

        private boolean emptyManualSettings() {
            return StringUtils.isNullOrEmpty(settings.getString(ProxySettings.HOST, "")) || StringUtils.isNullOrEmpty(settings.getString(ProxySettings.PORT, ""))
        }
    }

    private class ToolbarForumSearchAction extends AbstractAction {
        ToolbarForumSearchAction() {
            putValue(SHORT_DESCRIPTION, "Searches the Smartbear Community Forum")
            putValue(SMALL_ICON, UISupport.createImageIcon("/find.png"))
        }

        void actionPerformed(ActionEvent e) {
            doCommunitySearch(searchField.text)
        }
    }

    private class SearchForumAction extends AbstractAction {
        SearchForumAction() {
            super("Search Forum")
            putValue(SHORT_DESCRIPTION, "Searches the Smartbear Community Forum")
        }

        void actionPerformed(ActionEvent e) {
            String text = UISupport.prompt("Search Text", "Search Community Forum", "")
            if (text == null) {
                return
            }

            doCommunitySearch(text)
        }
    }

    private class ExitWithoutSavingAction extends AbstractAction {
        ExitWithoutSavingAction() {
            super("Exit without saving")
            putValue(SHORT_DESCRIPTION, "Exits SoapUI without saving")
            putValue(ACCELERATOR_KEY, UISupport.getKeyStroke("ctrl shift Q"))
        }

        void actionPerformed(ActionEvent e) {
            saveOnExit = false
            WindowEvent windowEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)
            frame.dispatchEvent(windowEvent)
        }
    }

    private class SavePreferencesAction extends AbstractAction {
        SavePreferencesAction() {
            super("Save Preferences")
            putValue(SHORT_DESCRIPTION, "Saves all global preferences")
        }

        void actionPerformed(ActionEvent e) {
            try {
                soapUICore.saveSettings()
            }
            catch (Exception e1) {
                logError(e1, "There was an error when attempting to save your preferences")
                UISupport.showErrorMessage(e1)
            }
        }
    }

    private class PreferencesActionDelegate extends AbstractAction {
        PreferencesActionDelegate() {
            putValue(SMALL_ICON, UISupport.createImageIcon("/preferences_toolbar_icon.png"))
            putValue(SHORT_DESCRIPTION, "Sets Global SoapUI Preferences")
            putValue(NAME, "Preferences")
        }

        void actionPerformed(ActionEvent e) {
            SoapUIPreferencesAction.instance.actionPerformed(null)
        }
    }

    class MaximizeDesktopAction extends AbstractAction {
        private final InspectorLog4JMonitor log4JMonitor
        private JLogList lastLog
        private int lastMainDividerLocation
        private int lastLogDividerLocation

        MaximizeDesktopAction(InspectorLog4JMonitor log4JMonitor) {
            super("Maximize Desktop")
            this.log4JMonitor = log4JMonitor

            putValue(SHORT_DESCRIPTION, "Hides/Shows the Navigator and Log tabs")
            putValue(ACCELERATOR_KEY, UISupport.getKeyStroke("menu M"))
        }

        void actionPerformed(ActionEvent e) {
            if (mainInspector.currentInspector || logMonitor.currentLog) {
                lastMainDividerLocation = mainInspector.dividerLocation
                mainInspector.deactivate()

                lastLog = logMonitor.currentLog
                lastLogDividerLocation = log4JMonitor.dividerLocation

                log4JMonitor.deactivate()
            }
            else {
                mainInspector.currentInspector = "Navigator"
                mainInspector.dividerLocation = lastMainDividerLocation == 0 ? 250 : lastMainDividerLocation

                log4JMonitor.currentLog = lastLog
                log4JMonitor.dividerLocation = lastLogDividerLocation == 0 ? 500 : lastLogDividerLocation
            }
        }
    }
}
