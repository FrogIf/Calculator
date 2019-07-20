package frog.calculator.resolver.resolve;

import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.util.NumberUtil;

/**
 * 变量解析器
 */
public class VariableResolver extends AbstractResolver {

    private char[] splitor;

    private char[] close;

    public VariableResolver(IResolverResultFactory resolverResultFactory, String splitor, String close) {
        super(resolverResultFactory);
        if(splitor == null || close == null){
            throw new IllegalArgumentException("splitor or close is undefined.");
        }
        this.close = close.toCharArray();
        this.splitor = splitor.toCharArray();
    }

    @Override
    protected void resolve(char[] chars, int startIndex, IResolverResult resolveResult) {
        char ch = chars[startIndex];
        if(NumberUtil.isNumber(ch)){
            return;
        }else{

        }
    }
}
