package com.johnnyangel.myblackbook.util;

public class MyApplicationProperties extends android.app.Application  {
	
	private static String ApplicationName;
	private static String ApplicationImageDirectory;
	private static String ApplicationDataDirectory;
	private static String[] ApplicationDirectoryList;
	private static String[] ApplicationDefaultBookImageNames;
	private static int[] ApplicationDrawableImageList;
	private static String ApplicationDefaultBookImage;
	
	
	public static String getApplicationName() {
		return ApplicationName;
	}
	public static void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}
	public static String getApplicationImageDirectory() {
		return ApplicationImageDirectory;
	}
	public static void setApplicationImageDirectory(
			String applicationImageDirectory) {
		ApplicationImageDirectory = applicationImageDirectory;
	}
	public static String getApplicationDataDirectory() {
		return ApplicationDataDirectory;
	}
	public static void setApplicationDataDirectory(String applicationDataDirectory) {
		ApplicationDataDirectory = applicationDataDirectory;
	}
	public static String[] getApplicationDirectoryList() {
		return ApplicationDirectoryList;
	}
	public static void setApplicationDirectoryList(
			String[] applicationDirectoryList) {
		ApplicationDirectoryList = applicationDirectoryList;
	}
	public static String[] getApplicationDefaultBookImageNames() {
		return ApplicationDefaultBookImageNames;
	}
	public static void setApplicationDefaultBookImageNames(
			String[] applicationDefaultBookImageNames) {
		ApplicationDefaultBookImageNames = applicationDefaultBookImageNames;
	}
	public static int[] getApplicationDrawableImageList() {
		return ApplicationDrawableImageList;
	}
	public static void setApplicationDrawableImageList(
			int[] applicationDrawableImageList) {
		ApplicationDrawableImageList = applicationDrawableImageList;
	}
	public static String getApplicationDefaultBookImage() {
		return ApplicationDefaultBookImage;
	}
	public static void setApplicationDefaultBookImage(
			String applicationDefaultBookImage) {
		ApplicationDefaultBookImage = applicationDefaultBookImage;
	}
	

}
