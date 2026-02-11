package com.ant.auth;

import java.net.URLEncoder;

public final class TotpProvisioning {
    private TotpProvisioning() {}

    /** otpauth://totp/Issuer:account?secret=...&issuer=...&digits=6&period=30 */
    public static String buildOtpAuthUri(String issuer, String account, String base32Secret) {
        try {
            String label = URLEncoder.encode(issuer + ":" + account, "UTF-8")
                                     .replace("+", "%20");
            String iss   = URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
            // algorithm=SHA1 는 기본값이라 생략 가능
            return "otpauth://totp/" + label
                 + "?secret=" + base32Secret
                 + "&issuer=" + iss
                 + "&digits=6&period=30";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}