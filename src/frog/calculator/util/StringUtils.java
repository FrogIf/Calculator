package frog.calculator.util;

public class StringUtils {

    public static boolean isNotBlank(String str){
        return str != null && !"".equals(str.trim());
    }

    public static boolean isBlank(String str){
        return str == null || "".equals(str.trim());
    }

}
