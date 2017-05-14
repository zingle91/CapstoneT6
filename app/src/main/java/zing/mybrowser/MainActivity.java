package zing.mybrowser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private EditText mAddressEdit;
    private WebView myWebView;
    private Button mMoveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);     // JavaScript를 사용하는 사이트를 지원 - 구글,네이버 등
        myWebView.setWebViewClient(new WebViewClient()); // page navigation


        mAddressEdit = (EditText) findViewById(R.id.address_edit);
        mMoveButton = (Button) findViewById(R.id.move_button);

        mMoveButton.setOnClickListener(this); // '이동' 버튼을 눌렀을 시 line 29로 이동

        mAddressEdit.setOnEditorActionListener(this);


        myWebView.loadUrl("http://www.naver.com"); // 앱 시작 시 처음으로 나오는 URL
    }

    @Override
    public void onClick(View v) {
        String address = mAddressEdit.getText().toString();
        if (address.startsWith("http://") == false) {
            address = "http://" + address;
        }
        mAddressEdit.setText(address);
        myWebView.loadUrl(address);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // 뒤로 가기 키를 누르면 뒷 페이지로 이동하는 기능
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { // 엔터 누를 시 이동 버튼 눌리게
       if (actionId == EditorInfo.IME_ACTION_SEARCH) { // xml코드의 EditText에 추가한 ActionSearch 돋보기 버튼을 누를 시
           mMoveButton.callOnClick(); // 이동 버튼을 직접 누른 효과 주기
           return true;
       }
        return false;
    }
}
