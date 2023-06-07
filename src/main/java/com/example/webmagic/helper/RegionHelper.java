package com.example.webmagic.helper;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RegionHelper {
    private static final List<String> codeList = Arrays.asList("110000", "120000", "310000", "500000");

    public static boolean provinceJustify(String code) {
        return code.endsWith("0000") && !codeList.contains(code);
    }

    public static boolean cityJustify(String code) {
        return !code.endsWith("0000") && code.endsWith("00");
    }
}
