package com.surajit.googleapi.utility;
	
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

public class NetworkUtility {
     
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
     
     
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
 
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
             
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        } 
        return TYPE_NOT_CONNECTED;
    }
    
    public static boolean isNetworkAvailable(Context context) {
        boolean isMobile = false, isWifi = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(Build.VERSION.SDK_INT<21) {
            NetworkInfo[] infoAvailableNetworks = cm.getAllNetworkInfo();
            if (infoAvailableNetworks != null) {
                for (NetworkInfo network : infoAvailableNetworks) {

                    if (network.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (network.isConnected() && network.isAvailable())
                            isWifi = true;
                    }
                    if (network.getType() == ConnectivityManager.TYPE_MOBILE) {
                        if (network.isConnected() && network.isAvailable())
                            isMobile = true;
                    }
                }
            }
        }
        else{
            Network[] availableNetworks = cm.getAllNetworks();
            if (availableNetworks != null) {
                for (Network network : availableNetworks) {
                    NetworkInfo networkInfo = cm.getNetworkInfo(network);
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (networkInfo.isConnected() && networkInfo.isAvailable())
                            isWifi = true;
                    }
                    if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        if (networkInfo.isConnected() && networkInfo.isAvailable())
                            isMobile = true;
                    }
                }
            }
        }

        return isMobile || isWifi;
    }
     
    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtility.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtility.TYPE_WIFI) 
        {
        	status = "WIFI Status Changed";
        	
        } 
        else if (conn == NetworkUtility.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } 
        else if (conn == NetworkUtility.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}


