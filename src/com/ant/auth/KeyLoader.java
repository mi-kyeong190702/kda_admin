package com.ant.auth;

public final class KeyLoader {
    private KeyLoader() {}
    public static byte[] load(String path) {
        java.io.File f = new java.io.File(path);
        java.io.DataInputStream in = null;
        try {
            byte[] buf = new byte[(int) f.length()];
            in = new java.io.DataInputStream(new java.io.FileInputStream(f));
            in.readFully(buf);
            return buf;
        } catch (Exception e) { throw new RuntimeException(e); }
        finally { try { if (in != null) in.close(); } catch (Exception ignore) {} }
    }
}