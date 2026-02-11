package com.ant.auth; 

public class TotpSecretDeriver {
	 private TotpSecretDeriver() {}
	    private static final String HMAC_ALG = "HmacSHA256";
	    private static final byte[] MASTER_KEY = KeyLoader.load("C:/keys/master.key"); // 경로 조정

	    public static String deriveBase32Secret(String userId, String perUserSalt) {
	    	try {
	            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
	            mac.init(new javax.crypto.spec.SecretKeySpec(MASTER_KEY, "HmacSHA256"));
	            byte[] h = mac.doFinal((userId + ":" + perUserSalt).getBytes("UTF-8"));

	            // 20바이트 사용 (TOTP용)
	            byte[] secret20 = java.util.Arrays.copyOf(h, 20);
 
	            return SimpleBase32.encode(secret20);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

}
