package io.github.frogif.calculator.number;

import io.github.frogif.calculator.number.impl.NumberRoundingMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class RoundOffTest {

    public static void main(String[] args){
        int test = 10000;
        RoundOffTest t = new RoundOffTest();
        System.out.println("round off test");
        for(int i = 0; i < test; i++){
            RoundOffTestObj caseObj = t.generateCase();
            if(!t.singleStepTest(caseObj)){
                System.out.println(String.format("test failed, case object : %s, test class : %s",
                        caseObj.content(), RoundOffTest.class.getSimpleName()));
            }
        }
        System.out.println("round off test finish");
    }

    private static final Map<NumberRoundingMode, RoundingMode> map = new TreeMap<>(Enum::compareTo);
    private static final Set<Map.Entry<NumberRoundingMode, RoundingMode>> entrySet;
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

    private static final Random r = new Random();

    protected RoundOffTestObj generateCase() {
        String decimal = TestUtil.randomDecimal();
        int dotPos = decimal.indexOf('.');
        int scale;
        if(dotPos == -1){
            scale = r.nextInt(10);
        }else{
            scale = r.nextInt(decimal.length() - dotPos);
        }

        RoundOffTestObj obj = new RoundOffTestObj();
        obj.setNumber(decimal);
        obj.scale(scale);
        return obj;
    }

    protected boolean singleStepTest(RoundOffTestObj caseObj) {
        Iterator<Map.Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
        while(itr.hasNext()){
            Map.Entry<NumberRoundingMode, RoundingMode> entry = itr.next();
            if(!test(caseObj.getNumber(), caseObj.getScale(), entry.getKey(), entry.getValue())){
                return false;
            }
        }
        return true;
    }

    private static boolean test(String num, int scale, NumberRoundingMode numberRoundingMode, RoundingMode roundingMode){
        String result = NumberRoundingMode.roundOff(num, scale, numberRoundingMode);
        String ans = new BigDecimal(num).setScale(scale, roundingMode).toString();
        boolean check = ans.equals(result);
        if(!check){
            if(ans.contains("E")){
                check = true;
            }
        }
        return check;
    }

    protected RoundOffTestObj parse(String content) {
        String[] split = content.split("|");
        RoundOffTestObj obj = new RoundOffTestObj();
        obj.setNumber(split[0]);
        obj.scale(Integer.parseInt(split[1]));
        return obj;
    }

}


class RoundOffTestObj {

    private String number;

    private int scale;

    public void setNumber(String number){
        this.number = number;
    }

    public void scale(int scale){
        this.scale = scale;
    }

    public int getScale(){
        return this.scale;
    }

    public String getNumber(){
        return this.number;
    }

    public String content() {
        return number + "|" + scale;
    }

}