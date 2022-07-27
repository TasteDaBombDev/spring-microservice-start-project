package com.postme.authService.models.enums;

public enum UserRolesEnum {
    ROLE_USER(Constants.ROLE_USER_VALUE),
    ROLE_ADMIN(Constants.ROLE_ADMIN_VALUE);

    private final String label;

    UserRolesEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static class Constants {
        public static final String ROLE_USER_VALUE = "ROLE_USER";
        public static final String ROLE_ADMIN_VALUE = "ROLE_ADMIN";
    }
}
