package sch.frog.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import sch.frog.calculator.math.number.NumberRoundingMode;
import sch.frog.calculator.util.collection.IMap;
import sch.frog.calculator.util.collection.ISet;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.TreeMap;
import sch.frog.calculator.util.collection.IMap.Entry;
import sch.frog.test.util.DebugUtil;

public class RoundOffTest {

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
    
    public static void main(String[] args){
        // manualTest();
        randomTest();
    }


    private static void manualTest(){
        TestCase testCase = new TestCase();
        testCase.number = "-9.99";
        testCase.scale = 10;
        if(fullTest(testCase)){
            System.out.println("success!");
        }else{
            System.out.println("failed!");
        }
    }

    private static void randomTest(){
        for(int i = 0; i < 100000; i++){
            TestCase testCase = generateCase();
            if(!fullTest(testCase)){
                System.out.println("some thing error find.");
                return;
            }
        }
        System.out.println("test passed.");
    }

    private static boolean fullTest(TestCase testCase){
        Iterator<Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
        while(itr.hasNext()){
            Entry<NumberRoundingMode, RoundingMode> entry = itr.next();
            if(!singleTest(testCase.number, testCase.scale, entry.getKey(), entry.getValue())){
                System.out.println("find error, number : " + testCase.number + ", scale : " + testCase.scale + ".");
                return false;
            }
        }
        return true;
    }

    private static boolean singleTest(String num, int scale, NumberRoundingMode numberRoundingMode, RoundingMode roundingMode){
        String result = NumberRoundingMode.roundOff(num, scale, numberRoundingMode);
        String ans = new BigDecimal(num).setScale(scale, roundingMode).toString();
        System.out.println("result : " + result + ", except : " + ans + ", mode : " + numberRoundingMode.name());
        boolean check = ans.equals(result);
        if(!check){
            if(ans.contains("E")){
                System.out.println("--------find scientific notation.-----------");
                check = true;
            }
        }
        return check;
    }

    private static final Random r = new Random();

    private static TestCase generateCase(){
        String decimal = DebugUtil.randomDecimal();
        int dotPos = decimal.indexOf('.');
        int scale;
        if(dotPos == -1){
            scale = r.nextInt(10);
        }else{
            scale = r.nextInt(decimal.length() - dotPos);
        }
        TestCase testCase = new TestCase();
        testCase.number = decimal;
        testCase.scale = scale;
        return testCase;
    }

    private static class TestCase{
        private String number;
        private int scale;
    }

}
