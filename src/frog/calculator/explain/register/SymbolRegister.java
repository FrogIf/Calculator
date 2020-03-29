package frog.calculator.explain.register;

import frog.calculator.ISymbol;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.util.ComparableComparator;
import frog.calculator.util.collection.*;

public class SymbolRegister<T extends ISymbol> implements IRegister<T>, Comparable<SymbolRegister<T>>{

    private char symbol = 0;

    private T expression;

    private ISet<SymbolRegister<T>> nextLetter = new AVLTreeSet<>(ComparableComparator.<SymbolRegister<T>>getInstance());

    public SymbolRegister(){ }

    private SymbolRegister(char symbol){
        this.symbol = symbol;
    }

    @Override
    public void insert(T expression) throws DuplicateSymbolException {
        if(expression != null){
            insert(expression.symbol().toCharArray(), 0, expression, new SymbolRegister<>(), false);
        }
    }

    @Override
    public void replace(String exp, T expression) {
        try {
            this.insert(exp.toCharArray(), 0, expression, new SymbolRegister<>(), true);
        } catch (DuplicateSymbolException e) {
            // do nothing, the DuplicateSymbolException will not be trigger.
        }
    }

    private void insert(char[] expChars, int startIndex, T expression, SymbolRegister<T> finder, boolean replace) throws DuplicateSymbolException {
        char ch = expChars[startIndex];

        finder.symbol = ch;

        SymbolRegister<T> register = nextLetter.find(finder);

        if(register == null){
            register = new SymbolRegister<>();
            register.symbol = ch;
        }

        if(startIndex == expChars.length - 1){
            if(!replace){
                if(register.expression != null && expression != null){
                    throw new DuplicateSymbolException("duplicate define expression : " + expression.symbol());
                }
            }

            register.expression = expression;
        }else{
            register.insert(expChars, startIndex + 1, expression, finder, replace);
        }

        nextLetter.add(register);
    }

    @Override
    public T find(String symbol) {
        T expression = this.retrieve(symbol.toCharArray(), 0);
        if(expression != null && symbol.equals(expression.symbol())){
            return expression;
        }
        return null;
    }

    @Override
    public T retrieve(char[] chars, int startIndex) {
        if(chars.length <= startIndex){
            return this.expression;
        }
        char ch = chars[startIndex];
        SymbolRegister<T> treeRegister = nextLetter.find(new SymbolRegister<>(ch));
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
            SymbolRegister<T> register = this.nextLetter.find(new SymbolRegister<>(chars[0]));
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
            SymbolRegister<T> register = this.nextLetter.find(new SymbolRegister<>(chars[startIndex + 1]));
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
    public IList<T> getElements() {
        IList<T> result = new LinkedList<>();

        IList<ISet<SymbolRegister<T>>> cursorBranches = new LinkedList<>();
        IList<ISet<SymbolRegister<T>>> tempCursorBranches = new LinkedList<>();
        if(!this.nextLetter.isEmpty()) { cursorBranches.add(this.nextLetter); }
        while(!cursorBranches.isEmpty()){
            tempCursorBranches.clear();
            Iterator<ISet<SymbolRegister<T>>> iterator = cursorBranches.iterator();
            while(iterator.hasNext()){
                ISet<SymbolRegister<T>> nextSet = iterator.next();
                Iterator<SymbolRegister<T>> symbolItr = nextSet.iterator();
                while (symbolItr.hasNext()){
                    SymbolRegister<T> next = symbolItr.next();
                    if(next.expression != null){
                        result.add(next.expression);
                    }
                    if(!next.nextLetter.isEmpty()){
                        tempCursorBranches.add(next.nextLetter);
                    }
                }
            }
            IList<ISet<SymbolRegister<T>>> t = tempCursorBranches;
            tempCursorBranches = cursorBranches;
            cursorBranches = t;

        }
        return result;
    }

    @Override
    public int compareTo(SymbolRegister o) {
        return this.symbol - o.symbol;
    }

    @Override
    public String toString(){
        return String.valueOf(symbol);
    }
}
