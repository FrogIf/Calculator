package frog.calculator.operator.exception;

public class NonsupportRangeException extends NonsupportOperateException{
    public NonsupportRangeException(String trigger, String msg) {
        super(trigger, msg);
    }
}
