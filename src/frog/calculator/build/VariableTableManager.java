package frog.calculator.build;

import frog.calculator.build.register.IRegister;
import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.CommonResolveResultFactory;
import frog.calculator.build.resolve.IResolveResult;
import frog.calculator.build.resolve.IResolveResultFactory;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Stack;

public class VariableTableManager implements IVariableTableManager {

    private final Stack<IRegister<IExpression>> localRegisterStack = new Stack<>();

    private final static IResolveResultFactory resolveResultFactory = new CommonResolveResultFactory();

    @Override
    public void createLocalVariableTable() {
        localRegisterStack.push(new SymbolRegister<>());
    }

    @Override
    public IRegister<IExpression> popLocalVariableTable() {
        if(this.localRegisterStack.isEmpty()){
            throw new IllegalStateException("no local variable region can be pop.");
        }else{
            return localRegisterStack.pop();
        }
    }

    @Override
    public boolean isEmpty() {
        return localRegisterStack.isEmpty();
    }

    @Override
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
        this.localRegisterStack.top().insert(expression);
    }

    @Override
    public IResolveResult resolve(char[] chars, int startIndex) {
        IExpression expression = null;

        Iterator<IRegister<IExpression>> iterator = localRegisterStack.iterator();
        while(iterator.hasNext()){
            IRegister<IExpression> reg = iterator.next();
            IExpression tExp = reg.retrieve(chars, startIndex);
            if(tExp != null && (expression == null || tExp.symbol().length() > expression.symbol().length())){
                expression = tExp;
            }
        }

        return this.resolveResultFactory.createResolverResultBean(expression);
    }
}
