package com.xiaoleilu.loServer.action.enums;

public enum UserSettingScopeEnums {
    FAVORITE_GROUP(6, "群组最爱");
    private final Integer value;

    private final String message;

    UserSettingScopeEnums(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static UserSettingScopeEnums parse(String code) {
        if (code != null) {
            for (UserSettingScopeEnums info : values()) {
                if (info.name().equals(code)) {
                    return info;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name() + "|" + value + "|" + message;
    }
}
