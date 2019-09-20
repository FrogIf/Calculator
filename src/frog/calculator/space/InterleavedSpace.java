package frog.calculator.space;

import frog.calculator.util.ComparableComparator;
import frog.calculator.util.collection.*;

public final class InterleavedSpace<T> extends MergeableSpace<T> {

    private ISet<XSpace<T>> subspaces = new TreeSet<>(ComparableComparator.<XSpace<T>>getInstance());

    private ISet<IPoint<T>> points = new TreeSet<>(PointComparator.getInstance());

    private int dimension;

    /**
     * 特点:
     *      1. 维度发生扩展时: 对齐存放
     *            如果两个点的坐标只有最后一位不同, 那么他们一定存放在同一个points中
     *      2. 新增点时: 对齐存放, 最小高度存放
     *            最小高度存放 : 如果一个点的坐标最后若干为都是0, 那么会忽略所有0, 保证整棵树的高度最低
     * @param point 待插入的点
     * @param coordinate 待插入点应插入的位置
     */
    @Override
    public void addPoint(IPoint<T> point, ICoordinate coordinate) {
        if(point == null || coordinate == null){
            throw new IllegalArgumentException("point info is null.");
        }

        coordinate.trimRight();
        this.dimension = coordinate.dimension() > this.dimension ? coordinate.dimension() : this.dimension;

        Itraveller<Integer> traveller = coordinate.traveller();

        int pos = traveller.hasNext() ? traveller.next() : 0;
        int di = 1;

        XSpace<T> sf = new XSpace<>(pos);
        XPoint<T> pf = new XPoint<>(pos);
        InterleavedSpace<T> pointSpace = this;
        InterleavedSpace<T> cursorSpace = this;

        IPoint<T> movePoint;
        InterleavedSpace<T> prePointSpace;

        boolean hasNew = false;

        while(traveller.hasNext()){
            pf.axialValue = pos;
            movePoint = pointSpace.points.find(pf);

            // 为待添加的点寻找/开辟空间
            sf.index = pos;
            XSpace<T> xSpace = hasNew ? null : pointSpace.subspaces.find(sf);
            if(xSpace == null){
                InterleavedSpace<T> subSpace = new InterleavedSpace<>();
                subSpace.dimension = coordinate.dimension() - di;
                xSpace = new XSpace<>(pos, subSpace);
                pointSpace.subspaces.add(xSpace);
                hasNew = true;
            }

            ISpace<T> subspace = xSpace.space;

            if(subspace instanceof InterleavedSpace) {
                prePointSpace = pointSpace;
                pointSpace = ((InterleavedSpace<T>)subspace);   // 以该子空间为基准, 准备下一轮寻址
            }else{  // 如果该空间已经变成了别的类型的空间, 调用该空间的addPoint方法
                pointSpace.addPoint(point, new ContinueCoordinate(traveller, coordinate.dimension() - di));
                if(movePoint != null){
                    pointSpace.addPoint(movePoint, AbstractCoordinate.ORIGIN);
                }
                return;
            }

            cursorSpace = pointSpace;   // 切换至新的空间

            if(movePoint != null){
                prePointSpace.points.remove(pf);
                movePoint.setAxialValue(0);
                if(!cursorSpace.points.add(movePoint)){
                    throw new IllegalStateException("this coordinate's point has exists. point : " + movePoint.intrinsic());
                }
            }

            pos = traveller.next(); // 获取将要去往的空间编号
            di++;
        }

        // 向下继续填0继续寻找空间
        if(!hasNew){
            sf.index = pos;
            XSpace<T> xSpace = pointSpace.subspaces.find(sf);
            if(xSpace != null){
                pos = 0;
                while(xSpace != null){
                    ISpace<T> subspace = xSpace.space;
                    if(subspace instanceof InterleavedSpace) {
                        pointSpace = ((InterleavedSpace<T>)subspace);
                        cursorSpace = pointSpace;
                        xSpace = pointSpace.subspaces.find(sf);
                    }else{
                        pointSpace.addPoint(point, AbstractCoordinate.ORIGIN);
                        return;
                    }
                }
            }
        }

        // 向子空间中添加点
        point.setAxialValue(pos);
        if(!cursorSpace.points.add(point)){
            throw new IllegalStateException("this coordinate's point has exists. point : " + point.intrinsic());
        }
    }

    @Override
    public IPoint<T> getPoint(ICoordinate coordinate) {
        if(coordinate == null){
            throw new IllegalArgumentException("coordinate is null.");
        }

        Itraveller<Integer> traveller = coordinate.traveller();

        int pos = -1;
        IPoint<T> result = null;
        InterleavedSpace<T> cursorSpace = this;
        XPoint<T> pf = new XPoint<>(pos);
        XSpace<T> sf = new XSpace<>(pos);
        int di = 0;

        while(traveller.hasNext()){
            pos = traveller.next();
            di++;
            pf.axialValue = pos;
            IPoint<T> point = cursorSpace.points.find(pf);

            if(point != null){
                result = point;
            }

            sf.index = pos;
            XSpace<T> xSpace = cursorSpace.subspaces.find(sf);
            if(xSpace == null){
                break;
            }else if(xSpace.space instanceof InterleavedSpace){
                cursorSpace = (InterleavedSpace<T>) xSpace.space;
            }else{
                return xSpace.space.getPoint(new ContinueCoordinate(traveller, coordinate.dimension() - di));
            }
        }

        if(result == null){
            pf.axialValue = 0;
            sf.index = 0;
            while(true){
                XSpace<T> xSpace = cursorSpace.subspaces.find(sf);
                if(xSpace == null){ break; }
                else if(xSpace.space instanceof InterleavedSpace){
                    cursorSpace = (InterleavedSpace<T>) xSpace.space;
                    result = cursorSpace.points.find(pf);
                }else{
                    return xSpace.space.getPoint(AbstractCoordinate.ORIGIN);
                }
                if(result != null){ break; }
            }
        }


        return result;
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
        if(coordinate == null || coordinate.dimension() == 0){
            return this.subspaces.size() + (this.points.size() > 0 ? 1 : 0);
        }else{
            Itraveller<Integer> traveller = coordinate.traveller();
            InterleavedSpace<T> space = this;
            int di = 0;
            XSpace<T> sf = new XSpace<>(-1);
            while(traveller.hasNext()){
                sf.index = traveller.next();
                di++;
                XSpace<T> xSpace = space.subspaces.find(sf);
                if(xSpace == null){
                    return 0;
                }else if(xSpace.space instanceof InterleavedSpace){
                    space = (InterleavedSpace<T>) xSpace.space;
                }else{
                    return xSpace.space.width(new ContinueCoordinate(traveller, this.dimension - di));
                }
            }
            int w = space.subspaces.size();
            if(w == 0){
                return space.points.size();
            }else{
                return w + (space.points.size() > 0 ? 1 : 0);
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

    @Override
    public void addSubspace(ICoordinate coordinate, ISpace<T> space) {
        if(coordinate == null || coordinate.dimension() == 0){
            throw new IllegalArgumentException("can't locate space.");
        }
        InterleavedSpace<T> sub = this;
        Itraveller<Integer> traveller = coordinate.traveller();
        XSpace<T> sf = new XSpace<>(traveller.next());
        XPoint<T> pf = new XPoint<>(sf.index);

        InterleavedSpace<T> pointSpace = null;
        IPoint<T> movePoint = null;
        boolean hasNew = false;

        int di = 1;
        while(traveller.hasNext()){
            XSpace<T> spaceX = sub.subspaces.find(sf);
            if(spaceX == null){
                InterleavedSpace<T> subSpace = new InterleavedSpace<>();
                subSpace.dimension = coordinate.dimension() - di;
                spaceX = new XSpace<>(sf.index, subSpace);
                sub.subspaces.add(spaceX);
                hasNew = true;
            }
            ISpace<T> tiSpace = spaceX.space;
            if(tiSpace instanceof MergeableSpace){
                if(tiSpace instanceof InterleavedSpace){
                    sub = (InterleavedSpace<T>) tiSpace;
                    if(!hasNew && (pf.axialValue != 0 || movePoint == null)){
                        if(movePoint != null){
                            pointSpace.points.remove(movePoint);
                            movePoint.setAxialValue(0);
                            if(!sub.points.add(movePoint)){
                                throw new IllegalStateException("the point has exist.");
                            }
                        }
                        pointSpace = sub;
                        movePoint = pointSpace.points.find(pf);
                    }
                }else{
                    ContinueCoordinate continueCoordinate = new ContinueCoordinate(traveller, this.dimension - di);
                    sub.addSubspace(continueCoordinate, space);
                    if(movePoint != null){
                        sub.addPoint(movePoint, continueCoordinate);
                    }
                    return;
                }
            }else{
                throw new IllegalStateException("parent space can't support merge.");
            }
            sf.index = traveller.next();
            di++;
        }

        sf.space = space;
        if(!sub.subspaces.add(sf)){
            throw new IllegalArgumentException("the space has exist.");
        }else{
            if(movePoint != null){
                pointSpace.points.remove(movePoint);
                space.addPoint(movePoint, AbstractCoordinate.ORIGIN);
            }
        }
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

        @Override
        public void trimRight() {
            // do nothing
        }

        @Override
        public void clear() {
            // do nothing
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
