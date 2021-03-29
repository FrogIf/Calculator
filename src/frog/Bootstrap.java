package frog;

import frog.calculator.ICalculator;
import frog.calculator.SimpleCalculator;
import frog.calculator.common.exec.result.SymbolValue;
import frog.calculator.connect.GeneralCalculatorSession;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.NumberRoundingMode;

import java.util.Scanner;

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
                result.setScale(10, NumberRoundingMode.HALF_UP);
                System.out.println(result.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        sc.close();
    }
}
