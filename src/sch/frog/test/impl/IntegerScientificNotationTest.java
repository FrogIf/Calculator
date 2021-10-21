package sch.frog.test.impl;

import sch.frog.test.ICaseObject;
import sch.frog.test.util.TestUtil;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import sch.frog.calculator.math.number.IntegerNumber;
import sch.frog.calculator.math.number.NumberRoundingMode;
import sch.frog.test.AbstractTestContent;

/**
 * 整数科学计数法测试
 */
public class IntegerScientificNotationTest extends AbstractTestContent<IntegerTestObject>{

    private static final NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));

    private static final Random r = new Random();

    static{
        formatter.setMaximumFractionDigits(10000);
    }
    
    @Override
    protected IntegerTestObject generateCase() {
        IntegerTestObject obj = new IntegerTestObject();
        obj.setNumber(TestUtil.randomInteger());
        return obj;
    }

    @Override
    protected boolean singleStepTest(IntegerTestObject caseObj) {
        String number = caseObj.getNumber();
        String ans = formatter.format(new BigInteger(number));
        IntegerNumber num = IntegerNumber.valueOf(number);
        int scale = r.nextInt(10);
        String val = num.scientificNotation(scale, NumberRoundingMode.DOWN);
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

    @Override
    public IntegerTestObject parse(String content) {
        IntegerTestObject testObject = new IntegerTestObject();
        testObject.setNumber(content);
        return testObject;
    }    
}

class IntegerTestObject implements ICaseObject{

    private String number;

    public void setNumber(String number){
        this.number = number;
    }

    public String getNumber(){
        return this.number;
    }

    @Override
    public String content() {
        return number;
    }
}