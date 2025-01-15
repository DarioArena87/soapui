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

package com.eviware.soapui.model.testsuite;

public interface TestRunner {

    /**
     * Gets the current status of this TestRunner
     */

    Status getStatus();

    /**
     * Starts running this TestRunners TestCase. If the async flag is set to
     * true, this method will return directly, otherwise it will block until the
     * TestCase is finished
     *
     * @param async flag controlling if TestCase should be run in seperate or
     *              callers thread.
     */

    void start(boolean async);

    /**
     * Returns the time taken by this runner since its last start
     */

    long getTimeTaken();

    /**
     * Returns the time this runner was last started
     */

    long getStartTime();

    /**
     * Blocks until this runner is finished, (returns directly if it already has
     * finished)
     */

    Status waitUntilFinished();

    /**
     * Cancels an ongoing test run with the specified reason
     */

    void cancel(String reason);

    /**
     * Fails an ongoing test run with the specified reason
     */

    void fail(String reason);

    /**
     * Gets the reason why a running test was canceled or failed.
     */

    String getReason();

    TestRunContext getRunContext();

    TestRunnable getTestRunnable();

    boolean isRunning();

    enum Status {
        INITIALIZED,
        RUNNING,
        CANCELED,
        FINISHED,
        FAILED,
        WARNING
    }
}
