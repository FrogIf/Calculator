package sch.frog.test;

import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.test.impl.GcdTest;
import sch.frog.test.impl.IntegerScientificNotationTest;
import sch.frog.test.impl.RBTreeTest;
import sch.frog.test.impl.RationalNumberTest;
import sch.frog.test.impl.RoundOffTest;
import sch.frog.test.impl.TreeMapTest;

public class TestPlatform {
    
    private static IList<AbstractTestContent<?>> testContents = new ArrayList<>();

    private static final int TEST_COUNT = 1000;

    static {
        testContents.add(new IntegerScientificNotationTest());
        testContents.add(new GcdTest());
        testContents.add(new TreeMapTest());
        testContents.add(new RoundOffTest());
        testContents.add(new RBTreeTest());
        testContents.add(new RationalNumberTest());
    }

    public static void main(String[] args){
        Iterator<AbstractTestContent<?>> testItr = testContents.iterator();
        while(testItr.hasNext()){
            AbstractTestContent<?> next = testItr.next();
            if(!next.repeatTest(TEST_COUNT)){
                break;
            }
            next.simpleTest();
        }
    }
}
