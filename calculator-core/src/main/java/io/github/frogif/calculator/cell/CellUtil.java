package io.github.frogif.calculator.cell;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.exception.ExecuteException;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.compile.semantic.result.VariableValue;
import io.github.frogif.calculator.number.impl.ComplexNumber;

public class CellUtil {

    private CellUtil(){
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
