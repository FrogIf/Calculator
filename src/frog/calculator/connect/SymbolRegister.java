package frog.calculator.connect;

import frog.calculator.compile.IWord;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.util.ComparableComparator;
import frog.calculator.util.collection.*;

public class SymbolRegister<W extends IWord> implements IRegister<W>, Comparable<SymbolRegister<W>>{

    private char symbol = 0;

    private W expression;

    private final ISet<SymbolRegister<W>> nextLetter = new AVLTreeSet<>(ComparableComparator.<SymbolRegister<W>>getInstance());

    public SymbolRegister(){ }

    private SymbolRegister(char symbol){
        this.symbol = symbol;
    }

    @Override
    public void insert(W word) throws DuplicateSymbolException {
        if(word != null){
            insert(word.word().toCharArray(), 0, word, new SymbolRegister<>(), false);
        }
    }

    @Override
    public void replace(String exp, W word) {
        try {
            this.insert(exp.toCharArray(), 0, word, new SymbolRegister<>(), true);
        } catch (DuplicateSymbolException e) {
            // do nothing, the DuplicateSymbolException will not be trigger.
        }
    }

    private void insert(char[] expChars, int startIndex, W word, SymbolRegister<W> finder, boolean replace) throws DuplicateSymbolException {
        char ch = expChars[startIndex];

        finder.symbol = ch;

        SymbolRegister<W> register = nextLetter.find(finder);

        if(register == null){
            register = new SymbolRegister<>();
            register.symbol = ch;
        }

        if(startIndex == expChars.length - 1){
            if(!replace && register.expression != null && expression != null){
                throw new DuplicateSymbolException("duplicate define expression : " + word.word());
            }

            register.expression = expression;
        }else{
            register.insert(expChars, startIndex + 1, expression, finder, replace);
        }

        nextLetter.add(register);
    }

    @Override
    public W find(String symbol) {
        W exp = this.retrieve(symbol.toCharArray(), 0);
        if(exp != null && symbol.equals(exp.word())){
            return expression;
        }
        return null;
    }

    @Override
    public W retrieve(char[] chars, int startIndex) {
        if(chars.length <= startIndex){
            return this.expression;
        }
        char ch = chars[startIndex];
        SymbolRegister<W> treeRegister = nextLetter.find(new SymbolRegister<>(ch));
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
            SymbolRegister<W> register = this.nextLetter.find(new SymbolRegister<>(chars[0]));
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
            SymbolRegister<W> register = this.nextLetter.find(new SymbolRegister<>(chars[startIndex + 1]));
            if(register != null  && register.remove(chars, startIndex + 1)){
                if(register.isEmpty()){
                    this.nextLetter.remove(register);
                }
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.expression == null && this.nextLetter.isEmpty();
    }

    @Override
    public IList<W> getElements() {
        IList<W> result = new LinkedList<>();

        IList<ISet<SymbolRegister<W>>> cursorBranches = new LinkedList<>();
        IList<ISet<SymbolRegister<W>>> tempCursorBranches = new LinkedList<>();
        if(!this.nextLetter.isEmpty()) { cursorBranches.add(this.nextLetter); }
        while(!cursorBranches.isEmpty()){
            tempCursorBranches.clear();
            Iterator<ISet<SymbolRegister<W>>> iterator = cursorBranches.iterator();
            while(iterator.hasNext()){
                ISet<SymbolRegister<W>> nextSet = iterator.next();
                Iterator<SymbolRegister<W>> symbolItr = nextSet.iterator();
                while (symbolItr.hasNext()){
                    SymbolRegister<W> next = symbolItr.next();
                    if(next.expression != null){
                        result.add(next.expression);
                    }
                    if(!next.nextLetter.isEmpty()){
                        tempCursorBranches.add(next.nextLetter);
                    }
                }
            }
            IList<ISet<SymbolRegister<W>>> t = tempCursorBranches;
            tempCursorBranches = cursorBranches;
            cursorBranches = t;

        }
        return result;
    }

    @Override
    public int compareTo(SymbolRegister<W> o) {
        return this.symbol - o.symbol;
    }

    @Override
    public String toString(){
        return String.valueOf(symbol);
    }
}
