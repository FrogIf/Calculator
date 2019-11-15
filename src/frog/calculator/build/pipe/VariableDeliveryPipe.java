package frog.calculator.build.pipe;

import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.register.IRegister;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

/**
 * 变量传递管道
 */
public class VariableDeliveryPipe implements IBuildPipe {

    private String[] symbol;

    private int age;

    private IRegister<IExpression> register;

    public VariableDeliveryPipe(String[] symbol) {
        if(symbol == null || symbol.length == 0){
            throw new IllegalArgumentException("age and symbol array is necessary.");
        }
        this.symbol = symbol;
    }

    public void setRegister(IRegister<IExpression> register) {
        this.register = register;
    }

    @Override
    public String symbol() {
        if(this.age == this.symbol.length){ return null; }
        return this.symbol[this.age++];
    }

    @Override
    public void matchCallBack(IExpressionBuilder builder) throws DuplicateSymbolException {
        if(this.age == symbol.length && this.register != null){
            IList<IExpression> elements = this.register.getElements();
            Iterator<IExpression> iterator = elements.iterator();
            while (iterator.hasNext()){
                builder.addVariable(iterator.next());
            }
        }
    }

}
