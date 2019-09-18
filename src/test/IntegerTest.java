package test;

import frog.calculator.math.INT.IntegerNumber;

import java.util.Random;

public class IntegerTest {

    public static void main(String[] args){
//        BigInteger a = new BigInteger("123456");
//        BigInteger b = new BigInteger("456");
//        BigInteger remainder = a.remainder(b);
//        System.out.println(remainder);
//        assignTest(-569062871, 1072174604);
//        randomTest();
//        System.out.println(gcd(12, 6));
//        randomTestHalf();
//        for(int i = 0; i < 1000; i++){
//            System.out.println(i);
//            testHalfAssign(i * 2);
//        }
        testGCD();
//        assignGCDTest(0, 4);
//        assignGCDTest(1009079744, 200842384);
    }

    private static void testGCD(){
        Random r = new Random();
        for(int i = 0; i < 1000000; i++){
            int left = r.nextInt(Integer.MAX_VALUE >> 1);
            int right = r.nextInt(Integer.MAX_VALUE >> 1);

            assignGCDTest(left, right);
        }
    }

    private static void assignGCDTest(int left, int right){
        int gcd = gcd(left, right);
        IntegerNumber a = new IntegerNumber(String.valueOf(left));
        IntegerNumber b = new IntegerNumber(String.valueOf(right));
        System.out.println(left + ", " + right + " --> " + gcd);
        IntegerNumber res = a.greatestCommonDivisor(b);
        if(!res.toString().equals(String.valueOf(gcd))){
            throw new IllegalStateException(left + ", " + right + " --> " + gcd + ", but " + res);
        }
    }

    public static int gcd(int a, int b){
        if(a == 0){ return b; }
        if(b == 0){ return a; }
        if(a % 2 == 0 && b % 2 == 0){
            return 2 * gcd(a >> 1, b >> 1);
        }else if(a % 2 == 0){
            return gcd(a >> 1, b);
        }else if(b % 2 == 0){
            return gcd(a, b >> 1);
        }else{
            return gcd(Math.abs(a - b), Math.min(a, b));
        }
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

    private static void testHalfAssign(int num){
//        String result = IntegerNumber.floorHalf(new StringBuilder(String.valueOf(num)).reverse()).reverse().toString();
//        if(!result.equals(String.valueOf(num / 2))){
//            throw new IllegalStateException(result + " != " + (num / 2));
//        }
    }

    public static void randomTestHalf(){
        Random r = new Random();
        for(int i = 0; i < 1000000; i++){
            int a = r.nextInt(Integer.MAX_VALUE >> 1) << 1; // 随机生成一个偶数
            System.out.println(a + "/" + 2);
            testHalfAssign(a);
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
