package com.hitoo.ui.editor;

import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.hitoo.ui.editor.java.JavaCodeEditorConfiguration;
import com.hitoo.ui.editor.java.JavaSourceViewer;
import com.hitoo.ui.projecttree.model.FileType;
public class EditorView extends Composite{
	private CodeDocument document ;
	private JavaSourceViewer sourceViewer;
	
	private String path;
	private FileType type;


	public EditorView(Composite parent, int style, String path, FileType type) {
		super(parent, style);
		this.path = path;
		this.type = type;
		initView();
	}
	/**
	 * 初始化内容
	 */
	private void initView() {
		this.setLayout(new FillLayout());
		//初始化文档对象
		document = new CodeDocument(path);
		document.open();
		
		//初始化文档内容显示对象
		sourceViewer = new JavaSourceViewer(this, new VerticalRuler(10), SWT.V_SCROLL|SWT.H_SCROLL);
		
		IDocumentPartitioner partitioner = new FastPartitioner(new RuleBasedPartitionScanner(), new String[0]);
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		sourceViewer.setDocument(document);
		//初始化文档的配置对象
		SourceViewerConfiguration configuration = getConfiguration(type);
		if(null != configuration) {
			sourceViewer.configure(configuration);
		}
	}

	private SourceViewerConfiguration getConfiguration(FileType fileType) {
		if(fileType.equals(FileType.JAVA)) {
			return new JavaCodeEditorConfiguration(path);
		}
		return null;
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
