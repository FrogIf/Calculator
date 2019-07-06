package frog.calculator.register;

import frog.calculator.express.IExpression;
import frog.calculator.operate.IOperator;
import frog.calculator.util.AVLTree;

public class TreeRegister implements IRegister, Comparable<TreeRegister>{

    private char symbol = 0;

    private IExpression expression;

    private IOperator operator;

    private AVLTree<TreeRegister> charRegister = new AVLTree<>();

    public TreeRegister(){ }

    private TreeRegister(char symbol){
        this.symbol = symbol;
    }

    @Override
    public void registe(String exp, IExpression expression, IOperator operator) {
        registe(exp.toCharArray(), 0, expression, null);
    }

    @Override
    public void registe(String exp, IOperator operator) {
        registe(exp.toCharArray(), 0, expression, operator);
    }

    @Override
    public void registe(String exp, IExpression expression) {
        registe(exp.toCharArray(), 0, null, operator);
    }

    private void registe(char[] oprs, int startIndex, IExpression expression, IOperator operator){
        char ch = oprs[startIndex];
        TreeRegister avlTreeRegister = new TreeRegister();
        avlTreeRegister.symbol = ch;

        TreeRegister register = charRegister.find(avlTreeRegister);

        if(register == null){
            register = new TreeRegister();
            register.symbol = ch;
        }

        if(startIndex == oprs.length - 1){
            if(register.operator != null && operator != null){
                throw new IllegalArgumentException("duplicate define operator.");
            }
            if(register.expression != null && expression != null){
                throw new IllegalArgumentException("duplicate define expression.");
            }

            register.operator = operator;
            register.expression = expression;
        }

        charRegister.add(register);
    }

    @Override
    public IExpression getExpression() {
        return this.expression;
    }

    @Override
    public IOperator getOperator() {
        return this.operator;
    }

    @Override
    public IRegister retrieveRegistryInfo(char[] chars, int startIndex) {
        if(chars.length <= startIndex){
            return this;
        }
        char ch = chars[startIndex];
        TreeRegister register = new TreeRegister(ch);

        TreeRegister treeRegister = charRegister.find(register);
        if(treeRegister != null){
            return treeRegister.retrieveRegistryInfo(chars, startIndex + 1);
        }else{
            return this;
        }
    }

    @Override
    public int compareTo(TreeRegister o) {
        return this.symbol - o.symbol;
    }
}
