package cn.az.sec.exception;

import cn.az.sec.result.CodeMsg;

public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final CodeMsg cm;

    public CommonException(CodeMsg cm, Throwable e) {
        super(cm.toString(), e);
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
