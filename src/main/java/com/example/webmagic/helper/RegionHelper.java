package com.example.webmagic.helper;

public class RegionHelper {

    public static boolean provinceJustify(String code){
        return code.endsWith("0000")? true : false;
    }
    public static boolean cityJustify(String code){
        return code.endsWith("00")? true : false;
    }
}
