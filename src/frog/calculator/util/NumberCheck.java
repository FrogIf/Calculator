package frog.calculator.util;

public class NumberCheck {
    public static boolean isNumber(String symbol){
        char first = symbol.charAt(0);
        if(symbol.length() == 1){
            if(first < '0' || first > '9'){
                return false;
            }
        }else{
            if(first != '-' && (first < '0' || first > '9')){
                return false;
            }
        }

        boolean hasDot = false;
        for (int i = 1; i < symbol.length(); i++){
            char ch = symbol.charAt(i);
            if(ch == '.' && !hasDot) {
                hasDot = true;
                continue;
            }
            if(ch < '0' || ch > '9'){
                return false;
            }
        }
        return true;
    }
}
