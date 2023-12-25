package org.joinmastodon.android;

import static org.unifiedpush.android.connector.UnifiedPush.registerAppWithDialog;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import org.joinmastodon.android.api.PushSubscriptionManager;
import org.unifiedpush.android.connector.RegistrationDialogContent;

import java.util.ArrayList;

import me.grishka.appkit.imageloader.ImageCache;
import me.grishka.appkit.utils.NetworkUtils;
import me.grishka.appkit.utils.V;

public class MastodonApp extends Application{

	@SuppressLint("StaticFieldLeak") // it's not a leak
	public static Context context;

	@Override
	public void onCreate(){
		super.onCreate();
		context=getApplicationContext();
		V.setApplicationContext(context);
		ImageCache.Parameters params=new ImageCache.Parameters();
		params.diskCacheSize=100*1024*1024;
		params.maxMemoryCacheSize=Integer.MAX_VALUE;
		ImageCache.setParams(params);
		NetworkUtils.setUserAgent("MastodonAndroid/"+BuildConfig.VERSION_NAME);
		GlobalUserPreferences.load();
		if(BuildConfig.DEBUG){
			WebView.setWebContentsDebuggingEnabled(true);
		}
	}
}
