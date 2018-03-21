package com.hitoo.ui.table;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class SimpleTableNameContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object input) {
		return ((List)input).toArray();
	}
}
