package frog.calculator.build;

import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;

public class BuildListenerManager {

    private final IList<IBuildFinishListener> buildFinishListenerList = new LinkedList<>();

}
