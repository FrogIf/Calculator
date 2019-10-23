package test;

import frog.calculator.math.rational.IntegerNumber;
import frog.calculator.math.rational.NewIntegerNumber;

import java.util.Random;

public class NewIntegerTest {


    public static void main(String[] args){
        // 加减法测试
//        testAssignForAdd("3364279374317476177436592912260245437304671144011014317709976665123387651039872274678514901817",
//                "6721145924893476187435776248491755040713616671193329851431979651705274731767121604782500423896");
        //47187468018679819296023277144934442824587678616557 / 00000996415707261747036437258911532390180365151033:
//        testAssignForAdd("0",
//                "7407603750487975370025");
        testAdd();
//        testAssignForAdd("45902106636439040318543659634932049081578129338787",
//                "977902361143697991984306183913914299129723513389");
//        testAssignForAdd("611303724177606193745596491566497125",
//                "1786422911145257050633445762297958469");
    }

    public static void testAdd(){
        long sum = 0;
        for(int i = 0; i < 100000000; i++){
            String left = randomBigNumber();
            String right = randomBigNumberRight();
            sum += testAssignForAdd(left, right);
        }
        System.out.println("fast : " + sum);
    }

    public static long testAssignForAdd(String left, String right){
        System.out.println(left + " / " + right + ":");
        NewIntegerNumber rightNum = NewIntegerNumber.valueOf(right);
        if(rightNum.equals(NewIntegerNumber.ZERO)) { return 0; }

        long a = System.currentTimeMillis();
        IntegerNumber base = IntegerNumber.valueOf(left).div(IntegerNumber.valueOf(right));
        long b = System.currentTimeMillis();
        NewIntegerNumber.Remainder remainder = new NewIntegerNumber.Remainder();
        NewIntegerNumber leftNum = NewIntegerNumber.valueOf(left);
        NewIntegerNumber result = leftNum.div(rightNum, remainder);

        if(!leftNum.equals(result.mult(rightNum).add(remainder.getRemainder()))){
            throw new IllegalArgumentException("计算出错:" + result.toString() + " * " + rightNum.toString() + " + " + remainder.getRemainder().toString()
                    + " != " + leftNum.toString());
        }else{
            System.out.println(result);
        }

        long c = System.currentTimeMillis();
        if(!result.toString().equals(base.toString())){
            throw new IllegalStateException("计算出错!" + result.toString() + "!=" + base.toString());
        }else{
            System.out.println(result);
        }

//        return 0;
        return b - a - c + b;
    }

    public static String randomBigNumber(){
        Random r = new Random();

        int pos = (int) (r.nextDouble() * 100) + 1;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < pos; i++){
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    public static String randomBigNumberRight(){
        Random r = new Random();

        int pos = (int) (r.nextDouble() * 100) + 1;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < pos; i++){
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

}
