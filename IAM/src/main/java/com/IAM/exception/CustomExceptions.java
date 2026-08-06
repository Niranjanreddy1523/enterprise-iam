package com.IAM.exception;

public class CustomExceptions {

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class RoleNotFoundException extends RuntimeException {
        public RoleNotFoundException(String message) {
            super(message);
        }
    }

    public static class PermissionNotFoundException extends RuntimeException {
        public PermissionNotFoundException(String message) {
            super(message);
        }
    }
}
