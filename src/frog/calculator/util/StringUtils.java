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

    public static String leftFill(String str, char ch, int count){
        StringBuilder sb = new StringBuilder(count);
        for(int i = 0; i < count; i++){
            sb.append(ch);
        }
        return sb.toString() + str;
    }

    public static String rightTrim(String str, char ch){
        int i = str.length() - 1;
        while(i > 0 && str.charAt(i) == ch){
            i--;
        }
        str = str.substring(0, i + 1);
        return str;
    }

    public static boolean startWith(char[] chars, int startIndex, String prefix){
        int i = startIndex, j = 0;
        char[] preChars = prefix.toCharArray();
        for(int lenA = chars.length, lenB = preChars.length; i < lenA && j < lenB; i++, j++){
            if(chars[i] != preChars[j]){
                return false;
            }
        }
        return true;
    }
}
