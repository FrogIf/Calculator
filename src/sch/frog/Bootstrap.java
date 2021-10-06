package sch.frog;

import java.util.Scanner;

import sch.frog.calculator.ICalculator;
import sch.frog.calculator.SimpleCalculator;
import sch.frog.calculator.runtime.GeneralCalculatorSession;
import sch.frog.calculator.runtime.ICalculatorSession;
import sch.frog.calculator.math.number.ComplexNumber;

public class Bootstrap {

	public static void main(String[] args){
        ICalculator<ComplexNumber> calculator = new SimpleCalculator();

        Scanner sc = new Scanner(System.in);

        System.out.println("**********Calculator************");

        ICalculatorSession session = new GeneralCalculatorSession();

        while(sc.hasNext()){
            String expression = sc.nextLine();
            if("exit".equals(expression)) {
                System.out.println("bye !");
                break;
            }

            try{
                ComplexNumber result = calculator.calculate(expression, session);
                System.out.println(result.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        sc.close();
    }
}
