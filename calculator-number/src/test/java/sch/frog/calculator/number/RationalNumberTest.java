package sch.frog.calculator.number;

import sch.frog.calculator.number.impl.NumberRoundingMode;
import sch.frog.calculator.number.impl.RationalNumber;
import sch.frog.calculator.number.util.StrUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class RationalNumberTest {

    public static void main(String[] args){
        int test = 10000;
        System.out.println("rational number test");
        RationalNumberTest t = new RationalNumberTest();
        for(int i = 0; i < test; i++){
            t.simpleTest();
        }
        System.out.println("rational number test finish");
    }

    private static final NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));

    private static final Random r = new Random();

    private static final Map<NumberRoundingMode, RoundingMode> map = new TreeMap<>(Enum::compareTo);
    private static final Set<Map.Entry<NumberRoundingMode, RoundingMode>> entrySet;
    static{
        formatter.setMaximumFractionDigits(10000);

        map.put(NumberRoundingMode.CEILING, RoundingMode.CEILING);
        map.put(NumberRoundingMode.FLOOR, RoundingMode.FLOOR);
        map.put(NumberRoundingMode.DOWN, RoundingMode.DOWN);
        map.put(NumberRoundingMode.UP, RoundingMode.UP);
        map.put(NumberRoundingMode.HALF_DOWN, RoundingMode.HALF_DOWN);
        map.put(NumberRoundingMode.HALF_UP, RoundingMode.HALF_UP);
        map.put(NumberRoundingMode.HALF_EVEN, RoundingMode.HALF_EVEN);
        entrySet = map.entrySet();
    }

    protected RationalNumberCase generateCase() {
        RationalNumberCase rationalNumberCase = new RationalNumberCase();
        if(r.nextInt(10) % 2 == 1){
            rationalNumberCase.setDecimal(TestUtil.randomDecimal());
        }else{
            rationalNumberCase.setDecimal(randomLitterNumber());
        }
        rationalNumberCase.setScale(r.nextInt(10));

        return rationalNumberCase;
    }

    protected boolean singleStepTest(RationalNumberCase caseObj) {
        if(!testRoundOff(caseObj.getDecimal(), caseObj.getScale())){
            return false;
        }
        if(!testPlainString(caseObj.getDecimal(), caseObj.getScale())){
            return false;
        }
        return true;
    }

    /**
     * plain string测试
     */
    private boolean testPlainString(String decimal, int scale){
        Iterator<Map.Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
        while(itr.hasNext()){
            Map.Entry<NumberRoundingMode, RoundingMode> entry = itr.next();
            RoundingMode roundingMode = entry.getValue();
            String ans = new BigDecimal(decimal).setScale(scale, roundingMode).toPlainString();
            String result = RationalNumber.valueOf(decimal).toPlainString(scale, entry.getKey());
            if(!result.equals(ans)){
                return false;
            }
        }
        return true;
    }

    /**
     * 舍入测试
     */
    private boolean testRoundOff(String decimal, int scale){
        Iterator<Map.Entry<NumberRoundingMode, RoundingMode>> itr = entrySet.iterator();
        while(itr.hasNext()){
            if(!singleTest(decimal, itr.next().getKey(), scale)){
                return false;
            }
        }
        return true;
    }

    private static boolean singleTest(String decimal, NumberRoundingMode numberRoundingMode, int scale){
        BigDecimal bigDecimal = new BigDecimal(decimal);
        String ans = formatter.format(bigDecimal);
        RationalNumber rationNumber = RationalNumber.valueOf(decimal);
        String result = rationNumber.scientificNotation(scale, numberRoundingMode);
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
        }else{
            int sign = pre.startsWith("-") ? 1 : 0;
            if(pre.charAt(sign) == '1'){
                String decimal = answer.substring(0, answer.indexOf('E'));
                String ans = new BigDecimal(decimal).setScale(scale, rm).toPlainString();
                ans = formatter.format(new BigDecimal(ans));
                ans = new BigDecimal(ans.substring(0, ans.indexOf('E'))).setScale(scale, rm).toPlainString();
                success = ans.equals(pre);
            }
        }
        return success;
    }

    // 随机生成小于1的小数
    private String randomLitterNumber(){
        int zeroCount = r.nextInt(10) + 5;
        String num = TestUtil.randomIntegerPositive();
        return "0." + StrUtils.leftFill(num, '0', zeroCount);
    }

    protected RationalNumberCase parse(String content) {
        // TODO Auto-generated method stub
        return null;
    }

    public void simpleTest(){
        String origin = "123.345_5E1";
        RationalNumber valueOf = RationalNumber.valueOf(origin);
        System.out.println(valueOf);
    }

}


class RationalNumberCase {

    private String decimal;

    private int scale;

    public void setDecimal(String decimal){
        this.decimal = decimal;
    }

    public void setScale(int scale){
        this.scale = scale;
    }

    public String getDecimal(){
        return this.decimal;
    }

    public int getScale(){
        return this.scale;
    }

    public String content() {
        return decimal + "|" + scale;
    }

}