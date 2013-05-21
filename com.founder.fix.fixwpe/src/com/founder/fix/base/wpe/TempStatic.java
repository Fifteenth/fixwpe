package com.founder.fix.base.wpe;

import java.util.ArrayList;

public class TempStatic {
	
	
	public static String staticCategory = "founderfix1";
	public static String categories[] = {"展现类组件","功能类组件","平台组件","弹出或下拉"}; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$

	public static ArrayList<String> categoriesList = new ArrayList<String>();
			
			
	public static ArrayList<String> getCategoriesList(){
		
		for(int i=0;i<categories.length;i++){
			categoriesList.add(categories[i]);
		}
		return categoriesList;
	}
}
