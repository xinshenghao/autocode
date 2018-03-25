package com.hitoo.ui.preference;

import java.io.IOException;

import org.eclipse.jface.preference.PreferenceStore;

import com.hitoo.config.FilePathBean;
import com.hitoo.general.utils.PropertiesUtil;

public class PreferenceStoreHelper {
	
	private static String preferenceStoreFilePath = FilePathBean.getPreferenceStore();
	private static PreferenceStore preferenceStore = null;
	/**
	 * 是否发生了改变
	 */
	private static boolean change = false;
	
	public static PreferenceStore getPreferenceStore() {
		if(null == preferenceStore) {
			preferenceStore = new PreferenceStore(preferenceStoreFilePath);
			try {
				preferenceStore.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return preferenceStore;
	}
	
	public static void reLoad() {
		try {
			preferenceStore.load();
			change = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void change() {
		change = true;
	}
	
	public static boolean isChange() {
		return change;
	}
}
