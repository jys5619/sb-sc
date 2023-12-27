package com.base.sc.framework.db;

public class QueryResultData<T> {
    private Class<T> clazz;

    public T get() {
        try {
          return clazz.newInstance();

        //   Constructor<?> constructor = clazz.getDeclaredConstructor();
        //     if (constructor == null) {
        //         return null;
        //     }
        //     return (T) constructor.newInstance();
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      }
}
