package io.github.frogif.calculator.compile.lexical.fetcher;

import io.github.frogif.calculator.util.collection.AVLTreeSet;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.util.collection.ISet;
import io.github.frogif.calculator.util.collection.Iterator;
import io.github.frogif.calculator.compile.lexical.IScanner;
import io.github.frogif.calculator.compile.lexical.exception.DuplicateTokenException;
import io.github.frogif.calculator.compile.syntax.ISyntaxNodeGenerator;
import io.github.frogif.calculator.util.ComparableComparator;
import io.github.frogif.calculator.util.collection.LinkedList;

/**
 * word存储仓库
 */
public class WordRepository implements Comparable<WordRepository> {

    private char symbol = 0;

    private WordObj word;

    private final ISet<WordRepository> nextLetter = new AVLTreeSet<>(ComparableComparator.<WordRepository>getInstance());

    public WordRepository(){
        // do nothing.
    }

    public WordRepository(char symbol){
        this.symbol = symbol;
    }

    public void insert(WordObj word) throws DuplicateTokenException {
        insert(word.word(), 0, word, new WordRepository(), false);
    }

    private void insert(String word, int startIndex, WordObj wordObj, WordRepository finder, boolean replace) throws DuplicateTokenException {
        char ch = word.charAt(startIndex);

        finder.symbol = ch;

        WordRepository register = nextLetter.find(finder);

        if(register == null){
            register = new WordRepository();
            register.symbol = ch;
        }

        if(startIndex == word.length() - 1){
            if(!replace && register.word != null && wordObj != null){
                throw new DuplicateTokenException(wordObj.word());
            }

            register.word = wordObj;
        }else{
            register.insert(word, startIndex + 1, wordObj, finder, replace);
        }

        nextLetter.add(register);
    }

    public boolean remove(String word) {
        if(word.length() == 0){
            return false;
        }else{
            WordRepository register = this.nextLetter.find(new WordRepository(word.charAt(0)));
            if(register == null){
                return false;
            }else{
                return register.remove(word, 0);
            }
        }
    }

    private boolean remove(String word, int startIndex){
        boolean result = false;
        if(word.length() == startIndex + 1){
            boolean hasExists = this.word != null;
            this.word = null;
            result = hasExists;
        }else if(word.length() > startIndex){
            WordRepository register = this.nextLetter.find(new WordRepository(word.charAt(startIndex + 1)));
            if(register != null  && register.remove(word, startIndex + 1)){
                if(register.isEmpty()){
                    this.nextLetter.remove(register);
                }
                result = true;
            }
        }
        return result;
    }

    public void replace(String symbol, WordObj word) {
        try {
            this.insert(symbol, 0, word, new WordRepository(), true);
        } catch (DuplicateTokenException e) {
            // do nothing, the DuplicateTokenException will not be trigger.
        }
    }

    public WordObj retrieve(IScanner scanner) {
        char ch = scanner.peek();
        WordRepository fr = new WordRepository(ch);
        WordRepository treeRegister = nextLetter.find(fr);
        if(treeRegister != null){
            scanner.take();
            if(scanner.isNotEnd()){
                return treeRegister.retrieveNext(scanner, fr);
            }else{
                return treeRegister.word;
            }
        }else{
            return this.word;
        }
    }

    private WordObj retrieveNext(IScanner scanner, WordRepository fr){
        fr.symbol = scanner.peek();
        WordRepository treeRegister = nextLetter.find(fr);
        if(treeRegister != null){
            scanner.take();
            if(scanner.isNotEnd()){
                return treeRegister.retrieveNext(scanner, fr);
            }else{
                return treeRegister.word;
            }
        }else{
            return this.word;
        }
    }

    public WordObj retrieve(String word) {
        int i = 0;
        WordRepository fr = new WordRepository();
        WordRepository base = this;
        WordRepository tr = null;
        for(int len = word.length(); i < len; i++){
            fr.symbol = word.charAt(i);
            tr = base.nextLetter.find(fr);
            if(tr == null){
                break;
            }else{
                base = tr;
            }
        }
        return tr == null ? null : tr.word;
    }

    public boolean isEmpty() {
        return this.word == null && this.nextLetter.isEmpty();
    }

    IList<WordObj> getWords() {
        IList<WordObj> result = new LinkedList<>();

        IList<ISet<WordRepository>> cursorBranches = new LinkedList<>();
        IList<ISet<WordRepository>> tempCursorBranches = new LinkedList<>();
        if(!this.nextLetter.isEmpty()) { cursorBranches.add(this.nextLetter); }
        while(!cursorBranches.isEmpty()){
            tempCursorBranches.clear();
            Iterator<ISet<WordRepository>> iterator = cursorBranches.iterator();
            while(iterator.hasNext()){
                ISet<WordRepository> nextSet = iterator.next();
                Iterator<WordRepository> symbolItr = nextSet.iterator();
                while (symbolItr.hasNext()){
                    WordRepository next = symbolItr.next();
                    if(next.word != null){
                        result.add(next.word);
                    }
                    if(!next.nextLetter.isEmpty()){
                        tempCursorBranches.add(next.nextLetter);
                    }
                }
            }
            IList<ISet<WordRepository>> t = tempCursorBranches;
            tempCursorBranches = cursorBranches;
            cursorBranches = t;
        }
        return result;
    }

    @Override
    public int compareTo(WordRepository o) {
        return this.symbol - o.symbol;
    }

    @Override
    public String toString(){
        return String.valueOf(symbol);
    }
    
}


class WordObj {
    private final String word;
    private final ISyntaxNodeGenerator generator;

    public WordObj(String word, ISyntaxNodeGenerator generator) {
        this.word = word;
        this.generator = generator;
    }

    public String word() {
        return word;
    }

    public ISyntaxNodeGenerator generator() {
        return generator;
    }
}