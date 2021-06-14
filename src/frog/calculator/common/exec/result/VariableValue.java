package frog.calculator.common.exec.result;

import frog.calculator.compile.semantic.IValue;
import frog.calculator.util.StringUtils;

public class VariableValue implements IValue{

    /**
     * 变量名
     */
    private final String name;

    /**
     * 变量值
     */
    private IValue value;

    /**
     * 该值是否可以修改
     */
    private final boolean modifiable;

    private boolean hasSetValue = false;

    public VariableValue(String name){
        this(name, true);
    }

    public VariableValue(String name, boolean modifiable){
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("variable name can't be null.");
        }
        this.name = name;
        this.modifiable = modifiable;
    }

    /**
     * 为变量设置值
     * @param value
     */
    public void setValue(IValue value){
        if(value instanceof VariableValue){
            throw new IllegalArgumentException("variable's value can't be variable for : " + this.name);
        }
        if(hasSetValue && !modifiable){
            throw new IllegalStateException("this variable can't be modify: " + this.name);
        }
        this.value = value;
        this.hasSetValue = true;
    }

    /**
     * 获取变量名
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * 获取值, 该值不会是Variable类型的对象
     * @return
     */
    public IValue getValue(){
        return this.value;
    }
}
