package com.hitoo.ui.projecttree.mvc;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.hitoo.ui.projecttree.model.ProjectTreeElement;

public class ProjectTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parentElement) {
		return ((ProjectTreeElement)parentElement).getChildren().toArray();
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return ((List)inputElement).toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return ((ProjectTreeElement)element).hasChildren();
	}

	
	
}
