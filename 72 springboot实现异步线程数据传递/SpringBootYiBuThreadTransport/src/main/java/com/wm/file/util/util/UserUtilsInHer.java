package com.wm.file.util.util;


public class UserUtilsInHer {
 private static final InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

 public static  String get(){
  return threadLocal.get();
 }
 public static void set(String userId){
  threadLocal.set(userId);
 }
 public static void clear(){
  threadLocal.remove();
 }
}
