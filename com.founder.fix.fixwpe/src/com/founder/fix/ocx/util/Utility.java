package com.founder.fix.ocx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.founder.fix.apputil.to.bizobj.BizObjTo;
import com.founder.fix.apputil.to.bizobj.ObjColumnTo;
import com.founder.fix.apputil.util.BizObjectUtil;
import com.founder.fix.apputil.util.JSONUtil;
import com.founder.fix.fixwpe.Activator;
import com.founder.fix.ocx.platformdesigner.interfaces.FixStudioInterface;



public class Utility 
{
	/**取资源图片
	 * @param imgname
	 * @return
	 */
	public static Image getImage(String imgname)
	{
		ImageDescriptor descriptor = getImageDescriptor(imgname); 
		org.eclipse.swt.graphics.Image img = descriptor.createImage();
		return img;
	}
	
	/**取图片ImageDescriptor
	 * @param imgname
	 * @return
	 */
	public static ImageDescriptor getImageDescriptor(String imgname)
	{
		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(FormConst.PLUGINID,imgname); 
		
		return descriptor;
	}
	
	

	/**
	 * 获取eclipse的workspace真实目录
	 */
	public static String getWorkspaceRealPath() {
		String workspacePath = Activator.getDefault().getBundle().getLocation();
		workspacePath = workspacePath.substring(workspacePath.indexOf("/") + 1, workspacePath.lastIndexOf("/"));
		workspacePath = workspacePath.substring(0, workspacePath.lastIndexOf("/") + 1);
		return workspacePath;
	}
	
	/**
	 * 获取eclipse工程真实目录
	 */
	public static String getProjectRealPath() {
		String sysPath = Activator.getDefault().getBundle().getLocation();
		sysPath = sysPath.substring(sysPath.indexOf("/") + 1);
		return sysPath;
	}
	
	
	/**
	 * 根据ID取业务对象
	 * @param id
	 * @return
	 */
	public static BizObjTo getBizObjToFromId(String id)
	{
		String fileName = FixStudioInterface.getBizobjRealPathFromId(id);
		return getBizObjToFromPath(fileName);
	}
	
	
	
	/**根据路径取业务对象
	 * @param path
	 * @return
	 */
	public static BizObjTo getBizObjToFromPath(String path)
	{
		try
		{
			String fileName = path;
			File file = new File(fileName);
			InputStream ins = new FileInputStream(file);
			BizObjTo bizObjTo = BizObjectUtil.readDataObjInfo(ins);
			return bizObjTo;
		}
		catch(Exception e)
		{
			//
		}
		return null;
	}
	
	
	/**根据Html路径取其对应的业务对象
	 * @param path
	 * @return
	 */
	public static BizObjTo getBizObjToFromHtmlPath(String htmlpath)
	{

		String fileName = FixStudioInterface.getBizobjRealPath(htmlpath);
		return getBizObjToFromPath(fileName);
		
	}
	
	/**通过BizObjTo转成map
	 * @param bizObjTo
	 * @return
	 */
	public static Map<String,Object> getBizObjToMap(BizObjTo bizObjTo)
	{
		Map<String,Object> bizInfo = new HashMap<String,Object>();
		Map<String,Object> bizObj = new HashMap<String,Object>();
		bizObj.put(DHtmlConst.FORM_BizObjId, bizObjTo.getId());
		bizObj.put(DHtmlConst.FORM_BizObjName, bizObjTo.getName());
		List<Object> colsInfo = new ArrayList<Object>();
		List<ObjColumnTo> listCol = bizObjTo.getColumns();
		for ( int i = 0; i < listCol.size(); i++ )
		{
			ObjColumnTo col = listCol.get(i);
			colsInfo.add(Utility.getColumnToMap(col));
		}
		bizObj.put("columns", colsInfo);
		
		bizInfo.put("master", bizObj);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for ( int i = 0; i < bizObjTo.getDataRelations().size(); i++ )
		{
			BizObjTo bizTo = null;
			try 
			{
				bizTo = bizObjTo.getDataRelations().get(i).getRelObj();
			} 
			catch (Exception e) 
			{
				//e.printStackTrace();
			}
			if ( bizTo != null )
			{
				bizObj = new HashMap<String,Object>();
				bizObj.put(DHtmlConst.FORM_BizObjId, bizTo.getId());
				bizObj.put(DHtmlConst.FORM_BizObjName, bizTo.getName());
				
				List<Object> cols = new ArrayList<Object>();
				List<ObjColumnTo> subCol = bizTo.getColumns();
				for ( int j = 0; j < subCol.size(); j++ )
				{
					ObjColumnTo col = subCol.get(j);
					cols.add(Utility.getColumnToMap(col));
				}
				bizObj.put("columns", cols);
				
				list.add(bizObj);
			}
		}
		bizInfo.put("detail", list);
		return bizInfo;
	}
	
	/**
	 * 取业务对象TO，含下级关系
	 * @param bizObjTo
	 * @return
	 */
	public static String getBizObjToJson(BizObjTo bizObjTo)
	{
		return JSONUtil.parseObject2JSON(getBizObjToMap(bizObjTo));
	}
	
	/**
	 * 取列信息到MAP
	 * @param row
	 * @return
	 */
	public static Map<String,String> getColumnToMap(ObjColumnTo row)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(DHtmlConst.FORM_BizObjId, row.getParentObj().getId());
		map.put(DHtmlConst.FORM_FieldId, row.getId());
		map.put(DHtmlConst.FORM_Alias, row.getId());
		map.put(DHtmlConst.FORM_FieldName, row.getName());
		String ispk = row.getIsprimary();
		if ( ispk == null ) ispk = "false";
		if ( ispk.equals("1") ) ispk = "true";
		if ( ispk.equals("0") ) ispk = "false";
		map.put("isPK",ispk.toLowerCase());
		map.put("datatype",row.getDataType());
		map.put("description",row.getDescription());
		map.put("sensitive", row.getIssensitive());
		//map.put("sensitive", row.getIssensitive());
		map.put("category", row.getCategoryvalue());
		map.put("codetable", row.getCodetable());
		//
		map.put("needSubmit", "true");
		return map;
	}
	
	/**
	 * 取列信息到json
	 * @param row
	 * @return
	 */
	public static String getColumnToJson(ObjColumnTo row)
	{
		return JSONUtil.parseObject2JSON(getColumnToMap(row));
	}
	
	/**
	 * 初始化表单设计器临时目录
	 */
	public static void initFormDesignTemp()
	{
		String tmp = getFormDesignTemp();
		//创建表单临时目录
		File f = new File(tmp);
		if ( !f.exists() )
			f.mkdirs();
		//创建图片临时目录
		f = new File(tmp+"/img/");
		if ( !f.exists() )
			f.mkdirs();
		//创建HTML临时目录
		f = new File(tmp+"/htm/");
		if ( !f.exists() )
			f.mkdirs();
		//创建tmp临时目录
		f = new File(tmp+"/tmp/");
		if ( !f.exists() )
			f.mkdirs();
	}
	/**取表单临时设计目录
	 * @return
	 */
	public static String getFormDesignTemp()
	{
		String tmp = FixUtil.getEclipseRealRootPath() + "temp/form/";
		return tmp;
	}
	
}
