package sch.frog.calculator.number;

import sch.frog.calculator.number.impl.IntegerNumber;
import sch.frog.calculator.number.impl.NumberRoundingMode;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class IntegerScientificNotationTest {

    private static final NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));

    private static final Random r = new Random();

    static{
        formatter.setMaximumFractionDigits(10000);
    }

    public static void main(String[] args){
        int test = 10000;
        System.out.println("test integer scientific notation");
        for(int i = 0; i < test; i++){
            String number = TestUtil.randomInteger();
            String ans = formatter.format(new BigInteger(number));
            IntegerNumber num = IntegerNumber.valueOf(number);
            int scale = r.nextInt(10);
            String val = num.scientificNotation(scale, NumberRoundingMode.DOWN);
            if(!check(val, ans, scale)){
                System.out.println("number : " + number + ", expect " + ans + ", but :" + val);
            }
        }
        System.out.println("test integer scientific notation finish");
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
