package frog.calculator.compile;

public interface IBuildListener {

    /**
     * 在下一个节点到来前执行
     * @return 该监听器是否继续监听: true - 继续, false - 销毁
     */
    boolean beforeNextNode();

    /**
     * ast树构建完成回调
     * @return
     */
    void onBuildFinish();
    
}
