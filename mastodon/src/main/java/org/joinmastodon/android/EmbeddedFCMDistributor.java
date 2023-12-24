package org.joinmastodon.android;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.unifiedpush.android.embedded_fcm_distributor.EmbeddedDistributorReceiver;

public class EmbeddedFCMDistributor extends EmbeddedDistributorReceiver {
	@NonNull
	@Override
	public String getEndpoint(@NonNull Context context, @NonNull String token, @NonNull String instance){
		String url = "https://app.joinmastodon.org/relay-to/fcm/" + token + "/" + instance;
		Log.d("EmbeddedFCMDistributor", "getEndpoint: " + token + " " + instance + url);
		return "https://app.joinmastodon.org/relay-to/fcm/" + token + "/" + instance;
	}
}
