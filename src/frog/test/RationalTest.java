package frog.test;

import frog.calculator.math.number.RationalNumber;

public class RationalTest {


    public static void main(String[] args){
        RationalNumber a = new RationalNumber("1128391234534535345345345345354345983789127389172839182738923.456");
        RationalNumber b = new RationalNumber("1239345345345345090090.456");
        RationalNumber add = a.mult(b);
        System.out.println(add.toDecimal(90));
        System.out.println(add);
//        RationalNumber g = new RationalNumber("123.456", 2);
//        System.out.println(g.toDecimal(90));
    }

}