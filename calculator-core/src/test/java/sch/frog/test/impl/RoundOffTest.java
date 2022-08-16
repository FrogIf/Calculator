package sch.frog.test.impl;

import sch.frog.calculator.math.number.NumberRoundingMode;
import sch.frog.calculator.util.collection.IMap;
import sch.frog.calculator.util.collection.ISet;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.TreeMap;
import sch.frog.test.util.TestUtil;
import sch.frog.test.ICaseObject;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.Random;

import sch.frog.test.AbstractTestContent;

/**
 * 舍入测试
 */
public class RoundOffTest extends AbstractTestContent<RoundOffTestObj>{

    private static final IMap<NumberRoundingMode, RoundingMode> map = new TreeMap<>((a, b) -> a.compareTo(b));
    private static final ISet<IMap.Entry<NumberRoundingMode, RoundingMode>> entrySet;
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

    @Override
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

    @Override
    protected boolean singleStepTest(RoundOffTestObj caseObj) {
        Iterator<IMap.Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
        while(itr.hasNext()){
            IMap.Entry<NumberRoundingMode, RoundingMode> entry = itr.next();
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

    @Override
    protected RoundOffTestObj parse(String content) {
        String[] split = content.split("|");
        RoundOffTestObj obj = new RoundOffTestObj();
        obj.setNumber(split[0]);
        obj.scale(Integer.parseInt(split[1]));
        return obj;
    }
    
}

class RoundOffTestObj implements ICaseObject{

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

    @Override
    public String content() {
        return number + "|" + scale;
    }

}