# 大数除法-估商法

## 概述

这里的大数除法采用Knuth的书《计算机程序设计艺术》第二卷4.3.1节D算法, 大部分描述均直接摘自该部分内容. 这里只讨论非负整数的除法, 其余情况实际上均可以转换为该种情况. 实际应用中, 一般采用万进制来提高计算及存储效率.

## 引理

这里有两个b进制正整数, 分别是$n + 1$位正整数$u$:

$$
u = u_n \cdot b ^ n + u_{n-1} \cdot b ^ {n - 1} + \dots + u_0 \cdot b ^ 0
$$

以及$n$位正整数$v$:

$$
v = v_{n - 1} \cdot b ^ {n - 1} + v_{n - 2} \cdot b ^ {n - 2} + \dots + v_0 \cdot b ^ 0
$$

其中, $u_i < b, v_i < b, u_n > 0, v_{n - 1} > 0$

令$q = \lfloor u / v \rfloor$, 显然$1 < q \leq b$, 并且这里排除$q = b$的情况, 即$1 < q < b$

现在, 寻找一种算法, 来求解$q$

**引理1**

由于$q = \lfloor u / v \rfloor < b$, 所以, $\lfloor u / b \rfloor < v$, 即:

$$
u_n \cdot b ^ {n - 1} + u_{n-1} \cdot b ^ {n - 2} + \dots + u_1 \cdot b ^ 0 < v_{n - 1} \cdot b ^ {n - 1} + v_{n - 2} \cdot b ^ {n - 2} + \dots + v_0 \cdot b ^ 0
$$

**引理2**

如果令$r = u - qv$, 则$q$是使得$0 \leq r < v$的**唯一**整数

***证明:***

假设$\exists p \in N^+, p \neq q$, 使得$r = u - pv$并且$0 < r < v$

* *情况一*

讨论$p > q$的情况, 不妨设$p = q + 1 = \lfloor u/v \rfloor + 1$

由于, $\lfloor u / v \rfloor \leq u / v$, 所以:

$$
r = u - pv \leq u - (u / v + 1)v = -v
$$

与$0 \leq r$矛盾, 所以, $p > q$不成立



* *情况二*

讨论$p < q$的情况, 不妨设$p = q - 1 = \lfloor u/v \rfloor - 1$

由向下取整的性质$\lfloor a/b \rfloor \geq \frac{a - (b - 1)}{b}$, 可得:

$$
p \geq \frac{u - v + 1}{v} - 1
$$

所以有:

$$
r = u - pv \geq u - (\frac{u - v + 1}{v} - 1)v = 2v - 1
$$

与$r < v$矛盾, 所以, $p < q$不成立.

综上, $q$是使得$0 \leq r < v$的**唯一**整数.


## 定理

接上, 求解$q$的最明显解决方案是根据$u$和$v$的最高位数字, 来对$q$进行推测

下面, 令:

$$
\hat{q} = min(\lfloor \frac{u_n \cdot b + u_{n-1}}{v_{n-1}} \rfloor, b - 1)
$$

这个公式说的是$\hat{q}$是通过$u$的两位前导数字除以$v$的前导数字得到的, 如果结果是b或者更大, 则以$b-1$代替

下面研究一个事实, 只要$v_{n-1}$适当大, 这个值$\hat{q}$总是非常接近所求答案$q$. 为了分析$\hat{q}$怎样接近与$q$, 首先要说明$\hat{q}$绝对不会太小.

**定理A**

$$\hat{q} \geq q$$

***证明:***

由于$q \leq b - 1$, 如果$\hat{q} = b - 1$, 则定理成立.

否则, 有$\hat{q} = \lfloor (u_n \cdot b + u_{n-1})/v_{n-1} \rfloor$, 则

$$
\hat{q} \geq \frac{u_n \cdot b + u_{n-1} - (v_{n-1} - 1)}{v_{n-1}}
$$

所以:

$$
\hat{q}v_{n - 1} \geq u_n \cdot b + u_{n-1} - v_{n-1} + 1
$$

所以:

$$
u - \hat{q}v \leq u - \hat{q}v_{n-1} \cdot b ^ {n - 1} \\
\leq u_n \cdot b ^ n + \dots + u_0 - (u_n \cdot b + u_{n-1} - v_{n-1} + 1) \cdot b ^ {n-1} \\
= u_{n-2} \cdot b ^{n-2} + \dots + u_0 - b ^ {n-1} + v_{n-1} \cdot b ^ {n-1} \\
< v_{n-1} \cdot b ^{n-1} \leq v
$$

综上$u - \hat{q}v < v$, 又根据引理2可知$q$是保证$0 \leq r = u - qv < v$的唯一整数, 所以$\hat{q} \geq q$. 当$\hat{q} = q$时, $0 \leq r = u - \hat{q}v < v$, 当$\hat{q} > q$时, $u - \hat{q}v < 0$

**定理B**

$$
如果v_{n - 1} \geq \lfloor b / 2\rfloor, 则\hat{q} - 2 \leq q \leq \hat{q}
$$

***证明:***

首先:

$$
\hat{q} \leq \frac{u_n \cdot b + u_{n-1}}{v_{n-1}} = \frac{u_n \cdot b ^ n + u_{n-1} \cdot b ^ {n-1}}{v_{n-1} \cdot b ^ {n - 1}} \leq \frac{u}{v_{n-1} \cdot b ^ {n-1}} < \frac{u}{v - b ^ {n-1}}
$$

注意, 上式中$v \neq b ^ {n-1}$, 当$v = b^{n-1}$时, $v_{n-1} = 1$, 最后一步推到, 由小于号变为等于号. 总之有:

$$
\hat{q} \leq \frac{u}{v - b ^ {n-1}}
$$

此外, 由于$q = \lfloor u /v \rfloor \geq (u - (v - 1)) / v > (u / v) - 1$

所以, 这里暂时假设$\hat{q} \geq q + 3$, 则有:

$$
3 \leq \hat{q} - q < \frac{u}{v - b^{n-1}} - \frac{u}{v} + 1 = \frac{u}{v}(\frac{b^{n-1}}{v - b ^ {n-1}}) + 1
$$

因此:

$$
\frac{u}{v} > 2(\frac{v - b ^ {n - 1}}{b ^ {n-1}}) \geq 2(v_{n - 1} - 1)
$$

显然, $2(v_{n-1} - 1) \in N^+$, 所以$\lfloor u / v \rfloor \geq 2(v_{n-1} - 1)$

又, 由于$\hat{q} \leq b - 1$

$$
b - 1 - 3 \geq \hat{q} - 3 \geq q = \lfloor \frac{u}{v} \rfloor
$$

综上有:

$$
b - 4 \geq 2(v_{n-1} - 1)
$$

即, $v_{n - 1} < \lfloor b / 2 \rfloor$. 这里我们归纳一下, 可知:

$$
当\hat{q} \geq q + 3时, v_{n-1} < \lfloor b / 2 \rfloor
$$

上面结论的逆否命题也成立, 即:

$$
当v_{n-1} \geq \lfloor b / 2 \rfloor时, \hat{q} < q + 3
$$

适当缩放既有:

$$
如果v_{n - 1} \geq \lfloor b / 2 \rfloor, 则\hat{q} - 2 \leq q \leq \hat{q}
$$

## Knuth D算法

给定非负整数$u = u_{m + n - 1} \cdot b ^ {m + n - 1} + \dots + u_1 \cdot b + u_0$和$v = v_{n - 1} \cdot b ^ {n - 1} + \dots + v_1 \cdot b + v_0$, 其中$v_{n - 1} \neq 0$且n > 1, 可得商$\lfloor u / v \rfloor = q_m$


## 附录

证明不等式:

$$
\lfloor \frac{a}{b} \rfloor \geq \frac{a - (b - 1)}{b}, a, b \in N^+
$$

证明:

不妨设$a = kb + c$, 其中, $k, c \in N^+$且$0 \leq c < b$

所以:

$$
\lfloor \frac{a}{b} \rfloor = \lfloor \frac{kb + c}{b} \rfloor = k
$$

又有:

$$
\frac{a}{b} = \frac{kb + c}{b} = k + \frac{c}{b}
$$

所以:

$$
\lfloor \frac{a}{b} \rfloor = \frac{a}{b} - \frac{c}{b} = \frac{a - c}{b}
$$

由于, $c < b$, 且$c \in N^+$, 所以$c \leq b - 1$, 所以:

$$
\lfloor \frac{a}{b} \rfloor = \frac{a - c}{b} \geq \frac{a - (b - 1)}{b}
$$

证毕.