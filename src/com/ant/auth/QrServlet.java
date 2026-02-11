package com.ant.auth;

import com.google.zxing.*;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

public class QrServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req,
                         javax.servlet.http.HttpServletResponse resp)
            throws java.io.IOException {
        String data = req.getParameter("data"); // otpauth URI
        if (data == null || data.length() == 0) { resp.sendError(400, "missing data"); return; }

        // 캐시 금지 (시크릿 포함)
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        resp.setHeader("Pragma", "no-cache");
        resp.setContentType("image/png");

        try {
            java.util.Map<EncodeHintType, Object> hints = new java.util.HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);

            int size = 240;
            BitMatrix m = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size, hints);

            java.awt.image.BufferedImage img =
                new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_RGB);
            for (int x=0; x<size; x++) for (int y=0; y<size; y++)
                img.setRGB(x, y, m.get(x,y) ? 0x000000 : 0xFFFFFF);

            javax.imageio.ImageIO.write(img, "png", resp.getOutputStream());
        } catch (Exception e) {
            resp.sendError(500, "QR generate error");
        }
    }
}