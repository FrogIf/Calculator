package sch.frog.test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

import sch.frog.calculator.math.number.NumberRoundingMode;
import sch.frog.calculator.math.number.RationalNumber;
import sch.frog.calculator.util.StringUtils;
import sch.frog.calculator.util.collection.IMap;
import sch.frog.test.util.DebugUtil;
import sch.frog.calculator.util.collection.ISet;
import sch.frog.calculator.util.collection.IMap.Entry;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.TreeMap;

public class RationalNumberTest {

    private static final NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));

    private static final Random r = new Random();

    private static final IMap<NumberRoundingMode, RoundingMode> map = new TreeMap<>((a, b) -> a.compareTo(b));
    private static final ISet<Entry<NumberRoundingMode, RoundingMode>> entrySet;
    static{
        map.put(NumberRoundingMode.CEILING, RoundingMode.CEILING);
        map.put(NumberRoundingMode.FLOOR, RoundingMode.FLOOR);
        map.put(NumberRoundingMode.DOWN, RoundingMode.DOWN);
        map.put(NumberRoundingMode.UP, RoundingMode.UP);
        map.put(NumberRoundingMode.HALF_DOWN, RoundingMode.HALF_DOWN);
        map.put(NumberRoundingMode.HALF_UP, RoundingMode.HALF_UP);
        map.put(NumberRoundingMode.HALF_EVEN, RoundingMode.HALF_EVEN);
        entrySet = map.entrySet();
    }

    static{
        formatter.setMaximumFractionDigits(10000);
    }
    
    public static void main(String[] args){
        // randomTest();
        // randomDecimalTest();
        // randomDecimalScale();
        testValueOf();
    }

    private static void testValueOf(){
        String origin = "123.345_5E1";
        RationalNumber valueOf = RationalNumber.valueOf(origin);
        System.out.println(valueOf);
    }

    private static void randomDecimalScale(){
        for(int i = 0; i < 1000; i++){
            String decimal = DebugUtil.randomDecimal();
            int scale = r.nextInt(10);
            System.out.println("origin : " + decimal + ", scale : " + scale);
            Iterator<Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
            while(itr.hasNext()){
                System.out.println(RationalNumber.valueOf(decimal).decimal(scale, itr.next().getKey()));
            }
        }
        System.out.println("finish!");
    }

    private static void randomDecimalTest(){
        for(int i = 0; i < 10000; i++){
            Iterator<Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
            while(itr.hasNext()){
                if(!singleDecimal(DebugUtil.randomDecimal(), itr.next().getKey(), r.nextInt(10))){
                    System.out.println("find error, stop.");
                    return;
                }
            }
        }

        for(int i = 0; i < 10000; i++){
            String decimal = randomLitterNumber();
            Iterator<Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
            while(itr.hasNext()){
                if(!singleDecimal(decimal, itr.next().getKey(), r.nextInt(10))){
                    System.out.println("find error, stop.");
                    return;
                }
            }
        }
        System.out.println("success!");
    }

    private static boolean singleDecimal(String decimal, NumberRoundingMode numberRoundingMode, int scale){
        RoundingMode roundingMode = map.get(numberRoundingMode);
        String ans = new BigDecimal(decimal).setScale(scale, roundingMode).toPlainString(); 
        String result = RationalNumber.valueOf(decimal).toPlainString(scale, numberRoundingMode);
        System.out.println(String.format("origin : %s, scale : %d, mode : %s, ans : %s, result : %s",
                decimal, scale, numberRoundingMode.name(), ans, result));
        return result.equals(ans);
    }

    private static void manualTest(String number, NumberRoundingMode numberRoundingMode, int scale){
        if(singleTest(number, numberRoundingMode, scale)){
            System.out.println("success");
        }else{
            System.out.println("failed");
        }
    }

    private static void randomTest(){
        for(int i = 0; i < 10000; i++){
            String decimal = DebugUtil.randomDecimal();
            Iterator<Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
            while(itr.hasNext()){
                if(!singleTest(decimal, itr.next().getKey(), r.nextInt(10))){
                    System.out.println("find error, stop.");
                    return;
                }
            }
        }
        for(int i = 0; i < 10000; i++){
            String decimal = randomLitterNumber();
            Iterator<Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
            while(itr.hasNext()){
                if(!singleTest(decimal, itr.next().getKey(), r.nextInt(10))){
                    System.out.println("find error, stop.");
                    return;
                }
            }
        }
        System.out.println("success!");
    }

    private static boolean singleTest(String decimal, NumberRoundingMode numberRoundingMode, int scale){
        System.out.println(String.format("origin : %s, scale : %d, mode : %s", decimal, scale, numberRoundingMode.name()));
        BigDecimal bigDecimal = new BigDecimal(decimal);
        String ans = formatter.format(bigDecimal);
        RationalNumber rationNumber = RationalNumber.valueOf(decimal);
        String result = rationNumber.scientificNotation(scale, numberRoundingMode);
        System.out.println(String.format("result : %s, ans : %s", result, ans));
        return check(result, ans, scale, numberRoundingMode);
    }

    private static boolean check(String result, String answer, int scale, NumberRoundingMode roundingMode){
        int ePos = result.indexOf('E');
        String pre = result.substring(0, ePos);
        String post = result.substring(ePos);
        boolean success = answer.endsWith(post);
        RoundingMode rm = map.get(roundingMode);
        if(success){
            String decimal = answer.substring(0, answer.indexOf('E'));
            String ans = new BigDecimal(decimal).setScale(scale, rm).toPlainString();
            success = ans.equals(pre);
            if(!success){
                System.out.println("----|ans : " + ans + "|----|result : " + pre + "|----------");
            }
        }else{
            int sign = pre.startsWith("-") ? 1 : 0;
            if(pre.charAt(sign) == '1'){
                String decimal = answer.substring(0, answer.indexOf('E'));
                String ans = new BigDecimal(decimal).setScale(scale, rm).toPlainString();
                ans = formatter.format(new BigDecimal(ans));
                ans = new BigDecimal(ans.substring(0, ans.indexOf('E'))).setScale(scale, rm).toPlainString();
                success = ans.equals(pre);
                System.out.println("----|ans : " + ans + "|----|result : " + pre + "|----------");
            }
        }
        return success;
    }

    // 随机生成小于1的小数
    private static String randomLitterNumber(){
        int zeroCount = r.nextInt(10) + 5;
        String num = DebugUtil.randomIntegerPositive();
        return "0." + StringUtils.leftFill(num, '0', zeroCount);
    }


    // private boolean divideTest(int scale, NumberRoundingMode numberRoundingMode, RoundingMode roundingMode){
    //     String a = DebugUtil.randomDecimal();
    //     String b = DebugUtil.randomDecimal();
    //     BigDecimal ans = new BigDecimal(a).divide(new BigDecimal(b), scale, roundingMode).toPlainString();
    //     String result = RationalNumber.valueOf(a).div(RationalNumber.valueOf(b)).decimal(scale, numberRoundingMode);
    //     return ans.equals(result);
    // }
    
}
