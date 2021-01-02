package frog.logger;

public interface ILogger {
    
    void error(String msg, Object... obj);

    void warn(String msg, Object... obj);

    void info(String msg, Object... obj);

    void debug(String msg, Object... obj);
    
}
