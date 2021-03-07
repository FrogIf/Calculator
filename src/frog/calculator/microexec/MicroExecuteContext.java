package frog.calculator.microexec;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.util.collection.IList;

public class MicroExecuteContext implements IExecuteContext {

    private IList<ComplexNumber> result;

    public IList<ComplexNumber> getResult() {
        return result;
    }

    public void setResult(IList<ComplexNumber> result) {
        this.result = result;
    }

}
