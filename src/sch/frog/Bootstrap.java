package sch.frog;

import java.io.PrintStream;
import java.util.Scanner;

import sch.frog.calculator.facade.Calculator;
import sch.frog.calculator.facade.ExecuteSession;
public class Bootstrap {

	public static void main(String[] args){
        PrintStream out = System.out;

        Calculator calculator = new Calculator();

        Scanner sc = new Scanner(System.in);

        out.println("**********Calculator************");

        ExecuteSession session = new ExecuteSession();

        while(sc.hasNext()){
            String expression = sc.nextLine();
            if("exit".equals(expression)) {
                out.println("bye !");
                break;
            }

            try{
                calculator.calculate(expression, out, session);
            }catch (Exception e){
                e.printStackTrace(out);
            }
        }

        sc.close();
    }
}
