package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.DuplicateTokenException;
import sch.frog.calculator.util.ComparableComparator;
import sch.frog.calculator.util.collection.AVLTreeSet;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.ISet;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.LinkedList;

/**
 * token存储仓库
 */
public class TokenRepository implements ITokenRepository, Comparable<TokenRepository> {

    private char symbol = 0;

    private IToken token;

    private final ISet<TokenRepository> nextLetter = new AVLTreeSet<>(ComparableComparator.<TokenRepository>getInstance());

    public TokenRepository(){
        // do nothing.
    }

    public TokenRepository(char symbol){
        this.symbol = symbol;
    }

    @Override
    public void insert(IToken token) throws DuplicateTokenException {
        insert(token.word().toCharArray(), 0, token, new TokenRepository(), false);
    }

    private void insert(char[] expChars, int startIndex, IToken token, TokenRepository finder, boolean replace) throws DuplicateTokenException {
        char ch = expChars[startIndex];

        finder.symbol = ch;

        TokenRepository register = nextLetter.find(finder);

        if(register == null){
            register = new TokenRepository();
            register.symbol = ch;
        }

        if(startIndex == expChars.length - 1){
            if(!replace && register.token != null && token != null){
                throw new DuplicateTokenException(token.word());
            }

            register.token = token;
        }else{
            register.insert(expChars, startIndex + 1, token, finder, replace);
        }

        nextLetter.add(register);
    }

    @Override
    public boolean remove(String word) {
        char[] chars = word.toCharArray();
        if(chars.length <= 0){
            return false;
        }else{
            TokenRepository register = this.nextLetter.find(new TokenRepository(chars[0]));
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
            boolean hasExists = this.token != null;
            this.token = null;
            result = hasExists;
        }else if(chars.length > startIndex){
            TokenRepository register = this.nextLetter.find(new TokenRepository(chars[startIndex + 1]));
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
    public void replace(String symbol, IToken token) {
        try {
            this.insert(symbol.toCharArray(), 0, token, new TokenRepository(), true);
        } catch (DuplicateTokenException e) {
            // do nothing, the DuplicateTokenException will not be trigger.
        }
    }

    @Override
    public IToken retrieve(IScanner scanner) {
        char ch = scanner.peek();
        TokenRepository fr = new TokenRepository(ch);
        TokenRepository treeRegister = nextLetter.find(fr);
        if(treeRegister != null){
            scanner.take();
            if(scanner.isNotEnd()){
                return treeRegister.retrieveNext(scanner, fr);
            }else{
                return treeRegister.token;
            }
        }else{
            return this.token;
        }
    }

    private IToken retrieveNext(IScanner scanner, TokenRepository fr){
        fr.symbol = scanner.peek();
        TokenRepository treeRegister = nextLetter.find(fr);
        if(treeRegister != null){
            scanner.take();
            if(scanner.isNotEnd()){
                return treeRegister.retrieveNext(scanner, fr);
            }else{
                return treeRegister.token;
            }
        }else{
            return this.token;
        }
    }

    @Override
    public IToken retrieve(String word) {
        char[] chars = word.toCharArray();
        int i = 0;
        TokenRepository fr = new TokenRepository();
        TokenRepository base = this;
        TokenRepository tr = null;
        for(; i < chars.length; i++){
            fr.symbol = chars[i];
            tr = base.nextLetter.find(fr);
            if(tr == null){
                break;
            }else{
                base = tr;
            }
        }
        return tr == null ? null : tr.token;
    }

    @Override
    public boolean isEmpty() {
        return this.token == null && this.nextLetter.isEmpty();
    }

    @Override
    public IList<IToken> getTokens() {
        IList<IToken> result = new LinkedList<>();

        IList<ISet<TokenRepository>> cursorBranches = new LinkedList<>();
        IList<ISet<TokenRepository>> tempCursorBranches = new LinkedList<>();
        if(!this.nextLetter.isEmpty()) { cursorBranches.add(this.nextLetter); }
        while(!cursorBranches.isEmpty()){
            tempCursorBranches.clear();
            Iterator<ISet<TokenRepository>> iterator = cursorBranches.iterator();
            while(iterator.hasNext()){
                ISet<TokenRepository> nextSet = iterator.next();
                Iterator<TokenRepository> symbolItr = nextSet.iterator();
                while (symbolItr.hasNext()){
                    TokenRepository next = symbolItr.next();
                    if(next.token != null){
                        result.add(next.token);
                    }
                    if(!next.nextLetter.isEmpty()){
                        tempCursorBranches.add(next.nextLetter);
                    }
                }
            }
            IList<ISet<TokenRepository>> t = tempCursorBranches;
            tempCursorBranches = cursorBranches;
            cursorBranches = t;
        }
        return result;
    }

    @Override
    public int compareTo(TokenRepository o) {
        return this.symbol - o.symbol;
    }

    @Override
    public String toString(){
        return String.valueOf(symbol);
    }
    
}
