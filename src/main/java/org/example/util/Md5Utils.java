package org.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class Md5Utils {
    private Md5Utils() {}

    // 基本 MD5（字符串）
    public static String md5(String input) {
        return md5(input, StandardCharsets.UTF_8);
    }

    public static String md5(String input, java.nio.charset.Charset charset) {
        if (input == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(charset));
            return bytesToHex(digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 带盐的 MD5（salt 可以放前或放后或两者都加）
    public static String md5WithSalt(String input, String salt) {
        if (input == null) return null;
        String combined = (salt == null) ? input : input + salt;
        return md5(combined);
    }

    // 多次迭代 MD5（不建议单独用于密码，示例用于理解）
    public static String md5Iterate(String input, String salt, int iterations) {
        if (input == null) return null;
        String result = (salt == null) ? input : input + salt;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < Math.max(1, iterations); i++) {
                md.reset();
                bytes = md.digest(bytes);
            }
            return bytesToHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 文件 MD5（流式，适合大文件）
    public static String md5File(File file) {
        try (InputStream is = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8 * 1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
            return bytesToHex(md.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // bytes -> hex（小写）
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // & 0xFF to treat as unsigned
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }
}
