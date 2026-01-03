package org.example.api.impl;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RSASignExample {

    public static String rsaSign(String data, String privateKeyPem) throws Exception {
        // 1. 去掉 PEM 头尾 & 换行
        String privateKeyContent = privateKeyPem
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");

        // 2. Base64 decode
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyContent);

        // 3. 生成私钥对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        // 4. 用 SHA256withRSA 签名
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));

        byte[] signedBytes = signature.sign();

        // 5. Base64 输出
        return Base64.getEncoder().encodeToString(signedBytes);
    }

    public static void main(String[] args) throws Exception {
        String data = "money=1&name=支付测试&notify_url=http://127.0.0.1/SDK/notify_url.php&out_trade_no=20260103152134279&pid=1031&return_url=http://127.0.0.1/SDK/return_url.php&timestamp=1767453758&type=alipay";
        
        String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n"
                + "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDFiaywuvYYprFBUXy0CA3GoqEpq606Xj5HzTCiXJRHQQ+rPLfvBMyFhBCShlzzHlLydrdxP8MOdiUPU7S3KtS49duu2egVQrclCG/O/WVdW4BYgg8jRYVwhxnNvdsGLTyepndwQjKmn0PdYg0oTJ/yYM5I7D4IijTyetyLPRK508ZoXCdBEkjF0PsYXikLv5brbd8iLGXnzRYL9ozZpNndXXz1gLkQiKsODW91aqSJnLVO97dAK1ViXmPeMRSofD+JJM46LAoAYeSacbxMEMlmo44H8EhmlefQrr/Ln09KcQRPhxO/+hWT7/tZx+4y3TebRJCWyT3OO7p3MbaGM2OJAgMBAAECggEBAKsbVjq/t+wid+fcbeiEEWq99FvkGWk1uTz4v+fjyYzvDmOFdM3VmlMM0k4ZlOoCmlbr/npagNtF+1QLbb47m4ebb29F0YKZB4tMg40IcYUsfUAtaG9lCPEwFWnhypbPGHNUPqE+tiEeU+qvxDXkKagJsqX3/MPo83QWeHMjs9TB1qmFnDPiwlb5Q6WlLHVTA2b3tBAySgd87eJa6fsEsi87FWljZsSlfUudxYJGR7hReoG/Ldp3XmNyAi4llErEJeoqZn45xzY/4U4j/N5wTnPxIjQxQeFBqhPlDmQcGKGIvjEW/FTpgTi6Tza/YWvFmOfd+QueqxIGmQscNETuswECgYEA5lNGWiG4yCC/K05d0PQ+Ghb1aCxigoyxqUlYRgQElN/McJIn/la/tVxLT8zkxRF79fjFvt0/XzjOSeLtbYB5bsJk4EqCM/eiBWHcFwZl39CgLE9C5Zw8qoT8PHEmfh63s75eRwqnJPq5JXNrOx1aUcMVvdNgdw7zA4j0LHdz9E8CgYEA247Aom/gnhyTR2yYXJcTRSec/yZkLY68zr9nYnFESV/Hk0tskiOmmyMPrvRgBP7L9FrXa21uvFGbMA9Js+0GDzJUM5jvFCh3CQG5Yk2Lii4dQc48S1RKLjDJvHUfErJbFW3aYW9PFoqD3AcyvdtS94iRF43orI1rJ2jzFHFLvKcCgYA6UKFCVsFjIi9kto24Ru6mSmPOAVbbuOWOd/x6la/XRaaMZeTsV2hocbq7tKPDZX7eMyNWBMxzvn00Zdk5XUrhiSRjSVoX8D28YR+JUNxgQ4EaVuMYIWuWvN7kKr3VNs/KGYvjLsZM7ZIM8mEW8YKZquwJSMhmvIk4vucbjw7zrwKBgQCdHiwGUk+IdmSDAViLMeWtYGq+QIgB/hppTZm7XdkDNRKJ+rioX/DTJiUMC39HM9tac0Ojcf2DsFaA7v/MTXs/lsGg5vl4EHkWrfAvQ9Tuvo9bTy3grmIjafCD98CTzaTW0fUsMs6DD/7KjzAlo3VdBJLoKzpteQpMepMxdt4/EwKBgQDClS/udAVTQh7BrIix0Me9zA6DhINQ393HCWAIm8N0d7Oa4ci1Uw+4Hb/84x3hkuQiN0PCvvIHvU3c26jYw7gMA367azrmzr6vPt9Wq5plyTp8HP4jq6iq5aBVp3Lm/k4QrkuIlCrF1Irop54W8iXiBFMhhyUcCQYJPjBPstUW9w=="
                + "-----END PRIVATE KEY-----";

        String sign = rsaSign(data, privateKeyPem);

        System.out.println("Base64签名结果：");
        System.out.println(sign);
    }
}
