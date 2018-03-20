package com.hitoo.ui.projecttree.action;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import com.hitoo.ui.editor.EditorView;
import com.hitoo.ui.projecttree.model.FileNode;
import com.hitoo.ui.projecttree.model.FolderNode;
import com.hitoo.ui.start.AutoCode;

public class TreeItemDoubleClickListener implements IDoubleClickListener {
	
	private TreeViewer tree;
	
	
	public TreeItemDoubleClickListener(TreeViewer tree) {
		this.tree = tree;
	}



	@Override
	public void doubleClick(DoubleClickEvent e) {
		Object selected = getProjectTreeSelection();
		if(selected instanceof FolderNode) {
			FolderNode folderNode = (FolderNode) selected;
			if(folderNode.hasChildren()){  
				//获取展开状态  
				if(tree.getExpandedState(folderNode))  
					tree.collapseToLevel(folderNode, 1);  
				else   
					tree.expandToLevel(folderNode, 1);    
			}  
		}else {
			FileNode fileNode = (FileNode) selected;
			
			CTabFolder tabFolder = AutoCode.getTabFolder();
			
			CTabItem cTabItem = new CTabItem(tabFolder, SWT.NONE);
			cTabItem.setText(fileNode.getName());
			
			openFileByFileType(fileNode, tabFolder, cTabItem);
			
			tabFolder.setSelection(tabFolder.getItemCount()-1);
		}
	}
	
	/**
	 * 根据文件类型选择不同的文件编辑器
	 * @param fileNode
	 * @param tabFolder 
	 * @param tabItem
	 */
	private void openFileByFileType(FileNode fileNode, CTabFolder tabFolder, CTabItem tabItem) {
		// TODO Auto-generated method stub
		switch (fileNode.getFileType()) {
		case JAVA:
			EditorView javaCodeEditorView = new EditorView(tabFolder, SWT.NONE, fileNode.getPath(), fileNode.getFileType());
			//JavaCodeEditorView javaCodeEditorView = new JavaCodeEditorView(tabFolder, SWT.NONE, fileNode.getPath());
			tabItem.setControl(javaCodeEditorView);			
			break;
		default:
			EditorView textEditorViewer = new EditorView(tabFolder, SWT.NONE, fileNode.getPath(), fileNode.getFileType());
			tabItem.setControl(textEditorViewer);
			break;
		}
	}



	/**
	 * 获取选中的树的节点
	 * @return
	 */
	private Object getProjectTreeSelection() {
		IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
		if(selection.size() != 1) {
			return null;
		}
		return selection.getFirstElement();
	}

}
