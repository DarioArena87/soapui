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

package com.eviware.soapui.impl.wsdl.teststeps.assertions.recent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public class BoundedQueue {
    private final int MAX_SIZE = 5;

    private final LinkedList<String> queue;

    public BoundedQueue() {
        queue = new LinkedList<String>();
    }

    public void remove(String e) {
        queue.remove(e);
    }

    public void add(String e) {
        if (queue.contains(e)) {
            return;
        }

        queue.addLast(e);

        if (queue.size() > MAX_SIZE) {
            queue.removeFirst();
        }
    }

    public List<String> getByAlphabeticalOrder() {
        List<String> list = new ArrayList<String>(queue);
        Collections.sort(list);
        return list;
    }

    public List<String> getByInsertionOrder() {
        return new LinkedList<String>(queue);
    }
}
