package test;

import frog.calculator.math.RationalNumber;

public class RationalTest {


    public static void main(String[] args){
        RationalNumber a = new RationalNumber("11283912983789127389172839182738923.456");
        RationalNumber b = new RationalNumber("1239090090.456");
        RationalNumber add = a.div(b);
        System.out.println(add.toDecimal(90));
        System.out.println(add);
//        RationalNumber g = new RationalNumber("123.456", 2);
//        System.out.println(g.toDecimal(90));
    }

}