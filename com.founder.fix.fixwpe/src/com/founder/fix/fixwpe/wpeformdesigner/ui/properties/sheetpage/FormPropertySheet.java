package com.founder.fix.fixwpe.wpeformdesigner.ui.properties.sheetpage;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;


/**
 * 属性页的实现
 * @author BB
 *
 */
public class FormPropertySheet extends PropertySheetPage 
{
	
	/* 在此实现排序
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.PropertySheetPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) 
	{		
		PropertySheetSorter sorter = new PropertySheetSorter() 
		{
			public int compare(IPropertySheetEntry entryA,
					IPropertySheetEntry entryB) 
			{
				//return getCollator().compare(entryA.getDescription(),
				//		entryB.getDescription());
				
				//把排序语法去掉，放两个空字符，表示不进行排序 
				//只认属性的先进先出
				return getCollator().compare("","");
			}
		};
		this.setSorter(sorter);
		super.createControl(parent);
	}

	@Override
	public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) 
	{
//		if ((sourcepart instanceof MainEditor||sourcepart==null)
//				&& selection instanceof IStructuredSelection) 
//		super.selectionChanged(sourcepart, selection);
	}
	


}
