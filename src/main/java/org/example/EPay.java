package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.response.*;

import java.math.BigDecimal;

public class EPay {
    private static String pid;
    private static String key;

    private EPay() {
    }

    public static void initialize(String pid, String key) {
        EPay.pid = pid;
        EPay.key = key;
    }
}
