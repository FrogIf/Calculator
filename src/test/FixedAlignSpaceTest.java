package test;

import frog.calculator.space.*;

public class FixedAlignSpaceTest {

    public static void main(String[] args){
//        testTwoDimension();
        testThreeDimension();
    }

    private static void testThreeDimension(){
        /*
         * widthInfos = [2, 4, 4]
         * (
         *     (
         *          (1, 2, 3, 4),
         *          (5, 6, 7, 8),
         *          (9, 10, 11, 12),
         *          (13, 14, 15, 16)
         *     ),
         *     (
         *          (17, 18, 19, 20),
         *          (21, 22, 23, 24),
         *          (25, 26, 27, 28),
         *          (29, 30, 31, 32)
         *     )
         * )
         */

//        FixedAlignSpaceBuilder<String> spaceBuilder = new FixedAlignSpaceBuilder<>();
//        spaceBuilder.setDimension(3);
//        spaceBuilder.setWidth(0, 2);
//        spaceBuilder.setWidth(1, 4);
//        spaceBuilder.setWidth(2, 4);
//        ISpace<String> space = spaceBuilder.build();
        ISpace<String> space = new InterleavedSpace<>();


//        space.addPoint(new SymbolPoint("k"), new Coordinate(0));
//        space.addPoint(new SymbolPoint("p"), new Coordinate(0, 0, 0, 0, 0, 1));

        space.addPoint(new SymbolPoint("5"), new Coordinate(0, 1, 0));     // 一会要试下有0, 和没0的
        space.addPoint(new SymbolPoint("1"), new Coordinate(0, 0, 0));

        space.addPoint(new SymbolPoint("2"), new Coordinate(0, 0, 1));
        space.addPoint(new SymbolPoint("3"), new Coordinate(0, 0, 2));
        space.addPoint(new SymbolPoint("4"), new Coordinate(0, 0, 3));

//        space.addPoint(new SymbolPoint("9"), new Coordinate(0, 2, 0));
        space.addPoint(new SymbolPoint("6"), new Coordinate(0, 1, 1));
        space.addPoint(new SymbolPoint("7"), new Coordinate(0, 1, 2));
        space.addPoint(new SymbolPoint("8"), new Coordinate(0, 1, 3));

        space.addPoint(new SymbolPoint("10"), new Coordinate(0, 2, 1));
        space.addPoint(new SymbolPoint("11"), new Coordinate(0, 2, 2));
//        space.addPoint(new SymbolPoint("12"), new Coordinate(0, 2, 3));

        space.addPoint(new SymbolPoint("13"), new Coordinate(0, 3, 0));
        space.addPoint(new SymbolPoint("14"), new Coordinate(0, 3, 1));
//        space.addPoint(new SymbolPoint("15"), new Coordinate(0, 3, 2));
        space.addPoint(new SymbolPoint("16"), new Coordinate(0, 3, 3));

        space.addPoint(new SymbolPoint("17"), new Coordinate(1, 0, 0));
        space.addPoint(new SymbolPoint("18"), new Coordinate(1, 0, 1));
        space.addPoint(new SymbolPoint("19"), new Coordinate(1, 0, 2));
        space.addPoint(new SymbolPoint("20"), new Coordinate(1, 0, 3));

        space.addPoint(new SymbolPoint("21"), new Coordinate(1, 1, 0));
        space.addPoint(new SymbolPoint("22"), new Coordinate(1, 1, 1));
        space.addPoint(new SymbolPoint("23"), new Coordinate(1, 1, 2));
        space.addPoint(new SymbolPoint("24"), new Coordinate(1, 1, 3));

        space.addPoint(new SymbolPoint("25"), new Coordinate(1, 2, 0));
        space.addPoint(new SymbolPoint("26"), new Coordinate(1, 2, 1));
        space.addPoint(new SymbolPoint("27"), new Coordinate(1, 2, 2));
        space.addPoint(new SymbolPoint("28"), new Coordinate(1, 2, 3));

        space.addPoint(new SymbolPoint("29"), new Coordinate(1, 3, 0));
        space.addPoint(new SymbolPoint("30"), new Coordinate(1, 3, 1));
        space.addPoint(new SymbolPoint("31"), new Coordinate(1, 3, 2));
        space.addPoint(new SymbolPoint("32"), new Coordinate(1, 3, 3));

        for(int x = 0; x < space.width(new Coordinate()); x++){
            System.out.println("(");
            for(int y = 0; y < space.width(new Coordinate(0)); y++){
                System.out.print("    (");
                for(int z = 0; z < space.width(new Coordinate(0, 0)); z++){
                    IPoint point = space.getPoint(new Coordinate(x, y, z));
                    if(z != 0){System.out.print(",");}
                    if(point != null) System.out.print(point.intrinsic());
                    else System.out.print("?");
                }
                System.out.println("),");
            }
            System.out.print("),");
        }

        System.out.println();
        TestUtil.showList(space.getPoints());
        System.out.println();

//        ISpace subspace = space.getSubspace(new Coordinate());
        ISpace<String> subspace = space.getSubspace(new Coordinate(0, 3));
//        System.out.println(subspace.dimension());
        subspace.addPoint(new SymbolPoint("15"), new Coordinate(2));
        TestUtil.showList(subspace.getPoints());
    }

    private static ISpace<String> testTwoDimension(){
        FixedAlignSpaceBuilder<String> spaceBuilder = new FixedAlignSpaceBuilder<String>();
        spaceBuilder.setDimension(2);
        spaceBuilder.setWidth(0, 3);
        spaceBuilder.setWidth(1, 4);

        ISpace<String> space = spaceBuilder.build();
        System.out.println(space.dimension());
//        System.out.println(space.width(new Coordinate(0, 0)));
        /*
         * (
         *     (1, 2, 3, 4),
         *     (5, 6, 7, 8),
         *     (9, 10, 11, 12)
         * )
         */
        space.addPoint(new SymbolPoint("1"), new Coordinate(0, 0));
        space.addPoint(new SymbolPoint("2"), new Coordinate(0, 1));
        space.addPoint(new SymbolPoint("3"), new Coordinate(0, 2));
        space.addPoint(new SymbolPoint("4"), new Coordinate(0, 3));


        space.addPoint(new SymbolPoint("5"), new Coordinate(1, 0));
        space.addPoint(new SymbolPoint("6"), new Coordinate(1, 1));
        space.addPoint(new SymbolPoint("7"), new Coordinate(1, 2));
        space.addPoint(new SymbolPoint("8"), new Coordinate(1, 3));

        space.addPoint(new SymbolPoint("9"), new Coordinate(2, 0));
        space.addPoint(new SymbolPoint("10"), new Coordinate(2, 1));
        space.addPoint(new SymbolPoint("12"), new Coordinate(2, 3));
        space.addPoint(new SymbolPoint("11"), new Coordinate(2, 2));

        TestUtil.showList(space.getPoints());

        System.out.println();
        System.out.println(space.getPoint(new Coordinate(0, 0)));
        System.out.println(space.getPoint(new Coordinate(1, 1)));
        System.out.println(space.getPoint(new Coordinate(2, 2)));
        System.out.println(space.getPoint(new Coordinate(1)));
        System.out.println(space.getPoint(new Coordinate(1, 0)));
        System.out.println(space.getPoint(new Coordinate(2, 0)));

        System.out.println("--- width ---");
        System.out.println(space.width(new Coordinate(1)));
        System.out.println(space.width(new Coordinate(1, 1)));

        for(int i = 0; i < space.width(new Coordinate()); i++){
            for(int j = 0; j < space.width(new Coordinate(0)); j++){
                IPoint point = space.getPoint(new Coordinate(i, j));
                System.out.print(" " + point.intrinsic());
            }
            System.out.println();
        }

        System.out.println("------ subspace --------");
        ISpace subspace = space.getSubspace(new Coordinate(2, 1));
        System.out.println(subspace.dimension());
        TestUtil.showList(subspace.getPoints());

        System.out.println();
        space.addPoint(new SymbolPoint("uuu"), new Coordinate(1, 2, 3));
        System.out.println(space.dimension());
        System.out.println(space.getPoint(new Coordinate(1, 2, 3)));

        return space;
    }

}
