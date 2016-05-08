package com.example.studio08.verysimplepodcast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by studio08 on 5/4/2016.
 */
public class WebViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String url = getArguments().getString("url");
        WebView webview = new WebView(getContext());
        webview.loadUrl(url);

        // found in stackOverflow, override this method so the url doesn't open in default browser
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        setContentView(webview);
        return super.onCreateView(inflater, container, savedInstanceState);


    }
}
