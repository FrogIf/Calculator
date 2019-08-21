package frog.calculator.util;

public class StringUtils {

    public static String concat(String... subString){
        StringBuilder sb = new StringBuilder();
        for (String s : subString) {
            sb.append(s);
        }
        return sb.toString();
    }

}
