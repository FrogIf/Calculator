package sch.frog.calculator.cell;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.exception.ExecuteException;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.semantic.result.VariableValue;
import sch.frog.calculator.number.impl.ComplexNumber;

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
