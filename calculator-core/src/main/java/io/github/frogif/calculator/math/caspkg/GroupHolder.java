package io.github.frogif.calculator.math.caspkg;

import io.github.frogif.calculator.math.caspkg.rationaddgroup.RationalAdditionInverseTransformer;
import io.github.frogif.calculator.math.caspkg.rationaddgroup.RationalElement;
import io.github.frogif.calculator.math.caspkg.rationaddgroup.RationalAdditionOperator;
import io.github.frogif.calculator.number.impl.RationalNumber;
import io.github.frogif.calculator.math.cas.Group;

/**
 * 群持有者
 */
public class GroupHolder {
    /**
     * 有理数加法群
     */
    private final Group<RationalElement> rationalAddGroup
        = new Group<>(new RationalAdditionOperator(), new RationalElement(RationalNumber.ZERO), new RationalAdditionInverseTransformer());
}
