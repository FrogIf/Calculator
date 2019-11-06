package frog.calculator.express;

import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

public class DefaultExpressionContext implements IExpressionContext {

    private IList<IBuildFinishListener> buildOverListeners = new LinkedList<>();

    private IExpression root;

    @Override
    public void setRoot(IExpression root) {
        this.root = root;
    }

    @Override
    public IExpression getRoot() {
        return root;
    }

    @Override
    public void finishBuild() {
        if(!buildOverListeners.isEmpty()){
            Iterator<IBuildFinishListener> iterator = buildOverListeners.iterator();
            while(iterator.hasNext()){
                iterator.next().buildFinishCallBack(this);
            }
        }
    }

    @Override
    public void addBuildFinishListener(IBuildFinishListener listener) {
        buildOverListeners.add(listener);
    }
}
