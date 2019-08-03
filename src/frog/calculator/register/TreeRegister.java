package frog.calculator.register;

import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.util.AVLTree;

public class TreeRegister implements IRegister, Comparable<TreeRegister>{

    private char symbol = 0;

    private IExpression expression;

    private IOperator operator;

    private AVLTree<TreeRegister> nextLetter = new AVLTree<>();

    public TreeRegister(){ }

    private TreeRegister(char symbol){
        this.symbol = symbol;
    }

    private void registe(String exp, IExpression expression, IOperator operator, boolean replace) {
        char[] chars = exp.toCharArray();
        registe(chars, 0, expression, operator, new TreeRegister(), replace);
    }

    @Override
    public void registe(String exp, IExpression expression, IOperator operator) {
        registe(exp, expression, operator, false);
    }

    @Override
    public void registe(String exp, IOperator operator) {
        registe(exp, null, operator, false);
    }

    @Override
    public void registe(String exp, IExpression expression) {
        registe(exp, expression, null, false);
    }

    private void registe(char[] oprs, int startIndex, IExpression expression, IOperator operator, TreeRegister finder, boolean replace){
        char ch = oprs[startIndex];

        finder.symbol = ch;

        TreeRegister register = nextLetter.find(finder);

        if(register == null){
            register = new TreeRegister();
            register.symbol = ch;
        }

        if(startIndex == oprs.length - 1){
            if(!replace){
                if(register.operator != null && operator != null){
                    throw new IllegalArgumentException("duplicate define operator.");
                }
                if(register.expression != null && expression != null){
                    throw new IllegalArgumentException("duplicate define expression.");
                }
            }

            register.operator = operator;
            register.expression = expression;
        }else{
            register.registe(oprs, startIndex + 1, expression, operator, finder, replace);
        }

        nextLetter.add(register);
    }

    @Override
    public IExpression find(String symbol) {
        IExpression expression = this.retrieveRegistryInfo(symbol.toCharArray(), 0);
        if(expression != null && symbol.equals(expression.symbol())){
            return expression;
        }
        return null;
    }

    @Override
    public IExpression retrieveRegistryInfo(char[] chars, int startIndex) {
        if(chars.length <= startIndex){
            return this.expression;
        }
        char ch = chars[startIndex];
        TreeRegister treeRegister = nextLetter.find(new TreeRegister(ch));
        if(treeRegister != null){
            return treeRegister.retrieveRegistryInfo(chars, startIndex + 1);
        }else{
            return this.expression;
        }
    }

    @Override
    public boolean remove(String exp) {
        char[] chars = exp.toCharArray();
        if(chars.length <= 0){
            return false;
        }else{
            TreeRegister register = this.nextLetter.find(new TreeRegister(chars[0]));
            if(register == null){
                return false;
            }else{
                return register.remove(chars, 0);
            }
        }

    }

    @Override
    public void replace(String exp, IExpression expression, IOperator operator) {
        this.registe(exp, expression, operator, true);
    }

    private boolean remove(char[] chars, int startIndex){
        boolean result = false;
        if(chars.length == startIndex + 1){
            boolean hasExists = this.expression != null;
            this.expression = null;
            this.operator = null;
            result = hasExists;
        }else if(chars.length > startIndex){
            TreeRegister register = this.nextLetter.find(new TreeRegister(chars[startIndex + 1]));
            if(register != null){
                if(register.remove(chars, startIndex + 1)){
                    if(register.isEmpty()){
                        this.nextLetter.remove(register);
                    }
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.expression == null && this.nextLetter.isEmpty();
    }

    @Override
    public int compareTo(TreeRegister o) {
        return this.symbol - o.symbol;
    }

    @Override
    public String toString(){
        return String.valueOf(symbol);
    }
}
