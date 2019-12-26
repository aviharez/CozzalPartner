package com.cozzal.partner.feature.owner.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.cozzal.partner.R;

public class MultiCalendarActivity extends AppCompatActivity {

    private final String TAG = MultiCalendarActivity.class.getSimpleName();

    private WebView wv_multi_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Multi Calendar");

        wv_multi_calendar = (WebView) findViewById(R.id.wv_multi_calendar);
        wv_multi_calendar.setWebViewClient(new WebViewClient());
        wv_multi_calendar.getSettings().setJavaScriptEnabled(true);
        wv_multi_calendar.getSettings().setDomStorageEnabled(true);
        wv_multi_calendar.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        wv_multi_calendar.loadUrl("https://www.google.com");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wv_multi_calendar.canGoBack()) {
                        wv_multi_calendar.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
