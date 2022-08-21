package sch.frog.calculator.compile.lexical.fetcher;

import sch.frog.calculator.compile.lexical.GeneralToken;
import sch.frog.calculator.compile.lexical.IScanner;
import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.lexical.matcher.IMatcher;
import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import sch.frog.calculator.util.StringUtils;
import sch.frog.calculator.util.collection.IMap;
import sch.frog.calculator.util.collection.ISet;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.TreeMap;

/**
 * 通过指定的匹配策略, 获取token
 */
public class MatcherTokenFetcher implements ITokenFetcher {

    public final TreeMap<Integer, ISyntaxNodeGenerator> syntaxNodeGeneratorMap = new TreeMap<>((a, b) -> {
        if(a > b){ return 1;}
        else if(a.equals(b)){ return 0; }
        else { return -1; }
    });

    public final TreeMap<Integer, IMatcher> matcherMap = new TreeMap<>((a, b) -> {
        if(a > b){ return 1;}
        else if(a.equals(b)){ return 0; }
        else { return -1; }
    });

    /**
     * 内部使用的一个索引, 用于将matcher与generator进行一一映射
     */
    private int index = 0;

    /**
     * 注册匹配器以及语法节点生成器
     * @param matcher 匹配器
     * @param generator 节点生成器
     */
    public void register(IMatcher matcher, ISyntaxNodeGenerator generator){
        syntaxNodeGeneratorMap.put(index, generator);
        matcherMap.put(index, matcher);
        index++;
    }

    @Override
    public IToken fetch(IScanner scanner) {
        ISet<IMap.Entry<Integer, IMatcher>> entryISet = matcherMap.entrySet();
        Iterator<IMap.Entry<Integer, IMatcher>> iterator = entryISet.iterator();
        IScanner.PointerSnapshot initSnapshot = scanner.snapshot();
        IScanner.PointerSnapshot matchSnapshot = null;
        int len = 0;
        IToken result = null;
        while(iterator.hasNext()){
            IMap.Entry<Integer, IMatcher> entry = iterator.next();
            scanner.applySnapshot(initSnapshot);
            String match = entry.getValue().match(scanner);
            if(StringUtils.isNotBlank(match) && match.length() > len){
                result = new GeneralToken(match, syntaxNodeGeneratorMap.get(entry.getKey()), scanner.position());
                matchSnapshot = scanner.snapshot();
                len = match.length();
            }
        }
        if(result == null){
            scanner.applySnapshot(initSnapshot);
        }else{
            scanner.applySnapshot(matchSnapshot);
        }
        return result;
    }
}
