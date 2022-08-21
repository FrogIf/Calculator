package sch.frog.test;

import sch.frog.calculator.facade.Calculator;
import sch.frog.calculator.facade.ExecuteSession;
import sch.frog.calculator.facade.SessionConfiguration;
import sch.frog.calculator.io.IOutputStream;

import java.io.PrintStream;
import java.util.Scanner;

public class Bootstrap {

	public static void main(String[] args){
        PrintStream out = System.out;

        Calculator calculator = new Calculator();

        Scanner sc = new Scanner(System.in);

        out.println("**********Calculator************");

        ExecuteSession session = new ExecuteSession();
        SessionConfiguration sessionConfiguration = session.getSessionConfiguration();
        sessionConfiguration.setShowAST(true);

        try{
            calculator.calculate(() -> {
                String line = sc.nextLine();
                if("exit".equals(line)){
                    return null;
                }
                return line;
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

        out.println("bye !");
        sc.close();
    }
}
