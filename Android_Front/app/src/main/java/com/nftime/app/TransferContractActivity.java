package com.nftime.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nftime.app.Adapter.TransferContractClauseRecyclerAdapter;
import com.nftime.app.RecyclerItem.TransferContractClauseItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransferContractActivity extends AppCompatActivity {
    private Button btnContractOk;

    private TextView tvContractTitle;
    private TextView tvContractTitleDetail;

    private TextView tvDate;

    private RecyclerView RVTransferContractClause;
    private TransferContractClauseRecyclerAdapter transferContractClauseRecyclerAdapter;
    ArrayList trasnferContractClauseItems = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_contract);

        tvContractTitle = findViewById(R.id.tv_contract_title);
        tvContractTitleDetail = findViewById(R.id.tv_contract_title_detail);

        btnContractOk = findViewById(R.id.btn_contract_ok);

        RVTransferContractClause = findViewById(R.id.rv_trasnfercontract_clause);

        tvDate = findViewById(R.id.tv_date);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getDate = dateFormat.format(date);
        tvDate.setText(getDate);

        // Korean Contract Definition
        tvContractTitle.setText("디지털 아트 소유권 양수도계약서 (양수도-양수자)");
        tvContractTitleDetail.setText("양수자(이하 ‘양수자’)와 양도자(이하 ‘양도자’)는 아래와 같이 디지털 아트 소유권의 양수도계약(이하 ‘본 계약’)을 체결한다. 양수자/양도자의 정보, 매매 대상물 및 본 계약 체결일은 본 계약 마지막에 첨부되어 있는 표에 명시한다. 해당 표도 본 계약의 일부로 간주한다.");

        // English Contract Definition
        tvContractTitle.setText("Digital Art Ownership Transfer Agreement");
        tvContractTitleDetail.setText("The transferee (hereinafter referred to as the" + "transferor" + ") and the transferor (hereinafter referred to as the" + "transferor" + "this contract" +") as follows. The information of the transferee/transferor, the object of sale and the date of conclusion of this Agreement shall be specified in the table attached at the end of this Agreement. The table is also considered to be part of this Agreement.");
        // 인기 작가 리사이클러 뷰
        transferContractClauseRecyclerAdapter = new TransferContractClauseRecyclerAdapter();

        RVTransferContractClause.setAdapter(transferContractClauseRecyclerAdapter);
        RVTransferContractClause.setLayoutManager(new LinearLayoutManager(this));

        // Korean Clause
/*        trasnferContractClauseItems.add(new TransferContractClauseItem("제1조 (목적)", "본 계약은 디지털 아트(아래 제2조 및 제3조에서 정의함)의 저작권자로부터 본 계약을 위한 모든 권리를 적법하게 위임받은 양도자가 양수자에게 디지털 아트의 소유권(아래 제2조에서 정의함)을 이전하고 양수자가 양도자에게 그 대금을 지급함에 있어서 필요한 제반 사항과 당사자의 권리 및 의무를 규율함에 목적이 있다."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("제2조 (정의)", "본 계약에서 사용하는 용어의 뜻은 아래와 같다.\n" +
                "\n" +
                "- “엔에프타임”이란 주식회사 엔에프타임(이하 “엔에프타임”)이 운영하는 디지털 아트 중개 플랫폼인 엔에프타임(nftime)를 의미한다.\n" +
                "- “디지털 아트”란 전자적 기술을 창작 및 표현의 핵심 요소로 활용하는 디지털 예술 콘텐츠로서 구체적인 사항은 아래 제3조에 따른다.\n" +
                "- “저장소”란 디지털 아트를 전자적 방식으로 저장하는 곳으로 엔에프타임이 제공하는 장소를 의미한다.\n" +
                "- “디지털 아트 소유권”이란 디지털 아트 보유권, 디지털 아트 사용권, 디지털 아트 처분권을 의미한다.\n" +
                "1. “디지털 아트 보유권”이란 디지털 아트를 기간 제한없이 보존할 수 있는 권리를 의미한다.\n" +
                "2. “디지털 아트 사용권”이란 디지털 아트를 원형 그대로 표시, 감상하거나 비상업적인 목적으로 사회관계망 서비스 또는 공개된 게시판 등에 게시할 수 있는 권리를 의미한다.\n" +
                "3. “디지털 아트 처분권”이란 디지털 아트의 소유권을 가진 자가 디지털 아트 소유권을 매도할 수 있는 권리를 의미한다.\n" +
                "5. “디지털 아트 저작권”이란 디지털 아트에 대하여 저작권법에서 규정하는 저작인격권과 저작재산권을 의미한다."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("제3조 (매매 대상 디지털 아트)", "본 계약에 따라 매매의 대상이 되는 디지털 아트는 아래 첨부된 표의 작품명에 기재된 작품으로 한다."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("제4조 (매매대금 및 지급 방법)", "1. 제3조의 디지털 아트의 매매대금은 엔에프타임에 게시된 해당 디지털 아트의 지정가 또는 낙찰가로 한다.\\n\" +\n" +
                "                \"2. 양도자는 양수자가 매매대금을 지급하면 디지털 아트 원본을 양수자에게 엔에프타임이 제공하는 서비스를 통하여 이전한다."));*/

        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 1 (Purpose)", "The purpose of this Agreement is to regulate all necessary matters and the rights and obligations of the parties in transferring ownership of the Digital Art (as defined in Article 2) to the transferee by the transferor, who is legally delegated all rights for this Agreement from the copyright holder of Digital Art (as defined in Articles 2 and 3 below)."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 2 (Definition)", "The meanings of the terms used in this Agreement are as follows.\n" +
                "\n" +
                "- \"nftime\" means nftime (nftime), a digital art brokerage platform operated by nftime Co., Ltd. (\"nftime\").\n" +
                "- The term \"digital art\" means digital art content that utilizes electronic technology as a key element of creation and expression, and specific matters shall be governed by Article 3 below.\n" +
                "- The term \"storage\" means a place where digital art is stored electronically and provided by NFTime.\n" +
                "- The term \"digital art ownership\" means the right to hold digital art, the right to use digital art, and the right to dispose of digital art.\n" +
                "1. The term \"right to retain digital art\" means the right to preserve digital art without limitation.\n" +
                "2. The term \"digital art license\" means the right to display and appreciate digital art as it is, or to post it on social networking services or public bulletin boards for non-commercial purposes.\n" +
                "3. The term \"right to dispose of digital art\" means the right of a person who has ownership of digital art to sell ownership of digital art.\n" +
                "4. The term \"digital art ownership\" means the right to hold digital art, the right to use digital art, and the right to dispose of digital art.\n" +
                "4-1. The term \"right to retain digital art\" means the right to preserve digital art without limitation.\n" +
                "4-2. The term \"digital art license\" means the right to display and appreciate digital art as it is in its original form or to post it on social networking services or public bulletin boards for non-commercial purposes.\n" +
                "4-3. The term \"right to dispose of digital art\" means the right of a person who has ownership of digital art to sell ownership of digital art." +
                "5. The term \"digital art copyright\" means the rights of copyrights and copyrights prescribed in the Copyright Act for digital art."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 3 (Digital Art Subject to Sale)", "The digital art subject to sale under this Agreement shall be the work listed in the name of the work in the attached table below."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 4 (Trading price and method of payment)", "1. The sale price of digital art under Article 3 shall be the designated price or successful bid price of the relevant digital art posted at nftime.\n" +
                "2. When the transferee pays the transaction price, the transferor transfers the original digital art to the transferee through the service provided by nftime."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 5 (Confirmation and Warranty)", "The transferor shall confirm and guarantee the following matters concerning the digital art subject to this contract:\n" +
                "\n" +
                "1. Digital art does not infringe on intellectual property rights and other rights of third parties\n" +
                "2. Before signing this contract, the relevant digital art does not have any burden to restrict the use of the transferee, such as transfer of copyright, permission, or pledge to a third party\n" +
                "3. The transferor has the legitimate authority and right to enter into this contract\n" +
                "4. The copyright holder complies with the obligations stipulated in Article 8"));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 6 (Copyright)", "1. The transferor transfers ownership of the digital art to the transferee. Other than that, copyrights are not transferred to the transferee.\n" +
                "2. Where a transferee posts, performs, or displays digital art pursuant to Article 7(4), the name of the author (or a name that can represent the author) and the name of the digital art shall be displayed in a way that does not go against the will of the author according to the custom of the digital art world.\n" +
                "3. The transferee shall not use digital art in a way that harms the author's personality rights, such as modulation, alteration, or damage to digital art, and shall respect the author's personality rights.\n" +
                "4. If necessary, such as the copyright holder having to check the original of digital art in order to take legal action against copyright infringement on digital art, the transferee must cooperate with the copyright holder's infringement control.\n" +
                "5. If a third party claims infringement of intellectual property rights and other rights in connection with the transferee's use of digital art, the transferor shall resolve it at his own cost and responsibility and indemnify the transferee. However, this is not the case if there is a reason attributable to the transferee in relation to the above claim by a third party."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 7 (Rights and Obligations of the Transferee)", "1. The transferee acquires ownership of the digital art by transferring the digital art in accordance with this contract.\n" +
                "2. The transferee can access the digital art stored in the storage to appreciate the purchased digital art and download the digital art.\n" +
                "3. The transferee may sell the ownership of the purchased digital art to the extent that it does not violate this contract. However, the transferee may sell/dispose digital art purchased under this contract to a third party only through NF time in the position of a non-business entity that does not sell it, and shall not sell/dispose it to another third party by copying it separately.\n" +
                "4. The transferee may post, perform, or exhibit digital art on an open bulletin board for non-commercial purposes only. If you intend to post, perform, or display digital art for commercial purposes, you must obtain prior consent from the copyright holder."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 8 (Duties, etc. of the Transferor)", "After the ownership of the digital art is transferred to the transferee pursuant to Article 4, the transferee and copyright holder shall not issue or reproduce additional digital art in addition to the quantity sold through NF Time and shall not interfere with the exercise of the transferee's rights under Article 7."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 9 (Prohibition of Transfer of Rights and Obligations)", "The transferor or transferee may not transfer the rights or obligations under this contract to a third party or provide them for the purpose of collateral without the prior consent of the other party."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 10 (Cancellation of Contract)", "1. Both parties may cancel this contract in the event of a natural disaster or other force majeure.\n" +
                "2. If the other party violates this contract without justifiable reasons, one party may urge the other party to correct it for a considerable period of time, and cancel the contract if the other party fails to perform it after that period. However, if the other party clearly expresses its intention to refuse correction or it is clearly recognized that correction is impossible due to the nature of the violation, the contract may be canceled without the above request.\n" +
                "3. The exercise of the right to cancel this contract does not affect the exercise of the right to claim damages against the other party."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 11 (Compensation for Damages)", "1. If one party violates this contract without justifiable reason, it is responsible for compensating the other party for all damages caused thereby. However, if this contract is not fulfilled due to the reason of Article 10 (1), liability for damages is avoided.\n" +
                "2. The transferee cannot claim compensation for damages to NfTime for confirmation and guarantee of the transferor. However, this is not the case when the transferor's confirmation and guarantee are forged, altered, or caused an error due to intentional or gross negligence of NF Time."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 12 (Controversy Resolution, etc.)", "1. In the event of a dispute in connection with this contract, both parties shall endeavor to resolve the dispute through mutual consultation.\n" +
                "2. Where a party files a lawsuit related to this contract, the competent court shall comply with the Civil Procedure Act."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 13 (Effect of this Agreement)", "1. This Agreement shall take effect from the date of conclusion of the Agreement.\n" +
                "2. This Agreement shall prevail over any verbal or written agreement or intention between the parties from the past to the date of signing this Agreement, regardless of the contents of the meeting, memorandum, memo, e-mail, or memorandum of understanding, as long as such verbal or written agreement or intention conflicts with the contents of this Agreement."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 14 (Keeping confidentiality)", "The two parties shall not disclose information on the other party, the contents of this contract, and the contents of digital art learned during the conclusion and implementation of this contract to a third party without the other party's written consent. The obligations under this section shall remain in effect even when this Agreement is terminated or terminated."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 15 (Taxes, etc.)", "Both parties shall bear the various tax and public charges incurred in connection with this contract."));
        trasnferContractClauseItems.add(new TransferContractClauseItem("Article 16 (Other)", "Matters not specified in this Agreement shall be determined by mutual consultation between the two parties in good faith, but in accordance with relevant laws, general commercial practices, and common practices in the Korean digital art world."));

        transferContractClauseRecyclerAdapter.setTransferContractClauseItems(trasnferContractClauseItems);

        btnContractOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}