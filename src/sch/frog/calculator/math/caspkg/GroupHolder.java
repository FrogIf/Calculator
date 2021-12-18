package sch.frog.calculator.math.caspkg;

import sch.frog.calculator.math.cas.Group;
import sch.frog.calculator.math.caspkg.rationaddgroup.RationalAdditionInverseTransformer;
import sch.frog.calculator.math.caspkg.rationaddgroup.RationalAdditionOperator;
import sch.frog.calculator.math.caspkg.rationaddgroup.RationalElement;
import sch.frog.calculator.math.number.RationalNumber;

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
