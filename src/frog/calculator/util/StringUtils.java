package frog.calculator.util;

public class StringUtils {

    public static String concat(String... subString){
        StringBuilder sb = new StringBuilder();
        for (String s : subString) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static boolean isNotBlank(String str){
        return str != null && !"".equals(str.trim());
    }

    public static boolean isBlank(String str){
        return str == null || "".equals(str.trim());
    }

}
