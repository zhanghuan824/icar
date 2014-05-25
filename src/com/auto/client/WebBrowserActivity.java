package com.auto.client;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 用来访问Web,通过Bundle的url属性得到网址，具体见PostLinkAdapter.getView的处理
 * @author zhanghuan
 *
 */
public class WebBrowserActivity extends SherlockActivity {

	private WebView _webView;
	final SherlockActivity _activity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.hide();
		
		setContentView(R.layout.activity_web_browser);
		_webView = (WebView)this.findViewById(R.id.web_browser);
		_webView.getSettings().setJavaScriptEnabled(true);
		Bundle bundle = this.getIntent().getExtras();
		String url = bundle.getString("url");
		
		_webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				_activity.setProgress(progress * 100);
				if(progress == 100)
					_activity.setTitle("");
			}
		});
		_webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
			
		});
		_webView.loadUrl(url);
	}

}
