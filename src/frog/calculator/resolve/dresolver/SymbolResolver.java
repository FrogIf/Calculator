package frog.calculator.resolve.dresolver;

import frog.calculator.express.IExpression;
import frog.calculator.express.mid.AddExpression;
import frog.calculator.express.mid.DivExpression;
import frog.calculator.express.mid.MultExpression;
import frog.calculator.express.mid.SubExpression;
import frog.calculator.express.right.FactorialExpression;
import frog.calculator.express.right.PercentExpression;
import frog.calculator.operate.IOperator;
import frog.calculator.operate.doubleopr.opr.mid.AddDoubleOperator;
import frog.calculator.operate.doubleopr.opr.mid.DivDoubleOperator;
import frog.calculator.operate.doubleopr.opr.mid.MultDoubleOperator;
import frog.calculator.operate.doubleopr.opr.mid.SubDoubleOperator;
import frog.calculator.operate.doubleopr.opr.right.FactorialDoubleOperator;
import frog.calculator.operate.doubleopr.opr.right.PercentDoubleOperator;
import frog.calculator.resolve.register.Registry;

public class SymbolResolver extends AResolver {

    private Registry registry = null;

    private static class ExpEntry{
        private String exp;

        private IExpression expression;

        private IOperator operator;

        public ExpEntry(String exp, IExpression expression, IOperator operator) {
            this.exp = exp;
            this.expression = expression;
            this.operator = operator;
        }
    }

    private final ExpEntry[] entryArr = new ExpEntry[]{
            new ExpEntry("+", new AddExpression(), new AddDoubleOperator()),
            new ExpEntry("-", new SubExpression(), new SubDoubleOperator()),
            new ExpEntry("*", new MultExpression(), new MultDoubleOperator()),
            new ExpEntry("/", new DivExpression(), new DivDoubleOperator()),
            new ExpEntry("!", new FactorialExpression(), new FactorialDoubleOperator()),
            new ExpEntry("%", new PercentExpression(), new PercentDoubleOperator())
    };

    private void init(){
        this.registry = new Registry();
        for(ExpEntry expEntry : entryArr){
            this.registry.registe(expEntry.exp, expEntry.expression, expEntry.operator);
        }
    }

    public SymbolResolver() {
        this.init();
    }

    @Override
    protected void resolve(char[] chars, int startIndex, AResolveResult resolveResult) {
        Registry registry = this.registry.retrieveRegistryInfo(chars, startIndex);
        if(registry == null){
            throw new IllegalArgumentException("unrecognize expression.");
        }else{
            IExpression expression = registry.getExpression();
            if(expression == null){
                throw new IllegalStateException("can't get express.");
            }
            IOperator operator = registry.getOperator();
            if(operator == null){
                throw new IllegalStateException("can't operate this express.");
            }
            IExpression exp = expression.clone();
            exp.setOperator(operator.copyThis());
            resolveResult.setExpression(exp);
            String completeSymbol = registry.getCompleteSymbol();
            if(completeSymbol != null){
                resolveResult.setSymbol(completeSymbol);
                resolveResult.setEndIndex(startIndex + completeSymbol.length() - 1);
            }
        }
    }

}
