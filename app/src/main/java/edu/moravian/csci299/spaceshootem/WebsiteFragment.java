package edu.moravian.csci299.spaceshootem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class WebsiteFragment extends Fragment {

    public WebView webView;

    public WebsiteFragment() { /* Required empty public constructor */ }

    /** Create a new website fragment */
    public static WebsiteFragment newInstance() { return new WebsiteFragment(); }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_website, container, false);
        webView = rootView.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl((String) getText(R.string.web_url));
        webView.setWebViewClient(new WebViewClient());
        return rootView;
    }
}

