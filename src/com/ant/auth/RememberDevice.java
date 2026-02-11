package com.ant.auth;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

public final class RememberDevice {
    private RememberDevice() {}

    private static final String COOKIE = "MFA_REM";
    private static final String HMAC_ALG = "HmacSHA256";

    // master.key 바이너리 그대로 사용 (앞서 만든 KeyLoader)
    private static final byte[] MASTER_KEY = KeyLoader.load("C:/Program Files/keys/master.key");

    /** 
     * 유효한 리멤버 토큰이라면 true.
     * 조건: (1) 쿠키 형식/서명/만료 OK, (2) 세션 G_ID와 토큰 userId 일치
     */
    public static boolean verifyAndAccept(HttpServletRequest req) {
        // 세션이 없으면 통과시키지 않음
        javax.servlet.http.HttpSession s = req.getSession(false);
        if (s == null) return false;

        String sessionUser = (String) s.getAttribute("G_ID");
        if (sessionUser == null || sessionUser.length() == 0) return false;

        String token = readCookie(req, COOKIE);
        if (token == null) return false;

        // 전체 토큰은 Base64(payload|sig)
        byte[] raw;
        try {
            raw = DatatypeConverter.parseBase64Binary(token);
        } catch (Exception e) {
            return false;
        }
        String all = new String(raw); // US-ASCII 가정 (구성 값이 ASCII 범위)
        int lastBar = all.lastIndexOf('|');
        if (lastBar <= 0) return false;

        String payload = all.substring(0, lastBar);           // deviceId|userId|exp
        String sigB64  = all.substring(lastBar + 1);

        // HMAC 재계산
        String sigCalcB64 = base64(hmacSha256(payload));
        if (!constantTimeEq(sigB64, sigCalcB64)) return false;

        // payload 파싱
        String[] parts = payload.split("\\|");
        if (parts.length != 3) return false;

        String deviceId = parts[0];
        String userId   = parts[1];
        long   exp;
        try { exp = Long.parseLong(parts[2]); } catch (Exception e) { return false; }

        // 만료 체크
        long now = System.currentTimeMillis();
        if (now > exp) return false;

        // 세션 사용자와 일치해야 함 (가장 중요!)
        if (!sessionUser.equals(userId)) return false;

        // (선택) deviceId 추가 검증: 별도 장치 쿠키와 동일한지 확인할 수도 있음

        return true;
    }

    private static String readCookie(HttpServletRequest r, String name) {
        Cookie[] cs = r.getCookies();
        if (cs == null) return null;
        for (Cookie c : cs) if (name.equals(c.getName())) return c.getValue();
        return null;
    }

    private static byte[] hmacSha256(String msg) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALG);
            mac.init(new SecretKeySpec(MASTER_KEY, HMAC_ALG));
            return mac.doFinal(msg.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String base64(byte[] b) {
        return DatatypeConverter.printBase64Binary(b);
    }

    private static boolean constantTimeEq(String a, String b) {
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        int r = 0;
        for (int i = 0; i < a.length(); i++) r |= (a.charAt(i) ^ b.charAt(i));
        return r == 0;
    }
}