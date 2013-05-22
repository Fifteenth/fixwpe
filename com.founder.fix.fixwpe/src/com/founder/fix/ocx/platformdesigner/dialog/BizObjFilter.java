package com.founder.fix.ocx.platformdesigner.dialog;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;


public class BizObjFilter extends ViewerFilter {

	private String searchString;

	public void setSearchText(String s) {
		// Search must be a substring of the existing value
		this.searchString = s + ".*";
	}
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		// TODO Auto-generated method stub
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		BizobjEntity p = (BizobjEntity) element;
		if (p.getId().matches(searchString)
				|| p.getId().matches(searchString.toUpperCase())) {
			return true;
		}
		if (p.getName().matches(searchString)
				|| p.getName().matches(searchString.toUpperCase())) {
			return true;
		}
		return false;
	}

}
