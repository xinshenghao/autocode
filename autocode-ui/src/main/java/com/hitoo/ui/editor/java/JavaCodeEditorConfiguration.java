package com.hitoo.ui.editor.java;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class JavaCodeEditorConfiguration extends SourceViewerConfiguration {
	private String path;
	
	public JavaCodeEditorConfiguration(String path) {
		this.path = path;
	}
	
	/**
	 * 提供代码着色功能
	 */
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(new JavaCodeScanner(path));
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}

	//提供内容辅助功能
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		//创建内容助手对象
		ContentAssistant contentAssistant = new ContentAssistant();
		//设置提示的内容
		contentAssistant.setContentAssistProcessor(new JavaObjectContentAssistant(), IDocument.DEFAULT_CONTENT_TYPE);
		//设置自动激活提示
		contentAssistant.enableAutoActivation(true);
		//设置自动激活提示的时间为500毫秒
		contentAssistant.setAutoActivationDelay(500);
		return contentAssistant;
	}


	
	
}






















