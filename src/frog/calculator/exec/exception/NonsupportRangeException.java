package frog.calculator.exec.exception;

public class NonsupportRangeException extends NonsupportOperateException{
    public NonsupportRangeException(String trigger, String msg) {
        super(trigger, msg);
    }
}