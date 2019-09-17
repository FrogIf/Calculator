package test;

import frog.calculator.number.IntegerNumber;

import java.util.Random;

public class IntegerTest {

    public static void main(String[] args){
//        IntegerNumber a = new IntegerNumber("412354981249812894132759813457324573295932475823458239958092345");
//        IntegerNumber b = new IntegerNumber("5949723828432893489234");
//        IntegerNumber add = a.add(b);
//        IntegerNumber sub = a.sub(b);
//        System.out.println(add);
//        System.out.println(sub);
//        assignTest(-569062871, 1072174604);
        randomTest();
    }

    public static void assignTest(long left, long right){
        IntegerNumber a = new IntegerNumber(String.valueOf(left));
        IntegerNumber b = new IntegerNumber(String.valueOf(right));

        System.out.println(left + " + " + right + " = ");
        IntegerNumber add = a.add(b);
        System.out.println(add.toString());
        if(!add.toString().equals(String.valueOf(left + right))){
            throw new IllegalStateException("计算不正确:" + add.toString() + " 不等于 " + (left + right));
        }

        System.out.println(left + " - " + right + " = ");
        IntegerNumber sub = a.sub(b);
        System.out.println(sub.toString());
        if(!sub.toString().equals(String.valueOf(left - right))){
            throw new IllegalStateException("计算不正确:" + sub.toString() + " 不等于 " + (left - right));
        }

        IntegerNumber mult = a.mult(b);
        System.out.println(left + " * " + right + " = ");
        System.out.println(mult.toString());
        if(!mult.toString().equals(String.valueOf(left * right))){
            throw new IllegalStateException("计算不正确:" + mult.toString() + " 不等于 " + (left * right));
        }
    }

    public static void randomTest(){
        Random r = new Random();
        for(int i = 0; i < 1000000; i++){
            int left = r.nextInt(Integer.MAX_VALUE >> 1) - ((Integer.MAX_VALUE >> 1) * r.nextInt(2));
            int right = r.nextInt(Integer.MAX_VALUE >> 1) - ((Integer.MAX_VALUE >> 1) * r.nextInt(2));

            assignTest(left, right);
        }
    }


}
