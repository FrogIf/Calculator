package sch.frog.calculator.base;

import sch.frog.calculator.base.number.IntegerNumber;

import java.util.Random;

public class GcdTest {

    public static void main(String[] args){
        int test = 1000;
        System.out.println("gcd test ...");
        Random r = new Random();
        for(int i = 0; i < test; i++){
            int a = r.nextInt(Integer.MAX_VALUE);
            int b = r.nextInt(Integer.MAX_VALUE);
            IntegerNumber gcd = IntegerNumber.valueOf(a).gcd(IntegerNumber.valueOf(b));
            int s = gcd(a, b);
            boolean result = gcd.toString().equals(String.valueOf(s));
            if(!result){
                System.out.println("failed " + a + ", " + b + " --> expect " + s + ", but " + gcd.toPlainString());
            }
        }
        System.out.println("gcd test finish");
    }

    private static int gcd(int a, int b){
        if(b == 0){
            return a;
        }else{
            return gcd(b, a % b);
        }
    }

}
