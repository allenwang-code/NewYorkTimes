package allenwang.newyorktimes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);

        Bundle b = getIntent().getExtras();
        if (b != null ) {
            url = b.getString("url");
        }
        webView.loadUrl(url);
    }
}
