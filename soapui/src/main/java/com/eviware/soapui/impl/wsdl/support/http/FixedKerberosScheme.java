package com.eviware.soapui.impl.wsdl.support.http;

import org.apache.http.auth.Credentials;
import org.apache.http.impl.auth.KerberosScheme;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

public class FixedKerberosScheme extends KerberosScheme {

    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";

    public FixedKerberosScheme(boolean stripPort, boolean useCanonicalHostname) {
        super(stripPort, useCanonicalHostname);
    }

    @Override
    protected byte[] generateToken(byte[] input, String authServer, Credentials credentials) throws GSSException {
        return KerberosProtocolFixer.generateFixedToken(this, new Oid(KERBEROS_OID), input, authServer, credentials);
    }
}
