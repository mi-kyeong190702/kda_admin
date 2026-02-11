package com.ant.auth;

public class SimpleBase32 {
	 private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
	
	    public static String encode(byte[] bytes) {
	        StringBuilder out = new StringBuilder((bytes.length * 8 + 4) / 5);
	        int buffer = 0, bitsLeft = 0;
	        for (int i = 0; i < bytes.length; i++) {
	            buffer = (buffer << 8) | (bytes[i] & 0xFF);
	            bitsLeft += 8;
	            while (bitsLeft >= 5) {
	                out.append(ALPHABET[(buffer >> (bitsLeft - 5)) & 0x1F]);
	                bitsLeft -= 5;
	            }
	        }
	        if (bitsLeft > 0) {
	            out.append(ALPHABET[(buffer << (5 - bitsLeft)) & 0x1F]);
	        }
	        return out.toString(); // 패딩 "=" 없음
	    }
	
	    private SimpleBase32() {}
}
