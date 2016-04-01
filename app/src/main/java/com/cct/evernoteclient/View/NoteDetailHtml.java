package com.cct.evernoteclient.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cct.evernoteclient.R;
import com.evernote.client.android.EvernoteSession;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by carloscarrasco on 31/3/16.
 */
public class NoteDetailHtml extends AppCompatActivity {
    private String noteHtml;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail_html);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ContentLoadingProgressBar progresBar = (ContentLoadingProgressBar) findViewById(R.id.pb);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            noteHtml = bundle.getString("note_html");
            getSupportActionBar().setTitle(bundle.getString("title"));
        }

        final WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                try {
                    Response response = EvernoteSession.getInstance().getEvernoteClientFactory().getHtmlHelperDefault().fetchEvernoteUrl(url);
                    WebResourceResponse webResourceResponse = toWebResource(response);
                    if (webResourceResponse != null) {
                        return webResourceResponse;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progresBar.setVisibility(View.GONE);
            }
        });

        webView.loadDataWithBaseURL("", noteHtml, "text/html", "UTF-8", null);


    }

    protected WebResourceResponse toWebResource(Response response) throws IOException {
        if (response == null || !response.isSuccessful()) {
            return null;
        }

        String mimeType = response.header("Content-Type");
        String charset = response.header("charset");
        return new WebResourceResponse(mimeType, charset, response.body().byteStream());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
