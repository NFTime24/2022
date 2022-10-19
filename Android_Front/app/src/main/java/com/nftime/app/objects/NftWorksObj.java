package com.nftime.app.objects;

import java.util.Arrays;

public class NftWorksObj {
    public NftWorkObj[] nft_infos;

    public NftWorksObj(NftWorkObj[] nft_infos){
        this.nft_infos = nft_infos;
    }

    @Override
    public String toString() {
        return "NftWorksObj{" +
                "nft_infos=" + Arrays.toString(nft_infos) +
                '}';
    }
}
