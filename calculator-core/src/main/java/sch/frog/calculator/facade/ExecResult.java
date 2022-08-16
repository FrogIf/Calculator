package sch.frog.calculator.facade;

/**
 * 执行结果
 */
public class ExecResult{

    private boolean success;
    
    private String msg;
    
    public boolean getSuccess(){
        return this.success;
    }
    
    public String getMsg(){
        return this.msg;
    }

    
    public void setSuccess(boolean success){
        this.success = success;
    }
    
    public void setErrorMsg(String msg){
        this.msg = "error : " + msg;
    }

    public void setInfoMsg(String msg){
        this.msg = "info : " + msg;
    }

    public void setWarnMsg(String msg){
        this.msg = "warn : " + msg;
    }
}