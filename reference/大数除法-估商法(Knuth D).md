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

令$q = \lfloor u / v \rfloor$, 显然$1 < q \leq b$, 并且这里排除$q = b$的情况, 即$1 < q < b$, 在后面算法实现中, 我们会看到如何消除$q=b$的情况

现在, 寻找一种算法, 来求解$q$

> 需要强调一下, 下面除算法实现小结以外, 都是在探讨仅差一位的两个数的相除

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

给定非负整数

$$u = u_{m + n - 1} \cdot b ^ {m + n - 1} + \dots + u_1 \cdot b + u_0$$

和

$$v = v_{n - 1} \cdot b ^ {n - 1} + \dots + v_1 \cdot b + v_0$$

其中$v_{n - 1} \neq 0$且n > 1.

可得商:

$$\lfloor u / v \rfloor = q_m \cdot b ^ m + q_{m-1} \cdot q ^ {m - 1} + \dots + q_0$$

和余数:

$$u\, mod\, v = r_{n - 1} \cdot b ^ {n-1} + \dots + r_0$$

具体算法步骤如下:

**1. 规格化**

我们不直接求解$u/v$, 而是通过规格化, 将u和v同时适当扩大, 使得$v_{n-1}$满足定理B, 而商又保持不变, 从而, 可以保证在至多3次估商就能得到正确的结果

置$d = \lfloor b / (v_{n-1} + 1)\rfloor$, 然后置$u' = u \cdot d$, 置$v' = v \cdot d$

显然, $v'$与$v$具有相同的位数, $u'$有可能需要进1位, 所以$u'$和$v'$可以表示为:

$$
u' = u'_{m + n} \cdot b^{m + n} + u'_{m + n - 1} \cdot b^{m + n - 1} + \dots + u'_0
$$

> 需要注意的是$u'_{m+n}$可能是0

和

$$
v' = v'_{n - 1} \cdot b ^ {n - 1} + v'_{n - 2} \cdot b ^ {n - 2} + \dots + v'_0
$$

此时, 有:

$$
\frac{u}{v} = \frac{u'}{v'}
$$

且:

$$
v'_{n - 1} \geq \lfloor \frac{b}{2} \rfloor
$$

实际上, 这里还隐含一个很有用的结论, 即:

$$
\frac{u'}{b^m \cdot v'} < b
$$

这个条件使得上面的讨论的结论可以方便的应用, 因为上面的讨论一直是在$0 < q < b$下进行的. 附录中证明了这个结论.

**2. 初始化**

定义变量$i = m$, 第2 - 7步是一个循环, 实际上是将$u'_{j + n} \cdot b ^ {j + n} + \dots + u'_0$除以$v'$的除法, 得到的商只有一位, 即$q_j$

**3. 计算$\hat{q_j}$**

根据前面$\hat{q}$的定义, 有:

$$
\hat{q_j} = \lfloor \frac{u'_{j + n} \cdot b + u'_{j + n - 1}}{v'_{n - 1}}\rfloor
$$

同时, 余数为:

$$
\hat{r} = u'_{j + n} \cdot b + u'_{j + n - 1} \, mod\, v_{n - 1}
$$

检查估商的正确性, 测试是否$\hat{q_j} = b$或$\hat{q_j} \cdot v'_{n-2} > b\hat{r} + u'_{j+n-2}$

如果是, 则使得$\hat{q_j} = \hat{q_j} - 1$, $\hat{r} = \hat{r} + v_{n - 1}$, 此时, 如果$\hat{r} < b$, 重复此测试.

> 上面的论证中可知, $\hat{q_j} = b$时, $\hat{q_j} > q$值. 对于$\hat{q_j} \cdot v'_{n-2} > b\hat{r} + u'_{j+n-2}$时, $\hat{q_j} > q$的证明见附录.

## 附录

**证明一**

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

----

**证明二**

求证:

$$
\frac{u'}{b^m \cdot v'} < b
$$

证明:

1. 当$u'_{m+n} = 0$时, 显然该结论成立.

2. 当$u'_{m+n} > 0$时:

$$
\frac{u'}{b^m \cdot v'} 
= \frac{u'_{m + n} \cdot b^{m + n} + u'_{m + n - 1} \cdot b^{m + n - 1} + \dots + u'_0}{b^m(v'_{n - 1} \cdot b ^ {n - 1} + v'_{n - 2} \cdot b ^ {n - 2} + \dots + v'_0)} 
< \frac{u'_{m+n} \cdot b^n+u'_{m+n-1} \cdot b^{n - 1} + \dots + u'_{m} + 1}{v'_{n - 1} \cdot b ^ {n - 1} + v'_{n - 2} \cdot b ^ {n - 2} + \dots + v'_0} \\
< \frac{u'_{m + n}\cdot b ^ n + (u'_{m + n - 1} + 1)b^{n-1}}{(v'_{n-1} - 1)b^{n-1}} = \frac{u'_{m+n}b + u'_{m+n-1} + 1}{v'_{n-1} - 1}
$$

所以, 需要证明:

$$
\frac{u'_{m+n}b + u'_{m+n-1} + 1}{v'_{n-1} - 1} \leq b
$$

即, 需要证明:

$$
u'_{m+n} - v'_{n - 1} < \frac{u'_{m+n-1} + 1}{b} - 1
$$

显然$(u'_{m+n-1} + 1)/b \leq 1$, 所以, 即证:

$$
u'_{m+n} - v'_{n - 1} < 0或u'_{m+n} < v'_{n - 1}
$$

又根据$u'_{m+n} = (u_{m+n-1}d) / b$以及$v'_{n-1} \geq v_{n-1}d$, 所以即证:

$$
\frac{u_{m+n-1}d}{b} < v_{n-1}d\\
\frac{u_{m+n-1}}{b} < v_{n-1}
$$

显然, $u_{m+n-1}<v_{n-1}$成立, $\frac{u'}{b^m \cdot v'} < b$成立. 

证毕.