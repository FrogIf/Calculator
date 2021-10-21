package sch.frog.test.impl;

import sch.frog.calculator.math.number.IntegerNumber;
import sch.frog.test.AbstractTestContent;
import sch.frog.test.ICaseObject;
import java.util.Random;

public class GcdTest extends AbstractTestContent<GcdTestObj> {

    private final Random r = new Random();

    @Override
    protected GcdTestObj generateCase() {
        return new GcdTestObj(r.nextInt(Integer.MAX_VALUE), r.nextInt(Integer.MAX_VALUE));
    }

    @Override
    protected boolean singleStepTest(GcdTestObj caseObj) {
        int a = caseObj.getA();
        int b = caseObj.getB();
        IntegerNumber gcd = IntegerNumber.valueOf(a).gcd(IntegerNumber.valueOf(b));
        return gcd.toString().equals(String.valueOf(gcd(a, b)));
    }

    private static int gcd(int a, int b){
        if(b == 0){
            return a;
        }else{
            return gcd(b, a % b);
        }
    }

    @Override
    public GcdTestObj parse(String content) {
        String[] arr = content.split("-");
        return new GcdTestObj(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
    }
    
}

class GcdTestObj implements ICaseObject{

    private int a;

    private int b;

    public GcdTestObj(int a, int b){
        this.a = a;
        this.b = b;
    }

    public int getA(){
        return this.a;
    }

    public int getB(){
        return this.b;
    }

    @Override
    public String content() {
        return a + "-" + b;
    }

}