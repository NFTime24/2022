package com.nftime.app.RecyclerItem;

public class TransferContractClauseItem {
    String clauseTitle;
    String clauseDetail;

    public TransferContractClauseItem(String clauseTitle, String clauseDetail) {
        this.clauseTitle = clauseTitle;
        this.clauseDetail = clauseDetail;
    }

    public String getClauseTitle() {
        return clauseTitle;
    }

    public void setClauseTitle(String clauseTitle) {
        this.clauseTitle = clauseTitle;
    }

    public String getClauseDetail() {
        return clauseDetail;
    }

    public void setClauseDetail(String clauseDetail) {
        this.clauseDetail = clauseDetail;
    }
}
