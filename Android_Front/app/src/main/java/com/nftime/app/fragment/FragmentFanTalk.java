package com.nftime.app.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nftime.app.Adapter.FanTalkRecyclerAdapter;
import com.nftime.app.MainActivity;
import com.nftime.app.R;
import com.nftime.app.RecyclerItem.FanTalkItem;
import com.nftime.app.objects.ArtistObj;
import com.nftime.app.objects.FantalkObj;
import com.nftime.app.util.ArrayHelper;
import com.nftime.app.util.asyncTasks.AsyncResponse;
import com.nftime.app.util.asyncTasks.CreateFantalkTask;
import com.nftime.app.util.asyncTasks.GetArtistFantalksTask;

import java.util.ArrayList;

public class FragmentFanTalk extends Fragment {
    private ViewGroup viewGroup;
    private Context context;

    private EditText et_message;
    private Button btn_submit;

    private RecyclerView rvFanTalk;
    private FanTalkRecyclerAdapter fanTalkRecyclerAdapter;

    private ArtistObj artistObj;

    InputMethodManager imm;

    public FragmentFanTalk(ArtistObj artistObj){
        this.artistObj = artistObj;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_fan_talk, container, false);
        context = getContext();

        et_message = viewGroup.findViewById(R.id.et_fantalk_message);
        btn_submit = viewGroup.findViewById(R.id.btn_fantalk_submit);
        rvFanTalk = viewGroup.findViewById(R.id.rv_fantalk);

        fanTalkRecyclerAdapter = new FanTalkRecyclerAdapter(context);
        rvFanTalk.setAdapter(fanTalkRecyclerAdapter);
        rvFanTalk.setLayoutManager(new LinearLayoutManager(getActivity()));

        imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_message.getText().toString().length() == 0){
                    Toast submitToast = Toast.makeText(context, "팬톡을 작성해주세요.", Toast.LENGTH_SHORT);
                    submitToast.show();
                }
                else {
                    new CreateFantalkTask(new AsyncResponse<Integer>() {
                        @Override
                        public void onAsyncSuccess(Integer result) {
                            et_message.setText(null);
                            et_message.clearFocus();
                            imm.hideSoftInputFromWindow(et_message.getWindowToken(), 0);

                            Toast submitToast = Toast.makeText(context, "팬톡이 등록되었습니다.", Toast.LENGTH_SHORT);
                            submitToast.show();

                            new GetArtistFantalksTask(new AsyncResponse<FantalkObj[]>() {
                                @Override
                                public void onAsyncSuccess(FantalkObj[] result) {
                                    ArrayList<FantalkObj> fantalkObjs = ArrayHelper.toArrayList(result);
                                    fanTalkRecyclerAdapter.setFanTalkItems(fantalkObjs);
                                }
                            }).execute(artistObj.id);
                        }
                    }).execute(
                            String.valueOf(artistObj.id),
                            String.valueOf(MainActivity.myUserInfo.id),
                            et_message.getText().toString()
                    ); // artist_id, owner_id, post_text
                }
            }
        });

        new GetArtistFantalksTask(new AsyncResponse<FantalkObj[]>() {
            @Override
            public void onAsyncSuccess(FantalkObj[] result) {
                ArrayList<FantalkObj> fantalkObjs = ArrayHelper.toArrayList(result);
                fanTalkRecyclerAdapter.setFanTalkItems(fantalkObjs);
            }
        }).execute(artistObj.id);

        return viewGroup;
    }
}