package sch.frog.test;

import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;

import sch.frog.test.impl.*;

public class TestPlatform {
    
    private static IList<AbstractTestContent<?>> testContents = new ArrayList<>();

    private static final int TEST_COUNT = 1000;

    static {
        testContents.add(new IntegerScientificNotationTest());
        testContents.add(new GcdTest());
        testContents.add(new TreeMapTest());
        testContents.add(new RoundOffTest());
    }

    public static void main(String[] args){
        Iterator<AbstractTestContent<?>> testItr = testContents.iterator();
        while(testItr.hasNext()){
            AbstractTestContent<?> next = testItr.next();
            if(!next.repeatTest(TEST_COUNT)){
                break;
            }
        }
    }
}
