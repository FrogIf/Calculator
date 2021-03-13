package frog.calculator.microexec.exception;

public class IncorrectStructureException extends NonsupportOperateException {

    private static final long serialVersionUID = 1L;

    public IncorrectStructureException(String trigger, String msg) {
        super(trigger, msg);
    }

}
