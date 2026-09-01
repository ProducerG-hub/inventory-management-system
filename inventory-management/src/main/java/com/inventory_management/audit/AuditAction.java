package com.inventory_management.audit;

public enum AuditAction {

    CREATE,
    UPDATE,
    UPDATE_PROFILE,
    ACTIVATE,
    DEACTIVATE,
    RESTORE,

    CANCEL,

    DELETE,

    LOGIN_SUCCESS,
    LOGIN_FAILED,
    LOGOUT
}