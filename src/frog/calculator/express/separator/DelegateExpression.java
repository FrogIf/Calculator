package frog.calculator.express.separator;

import frog.calculator.operator.IOperator;

public class DelegateExpression extends SeparatorExpression {
    public DelegateExpression(String symbol, int buildFactor, IOperator operator, boolean fifo) {
        super(symbol, buildFactor, operator, fifo);
    }

    public DelegateExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator);
    }

//    @Override
//    public IExpression assembleTree(IExpression expression) {
//        IExpression root = super.assembleTree(expression);
//        if(root == null){
//            System.out.println("root null Find : ");
//        }
//        return ;
//    }
}
