package frog.calculator.space;

import frog.calculator.util.ComparableComparator;
import frog.calculator.util.collection.*;

public final class InterleavedSpace<T> implements ISpace<T> {

    private ISet<XSpace<T>> subspaces = new TreeSet<>(ComparableComparator.<XSpace<T>>getInstance());

    private ISet<IPoint<T>> points = new TreeSet<>(PointComparator.getInstance());

    private int dimension;

    /**
     * 特点:
     *      1. 对齐存放
     *            如果两个点的坐标只有最后一位不同, 那么他们一定存放在同一个points中
     *      2. 最小高度存放
     *            如果一个点的坐标最后若干为都是0, 那么会忽略所有0, 保证整棵树的高度最低
     * ("对齐存放"的优先级高于"最小高度存放", 也就是说优先满足对齐存放, 如果没有需要满足的"对齐", 则需要满足"最小高度")
     * @param point
     * @param coordinate
     */
    @Override
    public void addPoint(IPoint<T> point, ICoordinate coordinate) {
        if(point == null || coordinate == null){
            throw new IllegalArgumentException("point info is null.");
        }

        if(coordinate.dimension() > this.dimension){
            this.dimension = coordinate.dimension();
        }

        Itraveller<Integer> traveller = coordinate.traveller();

        int pos = traveller.hasNext() ? traveller.next() : 0;
        int di = 1;

        XSpace<T> sf = new XSpace<>(pos);
        XPoint<T> pf = new XPoint<>(pos);
        InterleavedSpace<T> pointSpace = this;
        ISet<IPoint<T>> axialPoints = pointSpace.points;

        IPoint<T> movePoint;
        int pp = -1;    // pre pos
        InterleavedSpace<T> prePointSpace;


        while(traveller.hasNext()){
            pf.axialValue = pos;
            movePoint = pointSpace.points.find(pf);

            // 为待添加的点寻找/开辟空间
            sf.index = pos;
            XSpace<T> xSpace = pointSpace.subspaces.find(sf);
            if(xSpace == null){
                InterleavedSpace<T> subSpace = new InterleavedSpace<>();
                subSpace.dimension = coordinate.dimension() - di;
                xSpace = new XSpace<>(pos, subSpace);
                pointSpace.subspaces.add(xSpace);
            }

            ISpace<T> subspace = xSpace.space;

            if(subspace instanceof InterleavedSpace) {
                prePointSpace = pointSpace;
                pointSpace = ((InterleavedSpace<T>)subspace);   // 以该子空间为基准, 准备下一轮寻址
            }else{  // 如果该空间已经变成了别的类型的空间, 调用该空间的addPoint方法
                // TODO 移入movePoint
                pointSpace.addPoint(point, new ContinueCoordinate(traveller, coordinate.dimension() - di));
                return;
            }

            if(pos != 0){   // 现在不等于0, 才会移动到现在, 否则就不移动
                // 在切换坐标轴之前, 找一下有没有可以带到新的坐标轴上的元素
                prePointSpace.points.remove(pf);

                axialPoints = pointSpace.points;    // 切换至新的坐标轴

                if(movePoint != null){
                    movePoint.setAxialValue(0);
                    if(!axialPoints.add(movePoint)){
                        throw new IllegalStateException("this coordinate's point has exists. point : " + movePoint.intrinsic());
                    }
                }
            }

            pp = pos;
            pos = traveller.next(); // 获取将要去往的空间编号
            di++;
        }

        if(pos != 0){
            pf.axialValue = pp;
            movePoint = axialPoints.find(pf);
            axialPoints.remove(pf);

            axialPoints = pointSpace.points;

            if(movePoint != null){
                if(!axialPoints.add(movePoint)){
                    throw new IllegalStateException("this coordinate's point has exists. point : " + movePoint.intrinsic());
                }
            }
        }

        // 向下继续填0继续寻找空间
        sf.index = pos;
        XSpace<T> xSpace = pointSpace.subspaces.find(sf);
        if(xSpace != null){
            pos = 0;
            while(xSpace != null){
                ISpace<T> subspace = xSpace.space;
                if(subspace instanceof InterleavedSpace) {
                    pointSpace = ((InterleavedSpace<T>)subspace);
                    axialPoints = pointSpace.points;
                    xSpace = pointSpace.subspaces.find(sf);
                }else{
                    // TODO 移入movePoint, 此时movePoint一定是(x1, x2, ... 0)
                    pointSpace.addPoint(point, new ContinueCoordinate(traveller, coordinate.dimension() - di));
                    return;
                }
            }
        }

        // 向子空间中添加点
        point.setAxialValue(pos);
        if(!axialPoints.add(point)){
            throw new IllegalStateException("this coordinate's point has exists. point : " + point.intrinsic());
        }
    }

//    @Override
//    public void addPoint(IPoint<T> point, ICoordinate coordinate) {
//        if(point == null || coordinate == null){
//            throw new IllegalArgumentException("point info is null.");
//        }
//
//        if(coordinate.dimension() > this.dimension){
//            this.dimension = coordinate.dimension();
//        }
//
//        Itraveller<Integer> traveller = coordinate.traveller();
////        if(coordinate.dimension() <= 1){
////            int pos = traveller.hasNext() ? traveller.next() : 0;
////            point.setAxialValue(pos);
////            this.points.add(point);
////            return;
////        }
//
//        int pos = traveller.hasNext() ? traveller.next() : 0;
//        int di = 1;
//
//        XSpace<T> sf = new XSpace<>(pos);
//        XPoint<T> pf = new XPoint<>(pos);
//        InterleavedSpace<T> pointSpace = this;
//        ISet<IPoint<T>> axialPoints = pointSpace.points;
//        ISet<IPoint<T>> preAxialPoints = axialPoints;
//
//        IPoint<T> movePoint = axialPoints.find(pf);
//
//        while(traveller.hasNext()){
//            // 这个位置的pos是将要去的子空间的编号
//
//            /*
//             * 循环不变式:
//             *      1. 尾部0去除
//             *          如果一个坐标最后几位全是0, 则会去掉.
//             *          例如:
//             *              (x1, x2, 0, 0, 0)与(x1, x2)是等价的
//             *      2. 对齐存放
//             *          如果两个坐标除最后一位以外, 其余各位均相同, 则这两个坐标点放在会存储在同一个points内
//             *          例如:
//             *              (x1, x2, 1)和(x1, x2, 2)会存放在同一个points里, 并且编号是1和2
//             */
//
//            // 为待添加的点寻找/开辟空间
//            sf.index = pos;
//            XSpace<T> xSpace = pointSpace.subspaces.find(sf);
//            if(xSpace == null){
//                InterleavedSpace<T> subSpace = new InterleavedSpace<>();
//                subSpace.dimension = coordinate.dimension() - di;
//                xSpace = new XSpace<>(pos, subSpace);
//                pointSpace.subspaces.add(xSpace);
//            }
//
//            ISpace<T> subspace = xSpace.space;
//
//            if(subspace instanceof InterleavedSpace) {
//                pointSpace = ((InterleavedSpace<T>)subspace);   // 以该子空间为基准, 准备下一轮寻址
//            }else{  // 如果该空间已经变成了别的类型的空间, 调用该空间的addPoint方法
//                // TODO 移入movePoint
//                pointSpace.addPoint(point, new ContinueCoordinate(traveller, coordinate.dimension() - di));
//                return;
//            }
//
//            /*
//             * 程序执行到这一步所处的状态:
//             * 1. 根据坐标, 空间已经切换至新的空间
//             * 2. 坐标轴依旧是旧的坐标轴, 没有切换过来
//             *
//             * 这时, 需要判断坐标轴是否需要切换
//             * 1. 如果新的空间依旧是0号空间, 不需要切换坐标轴(为了保证循环不变式1)
//             * 2. 如果新的空间不再是0号空间, 需要切换坐标轴
//             */
//            if(pos != 0){   // 现在不等于0, 才会移动到现在, 否则就不移动
//                preAxialPoints = axialPoints;
//                axialPoints = pointSpace.points;    // 使用新的坐标轴
//            }
//
//            pos = traveller.next();
//            di++;
//
//            /*
//             * 这里可以看出:
//             *      movePoint重新摆放的时机: 将要去往的空间不是0号空间了
//             *             为什么这时候需要重新摆放pos?
//             *                  因为movePoint是从上级空间中一点点移动到这里的, movePoint重新摆放, 坐标会发生变化,
//             *                  但是如果尾部的坐标均是0, 则相当于没有变化, 当去往空间不再是0号空间, 这时如果不重新摆放movePoint
//             *                  那么movePoint摆放到下一级空间的话, movePoint坐标尾部就不再是0了, 导致坐标发生变化了, 所以需要重新摆放
//             *      movePoint重新存入的位置: 当前子空间(pointSpace)的points的0位置中
//             *
//             *
//             * 当前程序执行的状态:
//             *      获取到pos将要去往的下级空间编号
//             *      这时, 判断本级空间中有没有可以去往下级空间的点, 如果有, 指定为movePoint
//             *
//             * 讨论:
//             *  新的movePoint会不会覆盖旧的movePoint?
//             *  这里令旧的movePoint为P1, 新的movePoint为P2
//             *
//             *  首先, 讨论movePoint覆盖的前提是pos == 0
//             *      这是因为如果pos != 0, 下面的代码中已经将P1放入该空间中了, 即使覆盖了也没有关系
//             *
//             *  如果存在P1和P2覆盖的情况, 则
//             *      P1与P2前几级坐标全部相等, 由于P1, P2都不是新加入空间中的点, 所以根据循环不变式的对齐存放, 这P1, P2必定存放在同一个points中
//             *      显然这与当前P1, P2假定的情况相互矛盾, 所以不存在P1, P2覆盖的情况
//             */
//            if(pos != 0 && movePoint != null){
//                /*
//                 * 如果新的空间不是0号空间, 从上一级带过来的点应该放入该空间的0点位置
//                 */
//                preAxialPoints.remove(movePoint);
//                movePoint.setAxialValue(0);
//                if(!pointSpace.points.add(movePoint)){  // 将点放入现在的空间
//                    throw new IllegalStateException("this coordinate's point has exists. point : " + movePoint.intrinsic());
//                }
//            }
//            pf.axialValue = pos;
//            movePoint = axialPoints.find(pf);
//        }
//
//        if(pos != 0){
//            if(movePoint != null){
//                axialPoints.remove(movePoint);
//                if(!pointSpace.points.add(movePoint)){
//                    throw new IllegalStateException("this coordinate's point has exists. point : " + movePoint.intrinsic());
//                }
//            }
//            axialPoints = pointSpace.points;
//        }
//
//        // 向子空间中添加点
//        point.setAxialValue(pos);
//        if(!axialPoints.add(point)){
//            throw new IllegalStateException("this coordinate's point has exists. point : " + point.intrinsic());
//        }
//    }

    @Override
    public IPoint<T> getPoint(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();

        int pos = 0;
        int prepos = 0;
        int di = 0;
        XSpace<T> finder = new XSpace<>(pos);

        ISet<IPoint<T>> axialPoints = this.points;
        InterleavedSpace<T> pointSpace = this;

        while(traveller.hasNext()){
            pos = traveller.next();
            di++;

            finder.index = pos;
            XSpace<T> xSpace = pointSpace.subspaces.find(finder);

            if(xSpace == null){
                if(traveller.hasNext()){
                    return null;
                }else{
                    break;
                }
            }else{
                ISpace<T> subSpace = xSpace.space;
                if(subSpace instanceof InterleavedSpace){
                    pointSpace = (InterleavedSpace<T>) subSpace;
                    if(pos != 0){
                        prepos = pos;
                        axialPoints = pointSpace.points;
                    }
                }else{
                    return subSpace.getPoint(new ContinueCoordinate(traveller, this.dimension - di));
                }
            }

        }

        pos = pos == 0 ? prepos : pos;
        return axialPoints.find(new XPoint<>(pos));
    }

    @Override
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        ISpace<T> space = this;
        ISpace<T> result = space;

        int pos = 0;
        int di = 0;

        XSpace<T> finder = new XSpace<>(pos);
        XSpace<T> xSpace;

        while(traveller.hasNext()) {
            di++;
            pos = traveller.next();
            if(space instanceof InterleavedSpace){
                finder.index = pos;
                xSpace = ((InterleavedSpace<T>) space).subspaces.find(finder);
                if(xSpace == null){ break; }
                else{ space = xSpace.space; }
            }else{
                return space.getSubspace(new ContinueCoordinate(traveller, this.dimension - di));
            }

            result = space;
        }

        if(traveller.hasNext()){
            while(traveller.hasNext()){
                if(traveller.next() != 0){
                    return null;
                }
            }
            result = space;
        }

        return result;
    }

    @Override
    public int dimension() {
        return this.dimension;
    }

    @Override
    public int width(ICoordinate coordinate) {
        ISpace<T> space = this.getSubspace(coordinate);
        if(space == null){
            return 0;
        }else{
            if(space instanceof InterleavedSpace){
                if(coordinate.dimension() == this.dimension){
                    return ((InterleavedSpace)space).points.size();
                }else{
                    return ((InterleavedSpace)space).subspaces.size();
                }
            }else{
                return space.width(Coordinate.ORIGIN);
            }
        }
    }

    @Override
    public IList<IPoint<T>> getPoints() {
        IList<IPoint<T>> result = new ArrayList<>();
        Iterator<IPoint<T>> iterator = this.points.iterator();
        while(iterator.hasNext()){
            result.add(iterator.next());
        }

        Iterator<XSpace<T>> spaceIterator = this.subspaces.iterator();
        while(spaceIterator.hasNext()){
            XSpace<T> space = spaceIterator.next();
            ISpace<T> s = space.space;
            IList<IPoint<T>> points = s.getPoints();
            Iterator<IPoint<T>> pi = points.iterator();
            while(pi.hasNext()){
                result.add(pi.next());
            }
        }

        return new UnmodifiableList<>(result);
    }

    private static class ContinueCoordinate extends AbstractCoordinate{

        private Itraveller<Integer> traveller;

        private int dimension;

        private ContinueCoordinate(Itraveller<Integer> traveller, int dimension) {
            this.traveller = traveller;
            this.dimension = dimension;
        }

        @Override
        public void add(int axialValue) {
            throw new IllegalStateException("this object can't support this method.");
        }

        @Override
        public int dimension() {
            return this.dimension;
        }

        @Override
        public Itraveller<Integer> traveller() {
            return traveller;
        }
    }

    private static class XSpace<T> implements Comparable<XSpace<T>> {
        private int index;
        private ISpace<T> space;
        private XSpace(int index, ISpace<T> space) {
            this.index = index;
            this.space = space;
        }

        private XSpace(int index){
            this.index = index;
        }

        @Override
        public int compareTo(XSpace<T> o) {
            return this.index - o.index;
        }
    }

    private static class XPoint<T> extends AbstractPoint<T> {

        private int axialValue;

        private XPoint(int axialValue) {
            this.axialValue = axialValue;
        }

        @Override
        public T intrinsic() {
            return null;
        }

        @Override
        public int getAxialValue() {
            return this.axialValue;
        }

        @Override
        public void setAxialValue(int val) {
            // do nothing.
        }

    }
}
