package com.jsql.utils;

/**
 * Created with IntelliJ IDEA.
 * User: myliang
 * Date: 12/13/12
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtil {

    public static String join(Object[] objects, String split){
        return join(objects, split, new JoinCallBack() {
            @Override
            public String it(Object obj) {
                return obj + "";
            }
        });
    }

    public static String join(Object[] objects, String split, JoinCallBack callback){
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < objects.length; i++){
            buffer.append(callback.it(objects[i]));
            if(i < objects.length - 1)
                buffer.append(split);
        }

        return buffer.toString();
    }

    /**
     * joinTable 回调函数
     */
    public static interface JoinCallBack{
        String it(Object obj);
    }

    public static void main(String[] args){

    }
}
