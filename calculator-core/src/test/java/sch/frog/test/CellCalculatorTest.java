package sch.frog.test;

import io.github.frogif.calculator.cell.CellCalculator;
import io.github.frogif.calculator.number.impl.ComplexNumber;
import io.github.frogif.calculator.number.impl.NumberRoundingMode;

import java.util.Scanner;

public class CellCalculatorTest {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        CellCalculator calculator = new CellCalculator();
        while(true){
            String expression = sc.nextLine();
            if("exit".equals(expression)){
                break;
            }
            ComplexNumber result = calculator.calculate(expression);
            if(result != null){
                String decimal = result.decimal(10, NumberRoundingMode.HALF_UP);
                System.out.println(decimal);
            }
        }
    }

}
