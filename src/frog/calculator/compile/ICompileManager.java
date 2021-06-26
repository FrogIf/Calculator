package frog.calculator.compile;

import frog.calculator.compile.lexical.fetcher.ITokenFetcher;
import frog.calculator.util.collection.IList;

public interface ICompileManager {

    IList<ITokenFetcher> getTokenFetchers();
    
}
