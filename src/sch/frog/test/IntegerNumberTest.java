package sch.frog.test;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import sch.frog.calculator.math.number.IntegerNumber;
import sch.frog.calculator.math.number.NumberRoundingMode;
import sch.frog.test.util.DebugUtil;

/**
 * 整数测试类
 */
public class IntegerNumberTest {
    
    private static final NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));

    private static final Random r = new Random();

    static{
        formatter.setMaximumFractionDigits(10000);
    }

    public static void main(String[] args){
        // manualTest("74858094452683656295977035133");
        randomTest();
    }

    private static void manualTest(String number){
        if(singleTest(number)){
            System.out.println("success");
        }else{
            System.out.println("failed");
        }
    }

    private static void randomTest(){
        for(int i = 0; i < 100000; i++){
            if(!singleTest(DebugUtil.randomInteger())){
                System.out.println("find error, stop.");
                return;
            }
        }
        System.out.println("success!");
    }

    private static boolean singleTest(String integerNumber){
        String ans = formatter.format(new BigInteger(integerNumber));
        IntegerNumber num = IntegerNumber.valueOf(integerNumber);
        int scale = r.nextInt(10);
        String val = num.scientificNotation(scale, NumberRoundingMode.DOWN);
        System.out.println(String.format("origin : %s, result : %s, ans : %s", integerNumber, val, ans));
        return check(val, ans, scale);
    }

    private static boolean check(String result, String answer, int scale){
        int ePos = result.indexOf('E');
        String pre = result.substring(0, ePos);
        String post = result.substring(ePos);
        boolean success = (answer.startsWith(pre) || pre.startsWith(answer.substring(0, answer.indexOf('E')))) && answer.endsWith(post);
        if(success){
            int dotPos = result.indexOf('.');
            if(scale == 0){
                return dotPos == -1;
            }else{
                return ePos - dotPos - 1 == scale;
            }
        }
        return false;
    }
    
}
