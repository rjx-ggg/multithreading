package com.woniu.dynamicsql.util;


public class TenantRequestContext {
    private static ThreadLocal<String> tenantLocal = new ThreadLocal<>();

    public static void setTenantLocal(String tenantId) {
        tenantLocal.set(tenantId);
    }

    public static String getTenantLocal() {
        return tenantLocal.get();
    }

    public static void remove() {
        tenantLocal.remove();
    }
}