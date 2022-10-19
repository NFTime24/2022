package com.nftime.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nftime.app.R;

public class FragmentPageMarket extends Fragment {
    private Context context;
    private ViewGroup viewGroup;

    public FragmentPageMarket(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_market, container, false);

        return viewGroup;
    }
}