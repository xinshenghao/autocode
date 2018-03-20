package com.hitoo.ui.editor.java;

import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;

import com.hitoo.ui.preference.PreferenceStoreHelper;
import com.hitoo.ui.preference.PreferenceStoreKey;
import com.hitoo.ui.start.AutoCode;

public class JavaSourceViewer extends SourceViewer {

	public JavaSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
		super(parent, ruler, styles);
		initFont();
	}

	public void initFont() {
		if(PreferenceStoreHelper.isChange()) {
			PreferenceStoreHelper.reLoad();
		}
		System.out.println(PreferenceStoreHelper.getPreferenceStore().getString(PreferenceStoreKey.CODE_FONT));
		FontData defaultFont = new FontData("Courier New",14,SWT.NORMAL);
		//如果从首选项读出的字体有异常，使用默认字体
		FontData data = null;
		data = StringConverter.asFontData(PreferenceStoreHelper.getPreferenceStore().getString(PreferenceStoreKey.CODE_FONT), defaultFont);
		/*try {
			data = StringConverter.asFontData(PreferenceStoreHelper.getPreferenceStore().getString(PreferenceStoreKey.CODE_FONT));
			
		} catch (Exception e) {
			MessageDialog.openError(AutoCode.getApp().getShell(), "错误", "没有设置的字体，使用默认值");
			data = defaultFont;
		}*/
		//创建字体
		Font font = new Font(AutoCode.getApp().getShell().getDisplay(), data);
		this.getTextWidget().setFont(font);
	}

}
