package com.auto.client.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {

	private static NetworkInfo getActiveNetworkInfo(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork;
	}
	
	public static boolean isConnected(Context context) {
		NetworkInfo activeNetwork = NetworkStatus.getActiveNetworkInfo(context);
		return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
	}
	
	public static boolean isWifiConnected(Context context) {
		NetworkInfo activeNetwork = NetworkStatus.getActiveNetworkInfo(context);
		return (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI);
	}
	
	public static boolean isMobileNetworkConnected(Context context) {
		NetworkInfo activeNetwork = NetworkStatus.getActiveNetworkInfo(context);
		return (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE);
	}
}
