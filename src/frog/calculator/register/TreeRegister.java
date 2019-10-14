package frog.calculator.register;

import frog.calculator.express.IExpression;
import frog.calculator.util.ComparableComparator;
import frog.calculator.util.collection.TreeSet;

public class TreeRegister implements IRegister, Comparable<TreeRegister>{

    private char symbol = 0;

    private IExpression expression;

    private TreeSet<TreeRegister> nextLetter = new TreeSet<>(ComparableComparator.<TreeRegister>getInstance());

    public TreeRegister(){ }

    private TreeRegister(char symbol){
        this.symbol = symbol;
    }

    @Override
    public void insert(IExpression expression) {
        if(expression != null){
            insert(expression.symbol().toCharArray(), 0, expression, new TreeRegister(), false);
        }
    }

    @Override
    public void replace(String exp, IExpression expression) {
        this.insert(exp.toCharArray(), 0, expression, new TreeRegister(), true);
    }

    private void insert(char[] expChars, int startIndex, IExpression expression, TreeRegister finder, boolean replace){
        char ch = expChars[startIndex];

        finder.symbol = ch;

        TreeRegister register = nextLetter.find(finder);

        if(register == null){
            register = new TreeRegister();
            register.symbol = ch;
        }

        if(startIndex == expChars.length - 1){
            if(!replace){
                if(register.expression != null && expression != null){
                    throw new IllegalArgumentException("duplicate define expression.");
                }
            }

            register.expression = expression;
        }else{
            register.insert(expChars, startIndex + 1, expression, finder, replace);
        }

        nextLetter.add(register);
    }

    @Override
    public IExpression find(String symbol) {
        IExpression expression = this.retrieve(symbol.toCharArray(), 0);
        if(expression != null && symbol.equals(expression.symbol())){
            return expression;
        }
        return null;
    }

    @Override
    public IExpression retrieve(char[] chars, int startIndex) {
        if(chars.length <= startIndex){
            return this.expression;
        }
        char ch = chars[startIndex];
        TreeRegister treeRegister = nextLetter.find(new TreeRegister(ch));
        if(treeRegister != null){
            return treeRegister.retrieve(chars, startIndex + 1);
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

    private boolean remove(char[] chars, int startIndex){
        boolean result = false;
        if(chars.length == startIndex + 1){
            boolean hasExists = this.expression != null;
            this.expression = null;
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
