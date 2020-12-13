package frog.calculator;

import frog.calculator.build.IBuildManager;
import frog.calculator.util.collection.IList;

/**
 * 计算器管理器
 */
public interface ICalculatorManager {

    IBuildManager getBuildManager();

    IList<ICalculateListener> getCalculatorListeners();

}
