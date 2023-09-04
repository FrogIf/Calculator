package io.github.frogif.calculator.io;

public interface IInputStream {

    /**
     * 读取一行数据
     * @return 如果读到流末尾, 则返回null, 否则不能返回null
     */
    String readLine();

}
