package io.github.frogif.calculator.compile.lexical;

import io.github.frogif.calculator.compile.lexical.exception.ReadOutOfBoundsException;

/**
 * 表达式扫描器
 */
public interface IScanner {

    /**
     * 预读当前字符
     * @return
     */
    char peek() throws ReadOutOfBoundsException;

    /**
     * 是否没有结束
     * @return true - 是; false - 否
     */
    boolean isNotEnd();

    /**
     * 移动到下一个位置
     */
    char take() throws ReadOutOfBoundsException;

    /**
     * 当前所处位置
     */
    int position();

    PointerSnapshot snapshot();

    void applySnapshot(PointerSnapshot snapshot);

    /**
     * 指针快照
     */
    class PointerSnapshot {

        private final int pointer;

        public PointerSnapshot(int pointer) {
            this.pointer = pointer;
        }

        public int getPointer() {
            return pointer;
        }
    }
}
