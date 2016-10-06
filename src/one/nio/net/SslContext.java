/*
 * Copyright 2015 Odnoklassniki Ltd, Mail.Ru Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package one.nio.net;

import one.nio.os.NativeLibrary;

import javax.net.ssl.SSLException;

public abstract class SslContext {
    public static final int VERIFY_NONE = 0;               // client cert is not verified
    public static final int VERIFY_PEER = 1;               // client cert is verified, if provided
    public static final int VERIFY_REQUIRE_PEER_CERT = 2;  // fail, if client does not provide a cert
    public static final int VERIFY_ONCE = 4;               // do not verify certs on renegotiations

    public static SslContext getDefault() {
        return NativeLibrary.IS_SUPPORTED ? NativeSslContext.DEFAULT : null;
    }

    public static SslContext create() throws SSLException {
        if (NativeLibrary.IS_SUPPORTED) {
            return new NativeSslContext();
        }
        throw new UnsupportedOperationException();
    }

    public abstract void close();
    public abstract void setProtocols(String protocols) throws SSLException;
    public abstract void setCiphers(String ciphers) throws SSLException;
    public abstract void setCertificate(String certFile, String privateKeyFile) throws SSLException;
    public abstract void setCA(String caFile) throws SSLException;
    public abstract void setVerify(int verifyMode) throws SSLException;
    public abstract void setTicketKey(byte[] ticketKey) throws SSLException;
    public abstract void setTimeout(long timeout) throws SSLException;
}
