package sch.frog.test;

import java.io.PrintStream;
import java.util.Scanner;

import sch.frog.calculator.facade.Calculator;
import sch.frog.calculator.facade.ExecuteSession;
import sch.frog.calculator.io.IInputStream;
import sch.frog.calculator.io.IOutputStream;

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
                calculator.calculate(new IInputStream() {
                    boolean read = false;
                    @Override
                    public String readLine() {
                        if(read){
                            return null;
                        }
                        read = true;
                        return expression;

                    }
                }, new IOutputStream() {
                    @Override
                    public void println(String text) {
                        out.println(text);
                    }

                    @Override
                    public void print(String text) {
                        out.print(text);
                    }
                }, session);
            }catch (Exception e){
                e.printStackTrace(out);
            }
        }

        sc.close();
    }
}
