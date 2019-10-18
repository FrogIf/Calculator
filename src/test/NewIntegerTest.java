package test;

import frog.calculator.math.rational.IntegerNumber;
import frog.calculator.math.rational.NewIntegerNumber;

import java.util.Random;

public class NewIntegerTest {


    public static void main(String[] args){
        // 加减法测试
//        testAssignForAdd("3364279374317476177436592912260245437304671144011014317709976665123387651039872274678514901817",
//                "6721145924893476187435776248491755040713616671193329851431979651705274731767121604782500423896");
        testAdd();
//        testAssignForAdd("087509679329817194560777382538298208",
//                "727437156478976777716439222618063435653");
//        testAssignForAdd("611303724177606193745596491566497125",
//                "1786422911145257050633445762297958469");
    }

    public static void testAdd(){
        long sum = 0;
        for(int i = 0; i < 1000000000; i++){
            String left = randomBigNumber();
            String right = randomBigNumber();
            sum += testAssignForAdd(left, right);
        }
        System.out.println("fast : " + sum);
    }

    public static long testAssignForAdd(String left, String right){
        System.out.println(left + " + " + right + ":");
//        long a = System.currentTimeMillis();
        IntegerNumber base = IntegerNumber.valueOf(left).sub(IntegerNumber.valueOf(right));
//        long b = System.currentTimeMillis();
        NewIntegerNumber result = NewIntegerNumber.valueOf(left).sub(NewIntegerNumber.valueOf(right));
//        long c = System.currentTimeMillis();
        if(!result.toString().equals(base.toString())){
            throw new IllegalStateException("计算出错!" + result.toString() + "!=" + base.toString());
        }else{
            System.out.println(result);
        }

        return 0;
//        return b - a - c + b;
    }

    public static String randomBigNumber(){
        Random r = new Random();

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 20; i++){
            sb.append(r.nextInt(100));
        }
        return sb.toString();
    }

}
