package com.woniu.holder;


import org.apache.commons.lang3.StringUtils;

/**
 * @author woniu
 * @describe:
 * @date 2024/2/19
 */
public final class ContextHolder {
    private final static ThreadLocal<String> CRYPT_HOLDER = ThreadLocal.withInitial(()-> StringUtils.EMPTY);

    public static String getCryptHandler(){
        return CRYPT_HOLDER.get();
    }

    public static void setCryptHolder(String cryptHolder){
        CRYPT_HOLDER.set(cryptHolder);
    }

    public static void clearContext(){
        CRYPT_HOLDER.remove();
    }

}
