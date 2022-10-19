package com.nftime.app.objects;

public class NftItemObj {
    public String tokenId;
    public String owner;
    public String previousOwner;
    public String tokenUri;
    public String transactionHash;
    public int createdAt;
    public int updateAt;

    public NftItemObj(String tokenId, String owner, String previousOwner, String tokenUri, String transactionHash, int createdAt, int updateAt){
        this.tokenId = tokenId;
        this.owner = owner;
        this.previousOwner = previousOwner;
        this.tokenUri = tokenUri;
        this. transactionHash = transactionHash;
        this. createdAt = createdAt;
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "NftItemObj{" +
                "tokenId='" + tokenId + '\'' +
                ", owner='" + owner + '\'' +
                ", previousOwner='" + previousOwner + '\'' +
                ", tokenUri='" + tokenUri + '\'' +
                ", transactionHash='" + transactionHash + '\'' +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
