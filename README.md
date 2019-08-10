# Calculator

## 概述

计算器, 支持多种运算, 尝试以灵活性和可扩展性为前提进行设计.

举例:

```txt
>>> 1-sqrt(2)*(3+4*(4!+5%)+6)+2/(1+3)
-147.27526676164962
>>> @a=12
12
>>> a=a+1
13.0
>>> a=a+3
16.0
>>> b=4
4
>>> a+b
20.0
>>> @frog(x,y)->x+y*x+2
frog(
>>> frog(1,2)
5.0
```

## 功能特性

* 支持算数优先级.
* 支持的运算:

    ```txt
    加, 减, 乘, 除, 阶乘, 百分, 括号
    ```

* 解析, 表达, 运算完全解耦, 支持自定义开发
* 定义变量, 赋值

## 开发计划

* 自定义函数
* 脱式计算
* 算式合法性校验
* 大数计算
* 矩阵, 行列式

## 涉及的设计模式

1. 解释器模式
2. 模板方法模式
3. 访问者模式
4. 单例模式
5. 责任链模式
6. 工厂方法模式
7. 组合模式
8. 外观模式
9. 建造者模式
