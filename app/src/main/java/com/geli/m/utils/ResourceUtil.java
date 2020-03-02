package com.geli.m.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.geli.m.app.GlobalData;


/**
 * 获取资源id
 */
public class ResourceUtil {

	public static int getLayoutResIDByName(Context context, String name) {
		return context.getResources().getIdentifier(name, "layout",
				context.getPackageName());
	}

	public static int getIdResIDByName(Context context, String name) {
		return context.getResources().getIdentifier(name, "id",
				context.getPackageName());
	}

	// ResourceUtil.getStringResIDByName(this, "app_name")
	public static int getStringResIDByName(Context context, String name) {
		return context.getResources().getIdentifier(name, "string",
				context.getPackageName());
	}

	public static int getColorResIDByName(Context context, String name) {
		return context.getResources().getIdentifier(name, "color",
				context.getPackageName());
	}

	public static int getDrawableResIDByName(Context context, String name) {
		return context.getResources().getIdentifier(name, "drawable",
				context.getPackageName());
	}

	public static int getMipmapResIDByName(Context context, String name) {
		return context.getResources().getIdentifier(name, "mipmap",
				context.getPackageName());
	}

	public static int getRawResIDByName(Context context, String name) {
		return context.getResources().getIdentifier(name, "raw",
				context.getPackageName());
	}

	/*-----------------------------------
	-----------------------------------*/

	public static String getStringFromResources(int resid) {
		return GlobalData.getInstance().getResources().getString(resid);
	}

	public static String getStringFromResources(int resid, Object... format) {
		return String.format(GlobalData.getInstance().getResources().getString(resid), format);
	}


	public static String getStringFromResources(Context context, int resid) {
		return context.getResources().getString(resid);
	}

	public static String getStringFromResources(Context context, int resid, Object... format) {
		return String.format(context.getResources().getString(resid), format);
	}


	public static int getColor(Context context, int colorId) {
		return ContextCompat.getColor(context, colorId);
	}

	public static int getColor(int colorId) {
		return ContextCompat.getColor(GlobalData.getInstance(), colorId);
	}
}
