package io.github.frogif.calculator.compile.semantic.exec.exception;

public class NonsupportOperateException extends RuntimeException {
    public NonsupportOperateException(String trigger, String msg){
        super(trigger + " : " + msg);
    }

    public NonsupportOperateException(String trigger){
        super(trigger + " : non support this operate.");
    }
}
