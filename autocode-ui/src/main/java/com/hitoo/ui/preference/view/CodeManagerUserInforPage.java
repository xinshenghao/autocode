package com.hitoo.ui.preference.view;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;

import com.hitoo.ui.preference.PreferenceStoreHelper;
import com.hitoo.ui.preference.PreferenceStoreKey;

public class CodeManagerUserInforPage extends FieldEditorPreferencePage {
	
	public CodeManagerUserInforPage() {
		super(GRID);
		setPreferenceStore(PreferenceStoreHelper.getPreferenceStore());
		setTitle("用户名密码设置");
		setDescription("设置连接代码管理服务器的用户名和密码");
	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceStoreKey.CODEMANAGER_USERNAME, "用户名",  getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceStoreKey.CODEMANAGER_PASSWD, "密码",  getFieldEditorParent()));
	}

	
}