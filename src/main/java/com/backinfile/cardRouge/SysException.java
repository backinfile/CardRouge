package com.backinfile.cardRouge;

public class SysException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SysException() {
        super();
    }

    public SysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(Throwable cause) {
        super(cause);
    }
}
