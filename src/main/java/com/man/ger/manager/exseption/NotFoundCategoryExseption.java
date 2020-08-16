package com.man.ger.manager.exseption;

public class NotFoundCategoryExseption extends RuntimeException {

    public NotFoundCategoryExseption() {
        super();
    }

    public NotFoundCategoryExseption(String message) {
        super(message);
    }

    public NotFoundCategoryExseption(String message, Throwable cause) {
        super(message, cause);
    }
}
