package frog.calculator.common.exec.exception;

public class IncorrectStructureException extends NonsupportOperateException {

    private static final long serialVersionUID = 1L;

    public IncorrectStructureException(String trigger, String msg) {
        super(trigger, msg);
    }

}
