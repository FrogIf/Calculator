package frog.calculator.micro;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.exec.exception.ExecuteException;
import frog.calculator.compile.semantic.result.IValue;
import frog.calculator.compile.semantic.result.VariableValue;
import frog.calculator.math.number.ComplexNumber;

public class MicroUtil {

    private MicroUtil(){
        // do nothing.
    }

    public static ComplexNumber getNumber(IValue value, IExecuteContext context){
        if(value instanceof ComplexValue){
            return ((ComplexValue)value).getValue();
        }else if(value instanceof VariableValue){
            IValue val = ((VariableValue)value).getValue();
            return getNumber(val, context);
        }else if(value == null){
            return null;
        }else{
            throw new ExecuteException("can't get value from " + value.getClass());
        }
    }

}
