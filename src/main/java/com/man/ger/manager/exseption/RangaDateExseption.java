package com.man.ger.manager.exseption;

public class RangaDateExseption extends RuntimeException {
    public RangaDateExseption() {
        super();
    }

    public RangaDateExseption(String message) {
        super(message);
    }

    public RangaDateExseption(String message, Throwable cause) {
        super(message, cause);
    }
}
