package com.example.qrcodes;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Service
public class QRCodeService {
    public static String generateQRCodeDataUrl(String text, int size) {
        Objects.requireNonNull(text, "text must not be null");
        try {
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] bytes = pngOutputStream.toByteArray();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            return "data:image/png;base64," + base64;
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }
}
