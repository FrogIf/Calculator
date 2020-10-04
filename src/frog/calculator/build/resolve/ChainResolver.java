package frog.calculator.build.resolve;

public class ChainResolver extends AbstractResolver {

    private final ChainNode head = new ChainNode();

    private ChainNode tail = head;

    public void addResolver(IResolver resolver) {
        tail.next = new ChainNode();
        tail = tail.next;
        tail.resolver = resolver;
    }

    @Override
    public IResolveResult resolve(char[] chars, int startIndex) {
        ChainNode viewer = head;

        while(viewer.next != null){
            viewer = viewer.next;
            IResolveResult result = viewer.resolver.resolve(chars, startIndex);
            if(result.success()){
                return result;
            }
        }

        return EMPTY_RESULT;
    }

    private static class ChainNode {
        private IResolver resolver;
        private ChainNode next;
    }

}
