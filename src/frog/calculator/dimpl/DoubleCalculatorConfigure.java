package frog.calculator.dimpl;

import frog.calculator.ICalculatorConfigure;
import frog.calculator.dimpl.operate.opr.mid.AddDoubleOperator;
import frog.calculator.dimpl.operate.opr.mid.DivDoubleOperator;
import frog.calculator.dimpl.operate.opr.mid.MultDoubleOperator;
import frog.calculator.dimpl.operate.opr.mid.SubDoubleOperator;
import frog.calculator.dimpl.operate.opr.right.FactorialDoubleOperator;
import frog.calculator.dimpl.operate.opr.right.PercentDoubleOperator;
import frog.calculator.dimpl.resolve.DoubleNumberOperatorFactory;
import frog.calculator.express.IExpression;
import frog.calculator.express.context.DefaultExpressionContext;
import frog.calculator.express.context.IExpressContext;
import frog.calculator.express.mid.MidExpression;
import frog.calculator.express.right.RightExpression;
import frog.calculator.express.round.RoundCloseExpression;
import frog.calculator.express.round.RoundOpenExpression;
import frog.calculator.register.TreeRegister;
import frog.calculator.register.IRegister;
import frog.calculator.resolve.IResolver;
import frog.calculator.resolve.resolver.*;

public class DoubleCalculatorConfigure implements ICalculatorConfigure {

    private static final IExpressContext context = new DefaultExpressionContext(0, 100);

    // 新增运算符, 表达式, 只需修改这里
    private final IExpression[] expArr = new IExpression[]{
            new MidExpression(new AddDoubleOperator(), 1, "+"),
            new MidExpression(new SubDoubleOperator(), 1, "-"),
            new MidExpression(new MultDoubleOperator(), 2, "*"),
            new MidExpression(new DivDoubleOperator(), 2, "/"),
            new RightExpression(new FactorialDoubleOperator(), 3, "!"),
            new RightExpression(new PercentDoubleOperator(), 3, "%"),
            new RoundOpenExpression(null, 100, "(", ")", context),
            new RoundCloseExpression(null, ")")
    };


    private IResolver resolver;

    private IRegister register;

    private IResolver initResolver(){
        DefaultResolveResultFactory resolveResultFactory = new DefaultResolveResultFactory();
        SymbolResolver symbolResolver = new SymbolResolver(resolveResultFactory);

        INumberExpressionFactory numberExpressionFactory = new DefaultNumberExpressionFactory(context, new DoubleNumberOperatorFactory());

        NumberResolver numberResolver = new NumberResolver(resolveResultFactory, numberExpressionFactory);
        numberResolver.setNextResolver(symbolResolver);

        return numberResolver;
    }

    private IRegister initRegister(){
        TreeRegister register = new TreeRegister();
        for(IExpression exp : expArr){
            register.registe(exp.symbol(), exp, exp.getOperator());
        }
        return register;
    }
//    private IRegister initRegister(){
//        DictionaryRegister register = new DictionaryRegister();
//        for(IExpression exp : expArr){
//            register.registe(exp.symbol(), exp, exp.getOperator());
//        }
//        return register;
//    }

    public DoubleCalculatorConfigure() {
        this.register = initRegister();
        this.resolver = initResolver();
    }

    @Override
    public IResolver getResolver() {
        return this.resolver;
    }

    @Override
    public IRegister getRegister() {
        return this.register;
    }
}
