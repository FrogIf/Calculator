package frog.calculator.microexec;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.util.collection.IList;

public class MicroExecuteContext implements IExecuteContext {

    private IList<ComplexNumber> result;

    private ICalculatorSession session;

    public IList<ComplexNumber> getResult() {
        return result;
    }

    public void setResult(IList<ComplexNumber> result) {
        this.result = result;
    }

    public void setSession(ICalculatorSession session){
        this.session = session;
    }

    public ICalculatorSession getSession(){
        return this.session;
    }

}
