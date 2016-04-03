package com.cct.evernoteclient.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cct.evernoteclient.Domains.ErrorManager;
import com.cct.evernoteclient.Domains.TaskResultInterface;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.R;
import com.cct.evernoteclient.View.NoteViewManager.NoteRepresentationFactory;
import com.evernote.client.android.EvernoteSession;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by carloscarrasco on 31/3/16.
 */
public class NoteDetailHtml extends AppCompatActivity {
    private Note note;
    private WebView webView;
    private ContentLoadingProgressBar progresBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail_html);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progresBar = (ContentLoadingProgressBar) findViewById(R.id.pb);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            note = (Note) bundle.get("note");
            getSupportActionBar().setTitle(note.getTitle());
        }
        webView = (WebView) findViewById(R.id.webview);
        configureWebView();

        loadNote();
    }

    private void configureWebView(){
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
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progresBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadNote() {
        new NoteRepresentationFactory().getNoteRepresentation().getNoteDataForRepresentation(note, new TaskResultInterface<String>() {
            @Override
            public void onSucces(final String result) {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadDataWithBaseURL("", result, "text/html", "UTF-8", null);
                    }
                });
            }

            @Override
            public void onError(ErrorManager error) {
                progresBar.setVisibility(View.GONE);
                Toast.makeText(NoteDetailHtml.this, error.getReason(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
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
