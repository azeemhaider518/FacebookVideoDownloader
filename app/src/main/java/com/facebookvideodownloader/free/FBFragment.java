package com.facebookvideodownloader.free;

import java.io.File;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebookvideodownloader.free.R;
import com.google.android.gms.ads.*;

/**
 * Created by harishannam on 15/02/15.
 */
public class FBFragment extends Fragment {

	private static final String target_url = "https://m.facebook.com/";
	private static WebView webView;
	final Context context = getActivity();
	View rootView;

InterstitialAd mInterstitialAd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fb_main, container, false);

		AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);



		webView = (WebView) rootView.findViewById(R.id.webview);


		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(this, "FBDownloader");

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				webView.loadUrl("javascript:(function() { "
						+ "var el = document.querySelectorAll('div[data-sigil]');"
						+ "for(var i=0;i<el.length; i++)"
						+ "{"
						+ "var sigil = el[i].dataset.sigil;"
						+ "if(sigil.indexOf('inlineVideo') > -1){"
						+ "delete el[i].dataset.sigil;"
						+ "var jsonData = JSON.parse(el[i].dataset.store);"
						+ "el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\");');"
						+ "}" + "}" + "})()");
				Log.e("WEBVIEWFIN", url);
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				webView.loadUrl("javascript:(function prepareVideo() { "
						+ "var el = document.querySelectorAll('div[data-sigil]');"
						+ "for(var i=0;i<el.length; i++)"
						+ "{"
						+ "var sigil = el[i].dataset.sigil;"
						+ "if(sigil.indexOf('inlineVideo') > -1){"
						+ "delete el[i].dataset.sigil;"
						+ "console.log(i);"
						+ "var jsonData = JSON.parse(el[i].dataset.store);"
						+ "el[i].setAttribute('onClick', 'FBDownloader.processVideo(\"'+jsonData['src']+'\",\"'+jsonData['videoID']+'\");');"
						+ "}" + "}" + "})()");
				webView.loadUrl("javascript:( window.onload=prepareVideo;"
						+ ")()");
			}
		});
		CookieSyncManager.createInstance(getActivity());
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		CookieSyncManager.getInstance().startSync();

		webView.loadUrl(target_url);

		webView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mInterstitialAd.isLoaded()){
					mInterstitialAd.show();
					loadAd();
				}else {
					loadAd();
				}
			}
		});

		return rootView;
	}

	private void loadAd() {

		mInterstitialAd = new InterstitialAd(getActivity());
		mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ad_unit_id));
		AdRequest adRequest1 = new AdRequest.Builder().build();
		mInterstitialAd.loadAd(adRequest1);

	}

	@JavascriptInterface
	public void processVideo(final String vidData, final String vidID) {



		Log.e("WEBVIEWJS", "RUN");
		Log.e("WEBVIEWJS", vidData);
		Bundle args = new Bundle();
		args.putString("vid_data", vidData);
		args.putString("vid_id", vidID);
		DownloadDialog dFragment = new DownloadDialog();
		dFragment.setArguments(args);
		dFragment.show(getFragmentManager(), "Do you want to...");
		
		
		// count downloads

	}



	public static boolean Back() {
		if (webView.canGoBack()) {
			webView.goBack();
			return true;
		} else
			return false;
	}
}