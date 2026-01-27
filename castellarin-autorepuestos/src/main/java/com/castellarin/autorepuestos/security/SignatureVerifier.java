package com.castellarin.autorepuestos.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class SignatureVerifier {

    public static boolean isValidSignature(String resourceId, String requestId, String ts, String xSignature, String secretKey) {
        try {
            String manifest = String.format("id:%s;request-id:%s;ts:%s;", resourceId, requestId, ts);
            System.out.println(manifest);

            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);

            byte[] hashBytes = sha256Hmac.doFinal(manifest.getBytes(StandardCharsets.UTF_8));
            String generatedHash = HexFormat.of().formatHex(hashBytes);

            System.out.println("hash generado: "+generatedHash);
            System.out.println("hash recibido: "+xSignature);

            return generatedHash.equals(xSignature);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return false;
        }
    }
}