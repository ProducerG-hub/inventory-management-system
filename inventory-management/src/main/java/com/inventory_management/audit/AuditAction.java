package com.inventory_management.audit;

public enum AuditAction {

    //Business actions
    CREATE,
    UPDATE,
    UPDATE_PROFILE,
    ACTIVATE,
    DEACTIVATE,
    RESTORE,
    CANCEL,
    DELETE,

    // Security actions
    LOGIN_SUCCESS,
    LOGIN_FAILED,
    LOGOUT
}