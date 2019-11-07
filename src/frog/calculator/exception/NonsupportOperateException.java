package frog.calculator.exception;

public class NonsupportOperateException extends RuntimeException {
    public NonsupportOperateException(String trigger, String msg){
        super(trigger + " : " + msg);
    }
}
