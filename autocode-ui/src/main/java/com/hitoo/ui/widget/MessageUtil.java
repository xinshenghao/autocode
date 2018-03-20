package com.hitoo.ui.widget;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.hitoo.server.client.response.AbsResponseBean;

public class MessageUtil {

	public static void error(String errorInfor) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), "错误", errorInfor);
	}

	public static void error(Shell parent ,String errorInfor) {
		MessageDialog.openError(parent, "错误", errorInfor);
	}

	public static void error(AbsResponseBean response) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), "网络请求错误", response.getContent());
	}

	public static void infor(AbsResponseBean response) {
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), "信息", response.getContent());
	}

	public static void infor(String infor) {
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), "信息", infor);
	}
	
	public static void infor(Shell parent, String infor) {
		MessageDialog.openInformation(parent, "信息", infor);
	}

	public static boolean confirm(String confirmInfor) {
		return MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "询问", confirmInfor);
	}

	
}
