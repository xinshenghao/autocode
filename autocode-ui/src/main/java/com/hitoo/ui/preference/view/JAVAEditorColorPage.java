package com.hitoo.ui.preference.view;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;

import com.hitoo.ui.preference.PreferenceStoreHelper;
import com.hitoo.ui.preference.PreferenceStoreKey;

import org.eclipse.jface.preference.ColorFieldEditor;

public class JAVAEditorColorPage extends FieldEditorPreferencePage {

	/**
	 * Create the preference page.
	 */
	public JAVAEditorColorPage() {
		super(GRID);
		setPreferenceStore(PreferenceStoreHelper.getPreferenceStore());
		setTitle("编辑器字体颜色设置");
		setDescription("设置Java编辑器的字体颜色.");
	}

	/**
	 * Create contents of the preference page.
	 */
	@Override
	protected void createFieldEditors() {
		addField(new ColorFieldEditor(PreferenceStoreKey.JAVA_EDITOR_COLOR_KEYWORDS, "关键字", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceStoreKey.JAVA_EDITOR_COLOR_STRING, "字符串", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceStoreKey.JAVA_EDITOR_COLOR_OBJECT, "内置对象", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceStoreKey.JAVA_EDITOR_COLOR_COMMON, "注释:", getFieldEditorParent()));
	}

	@Override
	public boolean performOk() {
		PreferenceStoreHelper.change();
		return super.performOk();
	}
	
	
}
