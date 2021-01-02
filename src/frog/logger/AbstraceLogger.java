package frog.logger;

public abstract class AbstraceLogger implements ILogger{
    
    private final int level;

    private final String logName;
    
    public AbstraceLogger(LogLevel level, String logName){
        this.logName = logName;
        if(level == LogLevel.DEBUG){
            this.level = 0;
        }else if(level == LogLevel.INFO){
            this.level = 1;
        }else if(level == LogLevel.WARN){
            this.level = 2;
        }else if(level == LogLevel.ERROR){
            this.level = 3;
        }else{
            this.level = 4;
        }
    }

    @Override
    public void error(String msg, Object... obj) {
        if(this.level < 4){
            this.outputLog(msg, obj);
            // TODO 如果是错误, 最后一个参数可能需要特殊处理
        }
    }

    @Override
    public void warn(String msg, Object... obj) {
        if(this.level < 3){
            this.outputLog(msg, obj);
        }
    }

    @Override
    public void info(String msg, Object... obj) {
        if(this.level < 2){
            this.outputLog(msg, obj);
        }
    }

    @Override
    public void debug(String msg, Object... obj) {
        if(this.level < 1){
            this.outputLog(msg, obj);
        }
    }

    protected abstract void outputLog(String msg, Object... obj);


}
