package frog.calculator.exception;

public class IncorrectStructureException extends NonsupportOperateException {

    public IncorrectStructureException(String trigger, String msg) {
        super(trigger, msg);
    }

}
