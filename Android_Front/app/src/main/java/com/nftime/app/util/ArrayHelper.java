package com.nftime.app.util;

import java.util.ArrayList;

public class ArrayHelper {
    public static <T> ArrayList<T> toArrayList(T[] array){
        ArrayList<T> arrayList = new ArrayList<>();
        if(array != null)
            for(T e : array){
                arrayList.add(e);
            }

        return arrayList;
    }
}
