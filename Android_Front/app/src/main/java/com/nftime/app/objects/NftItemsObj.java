package com.nftime.app.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class NftItemsObj {
    public NftItemObj[] items;

    public NftItemsObj(NftItemObj[] items){
        this.items = items;
    }

    @Override
    public String toString() {
        return "NftItemsObj{" +
                "items=" + Arrays.toString(items) +
                '}';
    }

    public NftItemObj[] getItems() {
        return items;
    }

    public ArrayList<NftItemObj> getItemsWithAddress(String uAddr) {
        ArrayList<NftItemObj> itemsWithAddr = new ArrayList<NftItemObj>();
        int i = 0;

        for(NftItemObj el : items){
            if(uAddr.toLowerCase(Locale.ROOT).equals(el.owner.toLowerCase(Locale.ROOT))){
                itemsWithAddr.add(el);
            }
        }

        return itemsWithAddr;
    }
}
