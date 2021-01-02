package frog.calculator.logger;

public class LoggerFactory {

    private LoggerFactory(){
        // hide constructor
    }
    
    public static ILogger getLogger(String name){
        return new ConsoleLogger(LogLevel.INFO, name);
    }

    public static ILogger getLogger(Class<?> clazz){
        return new ConsoleLogger(LogLevel.INFO, clazz.getName());
    }

}
