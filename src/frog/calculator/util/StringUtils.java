package frog.calculator.util;

public class StringUtils {

    public static boolean isNotBlank(String str){
        return str != null && !"".equals(str.trim());
    }

    public static boolean isBlank(String str){
        return str == null || "".equals(str.trim());
    }

    public static String concat(char[] chars){
        if(chars == null){ return null; }
        StringBuilder builder = new StringBuilder();
        for(char c : chars){
            builder.append(c);
        }
        return builder.toString();
    }
}
