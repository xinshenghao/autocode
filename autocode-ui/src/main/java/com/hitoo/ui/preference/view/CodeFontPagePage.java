package com.hitoo.ui.preference.view;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;

import com.hitoo.ui.preference.PreferenceStoreHelper;
import com.hitoo.ui.preference.PreferenceStoreKey;

import org.eclipse.jface.preference.ColorFieldEditor;

public class CodeFontPagePage extends FieldEditorPreferencePage {

	/**
	 * Create the preference page.
	 */
	public CodeFontPagePage() {
		super(GRID);
		setPreferenceStore(PreferenceStoreHelper.getPreferenceStore());
		setTitle("编辑区域字体设置");
		setDescription("设置编辑器的编辑区域的字体样式.");
	}

	/**
	 * Create contents of the preference page.
	 */
	@Override
	protected void createFieldEditors() {
		addField(new FontFieldEditor(PreferenceStoreKey.CODE_FONT, "字体设置", getFieldEditorParent()));
	}

	@Override
	public boolean performOk() {
		PreferenceStoreHelper.change();
		return super.performOk();
	}
}
