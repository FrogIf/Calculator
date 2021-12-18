# Document

## Expression

运算表达式, 用于指示计算器做哪些运算.

## Command

命令用于控制计算器运算过程中的行为.所有的命令执行前, 都需要以"#"开头.

* **输出表达式AST树**

开启/关闭AST语法树输出:```show_ast on/off```

默认值: ```show_ast off```

* **指定计算结果表示形式**

命令: ```num_mode none/plain/up/down/half_up/half_down/half_even/ceiling/floor/scientific precision```

默认值: ```num_mode plain 10```

参数解释:
1. 第一个参数:
   * none 不指定结果形式, 将会以化简之后的表达式形式输出, 而不进行计算
   * plain 朴素模式, 全部转化为小数进行输出
   * up 向远离0的方向取整, 如果超出整数部分长度超过1000或者小数部分非0位在1000以后, 则改用科学技术法形式.
   * down 向靠近0的方向取整, 同上.
   * half_up 五舍六入, 同上.
   * half_down 四舍五入, 同上.
   * half_even 银行家舍入, 同上.
   * ceiling 向上取整, 同上.
   * floor 向下取整, 同上.
2. 第二个参数, 保留小数位数. 对于up/down/half_up/half_down/half_even/ceiling/floor, 不足位补0.