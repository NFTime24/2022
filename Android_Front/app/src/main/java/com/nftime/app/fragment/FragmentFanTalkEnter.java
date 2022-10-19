package com.nftime.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nftime.app.ArtistActivity;
import com.nftime.app.NFTPurchaseActivity;
import com.nftime.app.R;

public class FragmentFanTalkEnter extends Fragment {
    private Context context;
    private ViewGroup viewGroup;
    private Button btnEnterFandom;
    ArtistActivity artistActivity;

    public FragmentFanTalkEnter(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_fan_talk_enter, container, false);

        btnEnterFandom = viewGroup.findViewById(R.id.btn_etner_fandom_community);

        return viewGroup;
    }
}