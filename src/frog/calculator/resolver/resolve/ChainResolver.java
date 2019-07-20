package frog.calculator.resolver.resolve;

import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;

public class ChainResolver implements IResolver{

    private ChainNode head = new ChainNode();

    private ChainNode tail = head;

    public void addResolver(IResolver resolver) {
        tail.next = new ChainNode();
        tail = tail.next;
        tail.resolver = resolver;
    }

    @Override
    public IResolverResult resolve(char[] chars, int startIndex) {
        ChainNode viewer = head;

        while(viewer.next != null){
            viewer = viewer.next;
            IResolverResult result = viewer.resolver.resolve(chars, startIndex);
            if(result.getExpression() != null){
                return result;
            }
        }

        return null;
    }

    private static class ChainNode{
        private IResolver resolver;
        private ChainNode next;
    }

}
