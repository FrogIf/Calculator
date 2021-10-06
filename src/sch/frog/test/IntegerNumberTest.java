package sch.frog.test;

import java.util.Random;

import sch.frog.calculator.math.number.IntegerNumber;
import sch.frog.calculator.math.number.NumberRoundingMode;

public class IntegerNumberTest {

    private static final Random r = new Random();

    public static void main(String[] args){
        randomTest();
    }

    private static void randomTest(){
        for(int i = 0; i < 5000; i++){
            String eNumber = generateRandomNumber();
            System.out.println(eNumber);
            IntegerNumber num = IntegerNumber.valueOf(eNumber);
            for(int j = 0; j < 10; j++){
                String scientificNotation = num.scientificNotation(j, NumberRoundingMode.FLOOR);
                int pos = scientificNotation.indexOf("E");
                if(!eNumber.startsWith(scientificNotation.substring(0, pos)) || !eNumber.contains(scientificNotation.substring(pos))){
                    throw new IllegalArgumentException("origin : " + eNumber + ", result : " + scientificNotation + ", scale : " + j);
                }
            }
        }
    }

    private static String generateRandomNumber(){
        StringBuilder result = new StringBuilder();
        int preDot = r.nextInt(9) + 1;
        result.append(Integer.toString(preDot));
        int afterDotCount = r.nextInt(30);
        if(afterDotCount > 0){
            result.append('.');
            for(int i = 0; i < afterDotCount; i++){
                result.append(Integer.toString(r.nextInt(10)));
            }
        }

        result.append('E');
        int eCount = r.nextInt(9) + 1;
        StringBuilder eNum = new StringBuilder();
        for(int i = 0; i < eCount; i++){
            if(i == 0){
                eNum.append(Integer.toString(r.nextInt(9) + 1));
            }else{
                eNum.append(Integer.toString(r.nextInt(10)));
            }
        }
        if(afterDotCount > Integer.parseInt(eNum.toString())){
            eNum = new StringBuilder(Integer.toString(afterDotCount));
        }
        result.append(eNum);
        return result.toString();
    }
    
}
