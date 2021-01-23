package frog.calculator.compile;

public abstract class AbstractBuildListener implements IBuildListener {

    @Override
    public boolean beforeNextNode() {
        // do nothing
        return false;
    }

    @Override
    public void onBuildFinish() {
        // do nothing
    }
    
}
