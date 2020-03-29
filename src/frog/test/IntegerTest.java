package frog.test;

import frog.calculator.math.exception.DivideByZeroException;
import frog.calculator.math.number.IntegerNumber;
import frog.test.model.OldIntegerNumber;

import java.util.Random;

public class IntegerTest {

    public static void main(String[] args){
//        IntegerNumber num = IntegerNumber.valueOf("123456789");
//        for(int i = 0; i < 20; i++){
//            System.out.println(i + "\t" + num.decLeftShift(i));
//        }
//        randomTest();
//        assignTest("8", "18327732431324085593954544");
        divTest("100000000000", "2");
    }

    public static void randomTest(){
        for (int i = 0; i < 100000000; i++){
            assignTest(randomBigNumber(50), randomBigNumber(9));
        }
    }

    public static void assignTest(String left, String right){
        System.out.println("num : " + left + ", " + right);
//        addTest(left, right);
//        subTest(left, right);
//        multTest(left, right);
//        divTest(left, right);
//        gcdTest(left, right);
          addIntTest(left, Integer.parseInt(right));
    }

    public static void gcdTest(String left, String right){
        System.out.println("operator : gcd");
        OldIntegerNumber answer = OldIntegerNumber.valueOf(left).greatestCommonDivisor(OldIntegerNumber.valueOf(right));
        IntegerNumber result = IntegerNumber.valueOf(left).gcd(IntegerNumber.valueOf(right));
        check(answer, result);
    }

    public static void addIntTest(String left, int right){
        System.out.println("operator : +");
        OldIntegerNumber answer = OldIntegerNumber.valueOf(left).add(OldIntegerNumber.valueOf(right));
        IntegerNumber result = IntegerNumber.valueOf(left).add(right);
        check(answer, result);
    }

    private static void check(OldIntegerNumber answer, IntegerNumber result){
        if(!answer.toString().equals(result.toString())){
            throw new IllegalStateException("result : " + result.toString() + ", expect : " + answer.toString());
        }else{
            System.out.println("result :" + result.toString());
        }
    }

    public static void addTest(String left, String right){
        System.out.println("operator : +");
        OldIntegerNumber answer = OldIntegerNumber.valueOf(left).add(OldIntegerNumber.valueOf(right));
        IntegerNumber result = IntegerNumber.valueOf(left).add(IntegerNumber.valueOf(right));
        check(answer, result);
    }

    public static void subTest(String left, String right){
        System.out.println("operator : -");
        OldIntegerNumber answer = OldIntegerNumber.valueOf(left).sub(OldIntegerNumber.valueOf(right));
        IntegerNumber result = IntegerNumber.valueOf(left).sub(IntegerNumber.valueOf(right));
        check(answer, result);
    }

    public static void multTest(String left, String right){
        System.out.println("operator : *");
        OldIntegerNumber answer = OldIntegerNumber.valueOf(left).mult(OldIntegerNumber.valueOf(right));
        IntegerNumber result = IntegerNumber.valueOf(left).mult(IntegerNumber.valueOf(right));
        check(answer, result);
    }

    public static void divTest(String left, String right){
        System.out.println("operator : /");
        try{
            OldIntegerNumber answer = OldIntegerNumber.valueOf(left).div(OldIntegerNumber.valueOf(right));
            IntegerNumber result = IntegerNumber.valueOf(left).div(IntegerNumber.valueOf(right));
            if(!answer.toString().equals(result.toString())){
                throw new IllegalStateException("result : " + result.toString() + ", expect : " + answer.toString());
            }else{
                System.out.println(result.toString());
            }
        }catch (DivideByZeroException e){
            System.out.println(e.getMessage());
        }
    }

    public static String randomBigNumber(int maxLen){
        Random r = new Random();

        int pos = (int) (r.nextDouble() * maxLen) + 1;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < pos; i++){
            sb.append(r.nextInt(10));
        }
        return sb.toString();
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



}
