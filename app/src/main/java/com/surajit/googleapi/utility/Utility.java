package com.surajit.googleapi.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

//import com.surajit.mtrack.R;

public class Utility {

	private static String TAG = "Utility";
	
	public static String getVersion(Context paramContext){
	    try
	    {
	      String str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
	      return str;
	    }
	    catch (NameNotFoundException localNameNotFoundException) {}
	    return null;
	 }
	
	public static String getApplicationName(Context paramContext){
	    try
	    {
	      ApplicationInfo ai = paramContext.getPackageManager()
	    		  .getApplicationInfo(paramContext.getPackageName(), 0);
	      String str = (String)paramContext.getPackageManager().getApplicationLabel(ai);
	      return str;
	    }
	    catch (NameNotFoundException localNameNotFoundException) {}
	    return null;
	 }
	
	public static int getVersionCode(Context context){
		int version = -1;
	    try {
	        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
	        version = pInfo.versionCode;
	    } catch (NameNotFoundException e1) {
	    	Log.e(TAG, "Package not found", e1);
	    }
	    return version;
	}
	
	public static String getVersionName(Context context,String packageName){
		String version = null;
	    try {
	        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
	        version = pInfo.versionName;
	    } catch (NameNotFoundException e1) {
	        Log.e(TAG, "Package "+packageName+" not found", e1);
	    }
	    return version;
	}
	
	public static String getDate(long milliSeconds) {
	    //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(milliSeconds);
	    if(milliSeconds==0)
	    	return "";
	    else
	    	return formatter.format(calendar.getTime());
	}

	
	public static String getDatetime(long milliSeconds) {
	    //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(milliSeconds);
	    if(milliSeconds==0)
	    	return "";
	    else
	    	return formatter.format(calendar.getTime());
	}
	
	public static String getDate(Date date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
	    return formatter.format(date);
	}
	
	public static long getDateInMillisec(){
		Date date = new Date();
		long dt = date.getTime();
		return dt;
	}
	
	public static String exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            // Reads stdout.
            // NOTE: You can write to stdin of the command using process.getOutputStream().
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static void runCommnad(String cmd){
		try {

	        Process p = Runtime.getRuntime().exec( "su" );
	        InputStream es = p.getErrorStream();
	        DataOutputStream os = new DataOutputStream(p.getOutputStream());

	        os.writeBytes(cmd + "\n");

	        os.writeBytes("exit\n");
	        os.flush();

	        int read;
	        byte[] buffer = new byte[4096];
	        String output = new String();
	        while ((read = es.read(buffer)) > 0) {
	            output += new String(buffer, 0, read);
	        }

	        p.waitFor();

	    } 
		catch (IOException e) {
	        Log.e(TAG, e.getMessage());
	        e.printStackTrace();
	    }
		catch (InterruptedException e) {
	        Log.v(TAG, e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public static boolean isFloat(String str){
		// get the count of .
		int dotCount = getCharCount(str,'.');
		String reg = "^[-+]?[0-9]*\\.?[0-9]+$";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		if(m.matches()){
			
			if(dotCount==0 || dotCount >1){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}
	}
	
	public static boolean isInteger(String str){
		String reg = "^[-+]?[0-9]+$";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		if(m.matches()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static int getCharCount(String str,char ch){
		int count=0;
		int length = str.length();
		int i=0;
		while(i < length){
			char c = str.charAt(i);
			if(c == ch){
				count++;
			}
		}
		return count;
	}
	
	public static boolean isEmpty(String str){
		boolean ret = false;
		if(str == null){
			ret = true;
		}
		else if(str.length() == 0){
			ret = true;
		}
		else{
			ret = false;
		}
		return ret;
	}
	
	public static void showKeyBoard(Context context){
		InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm != null){
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
		}
	}
	
	public static void hideKeyBoard(Context context){
		InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm != null){
			imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
		}
	}
	
	/*
	 * to get two decimal point output pass extent value 2
	 */
	public static double parseDouble(double value, int extent){ 
		double base = Math.pow(10, extent);
		double bal = value*base;
		long temp = Math.round(bal);
		bal = (double)temp/base;
		return bal;
	}
	
	public static String getDeviceName() {
		  String manufacturer = Build.MANUFACTURER;
		  String model = Build.MODEL;
		  if (model.startsWith(manufacturer)) {
		    return capitalize(model);
		  } else {
		    return capitalize(manufacturer) + " " + model;
		  }
		}


	private static String capitalize(String s) {
		  if (s == null || s.length() == 0) {
		    return "";
		  }
		  char first = s.charAt(0);
		  if (Character.isUpperCase(first)) {
		    return s;
		  } else {
		    return Character.toUpperCase(first) + s.substring(1);
		  }
	} 
	
	public static String getOSVersion(){
		String myVersion = Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
		int sdkVersion = Build.VERSION.SDK_INT; // e.g. sdkVersion := 8;
		return myVersion;
	}

	public static void composeEmail(Context context,String[] addresses, String subject,String msg) {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.parse("mailto:")); // only email apps should handle this
		intent.putExtra(Intent.EXTRA_EMAIL, addresses);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, msg);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			context.startActivity(intent);
		}
	}

	public static void lockScreenOrientation(Activity activity)
	{
		WindowManager windowManager =  (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		Configuration configuration = activity.getResources().getConfiguration();
		int rotation = windowManager.getDefaultDisplay().getRotation();

		// Search for the natural position of the device
		if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
				(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) ||
				configuration.orientation == Configuration.ORIENTATION_PORTRAIT &&
						(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270))
		{
			// Natural position is Landscape
			switch (rotation)
			{
				case Surface.ROTATION_0:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					break;
				case Surface.ROTATION_90:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
					break;
				case Surface.ROTATION_180:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
					break;
				case Surface.ROTATION_270:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					break;
			}
		}
		else
		{
			// Natural position is Portrait
			switch (rotation)
			{
				case Surface.ROTATION_0:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					break;
				case Surface.ROTATION_90:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					break;
				case Surface.ROTATION_180:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
					break;
				case Surface.ROTATION_270:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
					break;
			}
		}
	}

	public static void unlockScreenOrientation(Activity activity)
	{
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
	}

	public static void showOkDialog(Context context,String title,String msg){

		/**
		 * You can define default dialog theme for your app like below
		 * <style name="AppTheme" parent="Theme.AppCompat.Light">
		 <!-- your style -->
		 <item name="alertDialogTheme">@style/AppCompatAlertDialogStyle</item>
		 </style>
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("OK", null);
		builder.setCancelable(false);
		builder.show();
	}

	public static void showOkDialog(Context context,String title,Spanned msg){

		/**
		 * You can define default dialog theme for your app like below
		 * <style name="AppTheme" parent="Theme.AppCompat.Light">
		 <!-- your style -->
		 <item name="alertDialogTheme">@style/AppCompatAlertDialogStyle</item>
		 </style>
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("OK", null);
		builder.setCancelable(false);
		builder.show();
	}

	public static String getUniqueDeviceID(Context context){
		/*String deviceId = Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID);
		return deviceId;
		*/
		return getUniqueID(context);
	}

	private static String getUniqueID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String deviceId = deviceUuid.toString();
		return deviceId;
	}

	public static String getIMEI(Context context){
		TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String IMEINumber=telephonyManager.getDeviceId();
		if(TextUtils.isEmpty(IMEINumber)){
			return getUniqueDeviceID(context);
		}
		else{
			return IMEINumber;
		}
	}

	public static String getModel(){
		String brand = Build.BRAND;
		String model = Build.MODEL;
		String str = null;
		String m = model.toLowerCase();
		String b = brand.toLowerCase();
		if(m.contains(b)){
			str = model;
		}
		else{
			str = Character.toUpperCase(brand.charAt(0)) + brand.substring(1);
			str += " " + model;
		}
		return str;
	}

	/**
	 *
	 * @return ADB serial number
	 */
	public static String getSerialNo(){
		String serialNo=null;
		if(Build.VERSION.SDK_INT>8){
			serialNo=Build.SERIAL; //hardware serial no
		}
		else{
			try {
				Class localClass = Class.forName("android.os.SystemProperties");
				String str = (String) localClass.getMethod("get", new Class[]{String.class}).invoke(localClass, new Object[]{"ro.serialno"});
			}
			catch(Exception e){
				e.printStackTrace();
				serialNo = "";
			}
		}
		return serialNo;
	}

	public static String getNetworkOperatorName(Context context){
		String networkOpeatorName="";
		TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String networkName = telephonyManager.getNetworkOperatorName();
		String simOperatorName = telephonyManager.getSimOperatorName();
		if(networkName !=null)
			networkOpeatorName = networkName;
		else
			networkOpeatorName = simOperatorName;
		return networkOpeatorName;
	}

	public static void showMessageOKCancel(Context context,String message, DialogInterface.OnClickListener okListener) {
		new android.support.v7.app.AlertDialog.Builder(context)
				.setMessage(message)
				.setPositiveButton("OK", okListener)
				.setNegativeButton("Cancel", null)
				.create()
				.show();
	}

	public static boolean isPermissionGranted(Context context,String permission){
		if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
			return true;
		}
		else return false;
	}

	public static boolean requestPermission(Activity context,String permission,String rational,int requestCode){
		if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(context,permission)) {

				// Show an expanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				final String perm = permission;
				final Activity cntxt = context;
				final int rqstCod = requestCode;
				showMessageOKCancel(context,rational,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								ActivityCompat.requestPermissions(cntxt,
										new String[]{perm},
										rqstCod);
							}
						});
				return false;

			} else {

				// No explanation needed, we can request the permission.
				// PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
				ActivityCompat.requestPermissions(context,
						new String[]{permission},
						requestCode);
				return false;
			}

		}
		else{
			//usr has permission to read contact
			return true;
		}
	}

	public static Bitmap loadBitmapFromView(View v) {
		Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
		v.draw(c);
		return b;
	}

	public static void takeScreenshot(Activity context) {
		Date now = new Date();
		android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

		try {
			// image naming and path  to include sd card  appending name you choose for file
			String mPath = Environment.getExternalStorageDirectory().toString() + "/sdc" + ".jpg";

			// create bitmap screen capture
			View v1 = context.getWindow().getDecorView().getRootView();
			v1.setDrawingCacheEnabled(true);
			// this is the important code :)
			// Without it the view will have a dimension of 0,0 and the bitmap will be null
			v1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
			v1.layout(0, 0, v1.getMeasuredWidth(), v1.getMeasuredHeight());
			v1.buildDrawingCache(true);
			Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
			v1.setDrawingCacheEnabled(false);

			//Bitmap bitmap = loadBitmapFromView(v1);

			File imageFile = new File(mPath);
			FileOutputStream outputStream = new FileOutputStream(imageFile);
			int quality = 100;
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			// Several error may come out with file handling or OOM
			e.printStackTrace();
		}
	}

	private static void openScreenshot(Context context,File imageFile) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(imageFile);
		intent.setDataAndType(uri, "image/*");
		context.startActivity(intent);
	}
	
}


