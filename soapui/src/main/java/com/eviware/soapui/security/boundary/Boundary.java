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

package com.eviware.soapui.security.boundary;

/**
 * @author nebojsa.tasic
 */
public interface Boundary {
    int LENGTH = 1;
    int MIN_LENGTH = 2;
    int MAX_LENGTH = 3;
    int TOTAL_DIGITS = 4;
    int FRACTION_DIGITS = 5;
    int MAX_EXCLISIVE = 6;
    int MIN_EXCLISIVE = 7;
    int MAX_INCLISIVE = 8;
    int MIN_INCLISIVE = 9;

    String outOfBoundary(int restrictionAttribute, String value);
}
