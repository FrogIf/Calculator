package test;

import frog.calculator.space.*;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class TestSpace {

    public static void main(String[] args){
        testThree();
    }

    private static void testThree(){
        CommonSpaceBuilder builder = new CommonSpaceBuilder();

        builder.setDimension(3);

        builder.setWidth(0, 2);
        builder.setWidth(1, 4);
        builder.setWidth(2, 4);

        /*
         * (
         *     (
         *         (1, 2, 3, 4),
         *         (5, 6, 7, 8),
         *         (9, 10, 11, 12),
         *         (13, 14, 15, 16)
         *     ),
         *     (
         *         (17, 18, 19, 20),
         *         (21, 22, 23, 24),
         *         (25, 26, 27, 28),
         *         (29, 30, 31, 32)
         *     )
         * )
         *
         */

        ISpace s = builder.build();

        s.addValue(new CommonCoordinate(0, 0, 0), new CommonLiteral("1"));
        s.addValue(new CommonCoordinate(0, 1, 1), new CommonLiteral("6"));
        s.addValue(new CommonCoordinate(0, 2, 2), new CommonLiteral("11"));
        s.addValue(new CommonCoordinate(0, 3, 3), new CommonLiteral("16"));
        s.addValue(new CommonCoordinate(1, 0, 0), new CommonLiteral("17"));
        s.addValue(new CommonCoordinate(1, 1, 1), new CommonLiteral("22"));
        s.addValue(new CommonCoordinate(1, 2, 2), new CommonLiteral("27"));
        s.addValue(new CommonCoordinate(1, 3, 3), new CommonLiteral("32"));

        System.out.println(s.getValue(new CommonCoordinate(0, 1, 1)));

        showListChildren(s.getValues());
    }

    private static void testZero(){
        CommonSpaceBuilder builder = new CommonSpaceBuilder();
        builder.setDimension(0);

        ISpace space = builder.build();
        space.addValue(new CommonCoordinate(0), new CommonLiteral("1"));

        IList<ILiteral> values = space.getValues();
        showListChildren(values);
    }

    private static void testOne(){
        CommonSpaceBuilder builder = new CommonSpaceBuilder();
        builder.setDimension(1);
        builder.setWidth(0, 1);

        ISpace space = builder.build();
        space.addValue(new CommonCoordinate(0), new CommonLiteral("1"));
        space.addValue(new CommonCoordinate(1), new CommonLiteral("2"));
        space.addValue(new CommonCoordinate(2), new CommonLiteral("3"));
        space.addValue(new CommonCoordinate(3), new CommonLiteral("4"));
        space.addValue(new CommonCoordinate(4), new CommonLiteral("5"));

        IList<ILiteral> values = space.getValues();
        showListChildren(values);
    }

    private static void testTwo(){
        CommonSpaceBuilder builder = new CommonSpaceBuilder();
        builder.setDimension(2);
        builder.setWidth(0, 4);
        builder.setWidth(1, 4);

        ISpace s = builder.build();

        /**
         * (
         *      (1, 2, 3, 4),
         *      (5, 6, 7, 8),
         *      (9, 10, 11, 12),
         *      (13, 14, 15, 16)
         * )
         */

        s.addValue(new CommonCoordinate(0, 0), new CommonLiteral("1"));
        s.addValue(new CommonCoordinate(1, 1), new CommonLiteral("6"));
        s.addValue(new CommonCoordinate(2, 2), new CommonLiteral("11"));
        s.addValue(new CommonCoordinate(3, 3), new CommonLiteral("16"));

        s.addValue(new CommonCoordinate(0, 1), new CommonLiteral("2"));
        s.addValue(new CommonCoordinate(0, 2), new CommonLiteral("3"));
        s.addValue(new CommonCoordinate(0, 3), new CommonLiteral("4"));

        s.addValue(new CommonCoordinate(1, 0), new CommonLiteral("5"));
        s.addValue(new CommonCoordinate(1, 2), new CommonLiteral("7"));
        s.addValue(new CommonCoordinate(1, 3), new CommonLiteral("8"));

        s.addValue(new CommonCoordinate(2, 0), new CommonLiteral("9"));
        s.addValue(new CommonCoordinate(2, 1), new CommonLiteral("10"));
        s.addValue(new CommonCoordinate(2, 3), new CommonLiteral("12"));

        s.addValue(new CommonCoordinate(3, 0), new CommonLiteral("13"));
        s.addValue(new CommonCoordinate(3, 1), new CommonLiteral("14"));
        s.addValue(new CommonCoordinate(3, 2), new CommonLiteral("15"));

        IList<ILiteral> values = s.getValues();

        showListChildren(values);
    }

    private static <E> void showListChildren(IList<E> list){
        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }


}
