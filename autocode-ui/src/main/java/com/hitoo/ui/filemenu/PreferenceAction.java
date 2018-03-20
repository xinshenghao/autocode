package com.hitoo.ui.filemenu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Display;

import com.hitoo.ui.preference.view.CodeFontPagePage;
import com.hitoo.ui.preference.view.CodeManagerUserInforPage;
import com.hitoo.ui.preference.view.JAVAEditorColorPage;
/**
 * 首选项Action
 * @author xsh
 *
 */
public class PreferenceAction extends Action {
	
	public PreferenceAction() {
		setText("首选项");
	}
	
	@Override
	public void run() {
		PreferenceManager manager = new PreferenceManager();
		//创建一个节点对象
		PreferenceNode codeManagerUserInfor = new PreferenceNode("codeManagerUserInfor", "代码管理", null, CodeManagerUserInforPage.class.getName());
		manager.addToRoot(codeManagerUserInfor);
		//Java编辑器颜色设置
		PreferenceNode javaEditorColor = new PreferenceNode("javaEditorColor", "颜色管理", null, JAVAEditorColorPage.class.getName());
		manager.addToRoot(javaEditorColor);
		//字体设置
		PreferenceNode codeFont = new PreferenceNode("codeFont", "字体设置", null, CodeFontPagePage.class.getName());
		manager.addToRoot(codeFont);
		//定义一个“首选项”对话框
		PreferenceDialog dlg = new PreferenceDialog(Display.getCurrent().getActiveShell(), manager);
		dlg.open();
	}
}
















