package com.chendong.customizedwebview;

import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.Menu;

import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity {
	private WebView webView;
	private EditText urlEditText;
	private Context context;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		urlEditText = (EditText) findViewById(R.id.urlField);
		webView = (WebView) findViewById(R.id.webView);
		webView.setWebViewClient(new WebViewClient() {
			private Dialog urlLoadingDialog;
			private Dialog settingDialog;

			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {

				view.onPause();
				
				urlLoadingDialog = new AlertDialog.Builder(context)
						.setIcon(android.R.drawable.btn_star)
						.setTitle(R.string.urlLoadingTitle)
						// .setMessage(R.string.urlLoadingMessage)
						.setPositiveButton(
								R.string.main_info_resume,
								new android.content.DialogInterface.OnClickListener() {
									@SuppressLint("NewApi")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										view.onResume();
										view.loadUrl(url);
									}
								})
						.setNegativeButton(
								R.string.main_info_cancel,
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										urlLoadingDialog.cancel();
									}
								}).create();

				urlLoadingDialog.show();
				return true;
			}

			

			private void onEventComing(final WebView view,String title) {
				view.onPause();
				settingDialog = new AlertDialog.Builder(context)
						.setIcon(android.R.drawable.btn_star)
						 .setTitle(title)
						// .setMessage(R.string.main_wifi_message)
						.setPositiveButton(
								R.string.main_info_resume,
								new android.content.DialogInterface.OnClickListener() {
									@SuppressLint("NewApi")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										view.onResume();										
									}
								})
						.setNegativeButton(
								R.string.main_info_cancel,
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										settingDialog.cancel();
									}
								}).create();

				settingDialog.show();
			}

			@Override
			public void onFormResubmission(WebView view, Message dontResend,
					Message resend) {
				onEventComing(view, getString(R.string.formResubmission));
			}
			
			@Override
			public void onLoadResource(WebView view, String url) {
				onEventComing(view,getString(R.string.loadResouce));
			}
			
			public void onPageFinished(final WebView view, String url) {
				onEventComing(view,getString(R.string.pageFinishedTitle));
			}
			
			@Override
			 public void onPageStarted(WebView view, String url, Bitmap
			 favicon) {
				onEventComing(view,getString(R.string.pageStartedTitle));
			 }
			
			@Override
			public void onReceivedHttpAuthRequest(WebView view,
					HttpAuthHandler handler, String host, String realm) {
				onEventComing(view,getString(R.string.receivedHttpAuthRequest));
			}
			
			@Override
			public void onReceivedLoginRequest(WebView view, String realm,
					String account, String args) {
				onEventComing(view,getString(R.string.receivedLoginRequest));
			}
			
			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				onEventComing(view,getString(R.string.receivedLoginRequest));
			}
			
			@Override
			public void onScaleChanged(WebView view, float oldScale,
					float newScale) {
				onEventComing(view,getString(R.string.scaleChanged));
			}
			
			 
			
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				onEventComing(view,getString(R.string.receiveError));
				System.out.println("errorCode:" + errorCode);
				System.out.println("failingUrl:" + failingUrl);
				System.out.println("description:" + description);
			}
			
			
			
		});
		Button openUrl = (Button) findViewById(R.id.goButton);
		webView.getSettings().setJavaScriptEnabled(true);
		openUrl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = urlEditText.getText().toString();
				//TODO for test
//				url = "http://www.baidu.com";
 				if (validateUrl(url)) {
					System.out.println(url);
					webView.loadUrl(url);
				}
			}

			private boolean validateUrl(String url) {
				return true;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
