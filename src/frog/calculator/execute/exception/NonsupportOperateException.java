package frog.calculator.execute.exception;

public class NonsupportOperateException extends RuntimeException {
    public NonsupportOperateException(String trigger, String msg){
        super(trigger + " : " + msg);
    }

    public NonsupportOperateException(String trigger){
        super(trigger + " : non support this operate.");
    }
}
