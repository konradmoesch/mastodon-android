package org.joinmastodon.android;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.joinmastodon.android.api.MastodonAPIController;
import org.joinmastodon.android.api.session.AccountSession;
import org.joinmastodon.android.api.session.AccountSessionManager;
import org.joinmastodon.android.model.Notification;
import org.joinmastodon.android.model.PaginatedResponse;
import org.unifiedpush.android.connector.MessagingReceiver;

public class UnifiedPushNotificationReceiver extends MessagingReceiver {
	private static final String TAG="UnifiedPushNotificationReceiver";
	public UnifiedPushNotificationReceiver() {
		super();
	}
	@Override
	public void onNewEndpoint(@NotNull Context context, @NotNull String endpoint, @NotNull String instance) {
		// Called when a new endpoint be used for sending push messages
		Log.d(TAG, "onNewEndpoint: New Endpoint " + endpoint + " for "+ instance);
		AccountSession account = AccountSessionManager.getInstance().getLastActiveAccount();
		if (account != null)
			account.getPushSubscriptionManager().registerAccountForPush(null);
	}

	@Override
	public void onRegistrationFailed(@NotNull Context context, @NotNull String instance) {
		// called when the registration is not possible, eg. no network
		Log.d(TAG, "onRegistrationFailed");
	}

	@Override
	public void onUnregistered(@NotNull Context context, @NotNull String instance) {
		// called when this application is unregistered from receiving push messages
		Log.d(TAG, "onUnregistered");
	}

	@Override
	public void onMessage(@NotNull Context context, @NotNull byte[] message, @NotNull String instance) {
		// Called when a new message is received. The message contains the full POST body of the push message
		Log.d(TAG, "onMessage");
	}
}