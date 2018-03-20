package com.hitoo.ui.resource;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.Color;

import com.hitoo.ui.preference.PreferenceStoreHelper;
import com.hitoo.ui.preference.PreferenceStoreKey;

/**
 * 颜色资源管理类
 * @author xsh
 *
 */
public class ColorResourceManager {
	private ColorResourceManager() {}
	private static ColorRegistry colorRegistry;
	
	//获得颜色注册器对象
	public static ColorRegistry getColorRegistry() {
		if(null == colorRegistry) {
			colorRegistry = new ColorRegistry();
			initColor();
		}
		return colorRegistry;
	}

	//获得颜色注册器中的颜色对象
	public static Color getColor(String key) {
		if(PreferenceStoreHelper.isChange()) {
			PreferenceStoreHelper.reLoad();
			initColor();
		}
		Color color = getColorRegistry().get(key);
		return color;
	}

	private static void initColor() {
		PreferenceStore store = PreferenceStoreHelper.getPreferenceStore();
		System.out.println(store.getString(PreferenceStoreKey.JAVA_EDITOR_COLOR_OBJECT));
		colorRegistry.put(PreferenceStoreKey.JAVA_EDITOR_COLOR_KEYWORDS, StringConverter.asRGB(store.getString(PreferenceStoreKey.JAVA_EDITOR_COLOR_KEYWORDS)));
		colorRegistry.put(PreferenceStoreKey.JAVA_EDITOR_COLOR_COMMON, StringConverter.asRGB(store.getString(PreferenceStoreKey.JAVA_EDITOR_COLOR_COMMON)));
		colorRegistry.put(PreferenceStoreKey.JAVA_EDITOR_COLOR_STRING, StringConverter.asRGB(store.getString(PreferenceStoreKey.JAVA_EDITOR_COLOR_STRING)));
		colorRegistry.put(PreferenceStoreKey.JAVA_EDITOR_COLOR_OBJECT, StringConverter.asRGB(store.getString(PreferenceStoreKey.JAVA_EDITOR_COLOR_OBJECT)));
	}

}





























