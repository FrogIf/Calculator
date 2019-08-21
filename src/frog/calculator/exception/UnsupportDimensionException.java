package frog.calculator.exception;

import frog.calculator.util.StringUtils;

public class UnsupportDimensionException extends RuntimeException {
    public UnsupportDimensionException(String symbol, int dimension) {
        super(StringUtils.concat(symbol, " with dimension ", String.valueOf(dimension)));
    }
}
