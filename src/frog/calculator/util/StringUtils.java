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

    /**
     * 判断字符串str从start位置开始(包含start),是否与match匹配
     * @param start 匹配位置偏移量
     * @param str 字符串
     * @param match 需匹配的字符
     * @return true 匹配, false 不匹配
     */
    public static boolean startWith(int start, String str, String match){
        if(match.length() + start > str.length()){
            return false;
        }
        boolean isMatch = false;
        int i = 0, len = match.length();
        while(i < len && (isMatch = str.charAt(start + i) == match.charAt(i))){
            i++;
        }
        return isMatch;
    }

    /**
     * 判断字符串str从start位置开始(包含start),是否与match匹配
     * @param start 匹配位置偏移量
     * @param str 字符串
     * @param match 需匹配的字符
     * @return true 匹配, false 不匹配
     */
    public static boolean startWith(int start, char[] str, String match){
        if(match.length() + start > str.length){
            return false;
        }
        boolean isMatch = false;
        int i = 0, len = match.length();
        while(i < len && (isMatch = str[start + i] == match.charAt(i))){
            i++;
        }
        return isMatch;
    }

}
