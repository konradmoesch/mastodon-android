package org.joinmastodon.android.model;

import static android.provider.Settings.System.getString;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import org.joinmastodon.android.R;
import org.joinmastodon.android.api.RequiredField;
import org.joinmastodon.android.ui.utils.UiUtils;

import androidx.annotation.StringRes;

public class PushNotification extends BaseModel{
	public String accessToken;
	public String preferredLocale;
	public long notificationId;
	@RequiredField
	public Type notificationType;
	@RequiredField
	public String icon;
	@RequiredField
	public String title;
	@RequiredField
	public String body;

	public static PushNotification fromNotification(Context context, Notification notification){
		PushNotification pushNotification = new PushNotification();
		pushNotification.notificationType = switch(notification.type) {
			case FOLLOW -> PushNotification.Type.FOLLOW;
			case MENTION -> PushNotification.Type.MENTION;
			case REBLOG -> PushNotification.Type.REBLOG;
			case FAVORITE -> PushNotification.Type.FAVORITE;
			case POLL -> PushNotification.Type.POLL;
			//Follow request, and reactions are not supported by the API
			default -> throw new IllegalStateException("Unexpected value: "+notification.type);
		};

		String notificationTitle = context.getString(switch(notification.type){
			case FOLLOW -> R.string.user_followed_you;
			case REBLOG -> R.string.notification_boosted;
			case FAVORITE -> R.string.user_favorited;
			case POLL -> R.string.poll_ended;
			case MENTION -> R.string.user_mentioned_you;
			default -> throw new IllegalStateException("Unexpected value: "+notification.type);
		});

		pushNotification.title = String.format(notificationTitle, notification.status.account.displayName);
		pushNotification.icon = notification.account.avatarStatic;
		pushNotification.body = notification.status.getStrippedText();
		return pushNotification;
	}

    @Override
	public String toString(){
		return "PushNotification{"+
				"accessToken='"+accessToken+'\''+
				", preferredLocale='"+preferredLocale+'\''+
				", notificationId="+notificationId+
				", notificationType="+notificationType+
				", icon='"+icon+'\''+
				", title='"+title+'\''+
				", body='"+body+'\''+
				'}';
	}

	public enum Type{
		@SerializedName("favourite")
		FAVORITE(R.string.notification_type_favorite),
		@SerializedName("mention")
		MENTION(R.string.notification_type_mention),
		@SerializedName("reblog")
		REBLOG(R.string.notification_type_reblog),
		@SerializedName("follow")
		FOLLOW(R.string.notification_type_follow),
		@SerializedName("poll")
		POLL(R.string.notification_type_poll);

		@StringRes
		public final int localizedName;

		Type(int localizedName){
			this.localizedName=localizedName;
		}
	}
}
