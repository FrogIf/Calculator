package frog.test;

import java.util.Random;

import frog.calculator.math.number.IntegerNumber;

/**
 * 最大公约数准确性测试
 */
public class GcdTest {

    public static void test(){
        Random r = new Random();
        int i = Integer.MAX_VALUE;
        while(i > 0){
            int a = r.nextInt(Integer.MAX_VALUE);
            int b = r.nextInt(Integer.MAX_VALUE);

            IntegerNumber gcd = IntegerNumber.valueOf(a).gcd(IntegerNumber.valueOf(b));
            if(!gcd.toString().equals(String.valueOf(gcd(a, b)))){
                throw new IllegalStateException("a : " + a + ", b : " 
                    + b + " --> real : " + String.valueOf(gcd(a, b)) + "but :" + gcd.toString());
            }else{
                System.out.println("a : " + a + ", b : " + b + " --> " + gcd.toString());
            }
            i--;
        }
    }

    private static int gcd(int a, int b){
        if(b == 0){
            return a;
        }else{
            return gcd(b, a % b);
        }
    }
    
}
