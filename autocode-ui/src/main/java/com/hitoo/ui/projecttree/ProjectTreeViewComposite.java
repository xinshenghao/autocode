package com.hitoo.ui.projecttree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.ui.projecttree.action.ReflushFileTreeAction;
import com.hitoo.ui.projecttree.action.TreeItemDoubleClickListener;
import com.hitoo.ui.projecttree.model.FolderNode;
import com.hitoo.ui.projecttree.model.ProjectTreeElement;
import com.hitoo.ui.projecttree.mvc.ProjectTreeContentProvider;
import com.hitoo.ui.projecttree.mvc.ProjectTreeLabelProvider;
import com.hitoo.ui.start.ApplicationContextHelper;

public class ProjectTreeViewComposite extends Composite {
	
	private CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
	private TreeViewDataHelper treeViewDataHelper = new TreeViewDataHelper();
	
	private TreeViewer tree;
	private List<ProjectTreeElement> treeData = null;
	private FolderNode root = null;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ProjectTreeViewComposite(Composite parent, int style) {
		super(parent, style);
		
		initView(parent);
		
		addRightMenu();

	}
	/**
	 * 增加右键菜单
	 */
	private void addRightMenu() {
		MenuManager manager = new MenuManager();
		manager.setRemoveAllWhenShown(true);
		
		manager.addMenuListener(new IMenuListener() {
			
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				Object selected = getProjectTreeSelection();
				if(selected!= null) {
					//manager.add(new MvnRunProjectAction());
					manager.add(new Separator());
					manager.add(new ReflushFileTreeAction());
				}
			}
		});
		tree.getControl().setMenu(manager.createContextMenu(tree.getControl()));
	}
	/**
	 * 初始化项目树
	 * @param parent
	 */
	private void initView(Composite parent) {
		this.setLayout(new FillLayout());
		
		//获取项目文件夹的数据
		String path = commonParameter.getWorkSpace();
		root = new FolderNode("根目录", path);
		root = treeViewDataHelper.getFileTreeData(path, root, true);

		tree = new TreeViewer(this);
		tree.setContentProvider(new ProjectTreeContentProvider());//内容提供器
		tree.setLabelProvider(new ProjectTreeLabelProvider());//设置标签提供器
		treeData = new ArrayList<>();
		treeData.add(root);
		tree.setInput(treeData);
		
		tree.addDoubleClickListener(new TreeItemDoubleClickListener(tree));
	}
	/**
	 * 刷新文件树
	 */
	public void reflushTreeView() {
		treeData.remove(0);
		
		String path = commonParameter.getWorkSpace();
		root = new FolderNode("根目录", path);
		root = treeViewDataHelper.getFileTreeData(path, root, true);
		
		treeData.add(0, root);
		
		tree.refresh();
	}

	@Override
	protected void checkSubclass() {
		
	}
	/**
	 * 
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
