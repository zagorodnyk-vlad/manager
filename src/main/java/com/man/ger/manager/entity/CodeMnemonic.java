package com.man.ger.manager.entity;

public enum CodeMnemonic {
    USD(980),
    EUR(978),
    UAN(840);

    private int code;

    CodeMnemonic(int code) {
        this.code = code;
    }
    public static String getNameByCode(int code){
        for(int i = 0; i< CodeMnemonic.values().length; i++){
            if(code==(CodeMnemonic.values()[i].code))
                return CodeMnemonic.values()[i].name();
        }
        return null;
    }

    public int getCode() {
        return code;
    }
}
