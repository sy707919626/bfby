package com.lulian.driver.utils.version;

import android.os.Environment;

import java.io.File;

public class FileHelper {

	public static String getDownloadApkCachePath() {

		String appCachePath = null;


		if (checkSDCard()) {
			appCachePath = Environment.getExternalStorageDirectory() + "/AlongVersionPath/" ;
		} else {
			appCachePath = Environment.getDataDirectory().getPath() + "/AlongVersionPath/" ;
		}
		File file = new File(appCachePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return appCachePath;
	}



	/**
	 *
	 */
	public static boolean checkSDCard() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

		return sdCardExist;

	}



}