package com.invocation.server.models;

public enum ROLES {
    CITIZEN("CITIZEN"),
    CENTER("CENTER"),
    PROFESSIONAL("PROFESSIONAL"),
    ADMIN("ADMIN");

    private final String dbValue;

    ROLES(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static ROLES fromDbValue(String dbValue) {
        for (ROLES role : values()) {
            if (role.dbValue.equalsIgnoreCase(dbValue)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + dbValue);
    }
}