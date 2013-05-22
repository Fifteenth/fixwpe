/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * StudioInterface.java
 */
package com.founder.fix.ocx.platformdesigner.interfaces;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.part.ViewPart;

import com.founder.fix.apputil.Const;
import com.founder.fix.apputil.Const.ObjType;
import com.founder.fix.apputil.util.XmlUtil;
import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;
import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.ocx.platformdesigner.Entity.project.BaseEntity;
import com.founder.fix.ocx.platformdesigner.Entity.project.JavaEntity;
import com.founder.fix.ocx.platformdesigner.Entity.project.ModelEntity;
import com.founder.fix.ocx.platformdesigner.trees.ITreeElement;
import com.founder.fix.ocx.util.CacheUtil;
import com.founder.fix.ocx.util.FixUtil;

/**
 * [类名]<br>
 * StudioInterface.java<br>
 * <br>
 * [功能概要]<br>
 *
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-7-1 ver1.0 <br>
 * <br>
 *
 * @作者 wangzhiwei
 *
 */

public class FixStudioInterface {
	
	/**
	 * 递归用的全局变量
	 */
	private static List<JavaEntity> entities = null;
	
	private static List<String> globalList = null;
	
	
	public static List<BizobjEntity> _bizobjList;

	/**
	 * 构造器
	 */
	private FixStudioInterface() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	  * autoSetColumnsWidth(表格列自动填满表格)
	  * @Title: AutoSetColumnsWidth
	  * @Description: TODO
	  * @param @param tableViewer    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void autoSetColumnsWidth(TableViewer tableViewer) {

		int divideNum = tableViewer.getTable().getColumns().length;

		if (tableViewer.getTable().getSize().x != 0) {
			for (int i = 0; i < divideNum; i++) {
				tableViewer.getTable().getColumns()[i].setWidth(tableViewer
						.getTable().getSize().x / divideNum - 5);// 减5是为了不填满整个表格，不然会出现滚动条
				tableViewer.getTable().getColumns()[i].setWidth(tableViewer
						.getTable().getSize().x / divideNum - 5);
			}
		}
	}

	
	/**
	 * 判断运行系统是否是windows
	 * @return
	 */
	public static boolean isWindows() {
		String os = System.getProperty("os.name");

		if (os.startsWith("win") || os.startsWith("Win")) {
			return true;
		}

		return false;
	}
	
	/**
	 * 根据工程绝对路径判断这个工程是否是web工程
	 * @param projectRealPath
	 * @return
	 */
	public static boolean isWebProject(String projectRealPath) {
		boolean isWebPrject = false;
		try {
			File file = new File(projectRealPath);
			if(file.exists()) {
				File webroot = new File(projectRealPath + "/WebRoot");
				File isMainWeb = new File(projectRealPath + "/isMainWeb");
				if(webroot.exists() && isMainWeb.exists()) {
					isWebPrject = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FixLoger.error(e.toString());
		}
		
		return isWebPrject;
	}
	
	/**
	 * 编辑器是否能保存
	 */
//	public static boolean canSaveEditer() {
//		IViewPart viewPart = getViewPartFromViewId(FixPerspective.PROJECT_VIEW);
//		if(viewPart != null) {
//			return true;
//		} else {
//			if(MessageDialog.openConfirm(null, "提示", "打开平台透视图才能保存，是否继续？")) {
//				showPerspective("com.founder.fix.studio.FixPerspective");
//				return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * 打开平台透视图
	 */
	public static void showPerspective(String viewId) {
		try {
			PlatformUI.getWorkbench().showPerspective(viewId, 
				       PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有对话框的标题图片
	 * @return
	 */
	public static Image getDialogTitleImage() {
		return FixUtil.getImageFromURL(FixImageProvider.CREATE_FILE_DIALOG);
	}

	/**
	 * 获取数据源列表
	 * @return
	 */
//	public static List<Resource> getDataSource(ViewPart projectView) {
//		//搜索并读取context文件
//		return DataSourceOperation.findContextFile(projectView);
//	}
	
	/**
	 * 获取数据源列表
	 * @return
	 */
//	public static List<Resource> getDataSource(ITreeElement selectElement) {
//		//搜索并读取context文件
//		return DataSourceOperation.findContextFile(selectElement);
//	}
	
	/**
	 * 获取数据源列表
	 * @return
	 */
//	public static List<Resource> getDataSource(String fileRealPath) {
//		//搜索并读取context文件
//		return DataSourceOperation.findContextFileByRealPath(fileRealPath);
//	}
	
	/**
	 * 根据条件返回是表、视图、存储过程的列表
	 * @param resource
	 * @param type
	 * @return
	 */
//	public static List<String> getDataSource(Resource resource, int type) {
//		return DataSourceOperation.getDataSource(resource, type);
//	}
	
	/**
	 * 根据条件返回是表、视图的所有列的信息
	 * @param resource
	 * @param type
	 * @return
	 */
//	public static DatabaseEntity getDataSource(Resource resource, int type, String objName) {
//		return DataSourceOperation.getDataSource(resource, type, objName);
//	}
	
	/**
	 * 根据sql语句动态返回列信息
	 * @param resource
	 * @param sql
	 * @return
	 */
//	public static DatabaseEntity getColumnsFromSQL(Resource resource, String sql) {
//		return DataSourceOperation.getColumnsFromSQL(resource, sql);
//	}
	
	/**
	 * 根据表单html全路径获取其对应的业务对象文件的全路径
	 * @param htmlRealPath
	 * @return
	 */
	public static String getBizobjRealPath(String htmlRealPath) {
		if(htmlRealPath != null && !htmlRealPath.equals("")) {
			htmlRealPath = htmlRealPath.replace('\\', '/');
			
			String tmpPath = htmlRealPath.substring(0, htmlRealPath.lastIndexOf("/"));
			//业务对象名称
			String bizobjName = tmpPath.substring(tmpPath.lastIndexOf("/") + 1, tmpPath.length());
			
			//通过缓存找到该业务对象全路径
			List<BizobjEntity> bizobjList = _bizobjList;
			if(bizobjList != null && bizobjList.size() > 0) {
				for (Iterator iterator = bizobjList.iterator(); iterator
						.hasNext();) {
					BizobjEntity bizobjEntity = (BizobjEntity) iterator.next();
					if(bizobjEntity.getId().equals(bizobjName)) {
						return bizobjEntity.getRealFullPath();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 通过业务对象id找到其绝对路径
	 * @param bizobjId
	 * @return
	 */
	public static String getBizobjRealPathFromBizobjId(String bizobjId) {
		String bizobjRealPath = "";
		List<BizobjEntity> bizobjEntitys = (List<BizobjEntity>) CacheUtil.getCache(CacheUtil.BIZOBJ);
		if(bizobjEntitys != null) {
			for (Iterator iterator = bizobjEntitys.iterator(); iterator
					.hasNext();) {
				BizobjEntity bizobjEntity = (BizobjEntity) iterator.next();
				if(bizobjEntity.getId().equals(bizobjId)) {
					bizobjRealPath = bizobjEntity.getRealFullPath();
					break;
				}
			}
		}
		return bizobjRealPath.replace('\\', '/');
	}
	
	/**
	 * 根据业务对象id获取其文件的全路径
	 * @param bizobjId
	 * @return
	 */
	public static String getBizobjRealPathFromId(String bizobjId) {
		//调用缓存工具类
		return CacheUtil.getBizobjRealPathFromId(bizobjId);
	}
	
	/**
	 * 根据业务对象id获取其下所有的方法service配置
	 * @param bizobjId
	 * @return
	 */
	public static List<String> getBizobjServicesFromBizobjId(String bizobjId) {
		List<String> services = new ArrayList<String>();
		boolean isEntityBizobj = false;
		
		//调用缓存工具类
		String bizobjFilePath = CacheUtil.getBizobjRealPathFromId(bizobjId);
		//读取文件
		try {
			Document document = XmlUtil.read(new File(bizobjFilePath), "UTF-8");
			Element root = document.getRootElement();
			
			//判断是否实体对象
			if(root.attributeValue("objtype") != null 
					&& root.attributeValue("objtype").equals(ObjType.EntityTable.toString())) {
				isEntityBizobj = true;
			}
			
			//找出所有的service配置节点
			List<Element> elements = root.elements("service");
			if(elements != null && elements.size() > 0) {
				for (Iterator iterator = elements.iterator(); iterator
						.hasNext();) {
					Element element = (Element) iterator.next();
					services.add(element.attributeValue("id"));
				}
			}
		} catch (Exception e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
		
		//如果该业务对象是实体对象，获取它的的默认方法
		if(isEntityBizobj) {
			List<String> defaultServices = getBizobjVirtualServices();
			if(defaultServices != null && defaultServices.size() > 0) {
				for (Iterator iterator = defaultServices.iterator(); iterator
						.hasNext();) {
					String value = (String) iterator.next();
					if(!services.contains(value)) {
						services.add(value);
					}
				}
			}
		}
		
		return services;
	}
	
	/**
	 * 根据业务对象id获取其下所有的方法service配置(和上面的方法返回的对象不同)
	 * @param bizobjId
	 * @return
	 */
	public static List<BizobjEntity> getBizobjServicesFromBizobjId2(String bizobjId) {
		List<BizobjEntity> services = new ArrayList<BizobjEntity>();
		boolean isEntityBizobj = false;
		
		//调用缓存工具类
		String bizobjFilePath = CacheUtil.getBizobjRealPathFromId(bizobjId);
		//读取文件
		try {
			Document document = XmlUtil.read(new File(bizobjFilePath), "UTF-8");
			Element root = document.getRootElement();
			
			//判断是否实体对象
			if(root.attributeValue("objtype") != null 
					&& root.attributeValue("objtype").equals(ObjType.EntityTable.toString())) {
				isEntityBizobj = true;
			}
			
			//找出所有的service配置节点
			List<Element> elements = root.elements("service");
			if(elements != null && elements.size() > 0) {
				for (Iterator iterator = elements.iterator(); iterator
						.hasNext();) {
					Element element = (Element) iterator.next();
					
					BizobjEntity entity = new BizobjEntity();
					entity.setId(element.attributeValue("id"));
					entity.setName(element.attributeValue("name"));
					
					services.add(entity);
				}
			}
		} catch (Exception e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
		
		//如果该业务对象是实体对象，获取它的的默认方法
		if(isEntityBizobj) {
			List<String> defaultServices = getBizobjVirtualServices();
			if(defaultServices != null && defaultServices.size() > 0) {
				for (Iterator iterator = defaultServices.iterator(); iterator
						.hasNext();) {
					String value = (String) iterator.next();
					
					//判断是否有重复
					boolean bool = true;
					for (Iterator iterator2 = services.iterator(); iterator2
							.hasNext();) {
						BizobjEntity entity = (BizobjEntity) iterator2.next();
						if(entity.getId().equals(value)) {
							bool = false;
							break;
						}
					}
					
					if(bool) {
						BizobjEntity entity = new BizobjEntity();
						entity.setId(value);
						entity.setName(value);
						services.add(entity);
					}
				}
			}
		}
		
		return services;
	}
	
	/**
	 * 根据业务对象id获取其下所有的方法service配置（不包含需方法、仅仅是.biz文件中的方法）
	 * @param bizobjId
	 * @return
	 */
	public static List<BizobjEntity> getBizobjServicesFromBizobjId3(String bizobjId) {
		List<BizobjEntity> services = new ArrayList<BizobjEntity>();
		
		//调用缓存工具类
		String bizobjFilePath = CacheUtil.getBizobjRealPathFromId(bizobjId);
		//读取文件
		try {
			Document document = XmlUtil.read(new File(bizobjFilePath), "UTF-8");
			Element root = document.getRootElement();
			
			//找出所有的service配置节点
			List<Element> elements = root.elements("service");
			if(elements != null && elements.size() > 0) {
				for (Iterator iterator = elements.iterator(); iterator
						.hasNext();) {
					Element element = (Element) iterator.next();
					
					BizobjEntity entity = new BizobjEntity();
					entity.setId(element.attributeValue("id"));
					entity.setName(element.attributeValue("name"));
					entity.setRealFullPath("d:");
					
					services.add(entity);
				}
			}
		} catch (Exception e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
		
		return services;
	}
	
	/**
	 * 获取业务对象(实体对象)的默认方法
	 */
	public static List<String> getBizobjVirtualServices() {
		List<String> services = new ArrayList<String>();
		
		//获取默认方法
		for (int i = 0; i < Const.BizDefaultService.values().length; i++) {
			services.add(Const.BizDefaultService.values()[i].toString());
		}

		return services;
	}
	
	/**
	 * 根据业务对象是否是实体对象获取其默认方法
	 * @param bizobjId
	 * @return
	 */
	public static List<String> getBizobjVirtualServicesFromBizobjId(String bizobjId) {
		List<String> services = new ArrayList<String>();
		
		//调用缓存工具类
		String bizobjFilePath = CacheUtil.getBizobjRealPathFromId(bizobjId);
		//读取文件
		try {
			Document document = XmlUtil.read(new File(bizobjFilePath), "UTF-8");
			Element root = document.getRootElement();
			
			//判断是否实体对象
			if(root.attributeValue("objtype") != null 
					&& root.attributeValue("objtype").equals(ObjType.EntityTable.toString())) {
				//获取默认方法
				for (int i = 0; i < Const.BizDefaultService.values().length; i++) {
					services.add(Const.BizDefaultService.values()[i].toString());
				}
			}
		} catch (Exception e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}

		return services;
	}
	
	/**
	 * 通过业务对象特定的数据源获取该数据源的事务配置信息
	 * @param bizobjFilePath
	 * @param dataSource
	 * @return
	 */
//	public static TransactionManagerEntity getTransactionManagersFromBizobjFilePath(String bizobjFilePath,
//			String dataSource) {
//		TransactionManagerEntity transaction = null;
//		
//		//根据bizobjId找到其对应的web工程真实路径
//		String webProjectRealPath = getWebProjectRealPathFromBizobjFilePath(bizobjFilePath);
//		
//		//读取xml文件
//		String xmlPath = webProjectRealPath + "/conf/applicationContext_resources_fix.xml";
//		try {
//			Document document = XmlUtil.read(xmlPath);
//			Element root = document.getRootElement();
//			//获取所有的bean节点
//			List<Element> beans = root.elements("bean");
//			if(beans != null) {
//				for (Iterator iterator = beans.iterator(); iterator.hasNext();) {
//					Element element = (Element) iterator.next();
//					//找到该数据源对应的配置
//					if(element.attributeValue("id").equals(dataSource)) {
//						transaction = new TransactionManagerEntity();
//						transaction.setId(element.attributeValue("id"));
//						transaction.setClassName(element.attributeValue("class"));
//						
//						List<Element> properties = element.elements("property");
//						if(properties != null && properties.size() > 0) {
//							for (Iterator iterator2 = properties.iterator(); iterator2
//									.hasNext();) {
//								Element property = (Element) iterator2.next();
//								//找到transaction
//								if(property.attributeValue("name").equals("transactionName")) {
//									transaction.setTransactionName(property.attributeValue("value"));
//									break;
//								}
//							}
//						}
//						break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			FixLoger.error(e.toString());
//			e.printStackTrace();
//		}
//		return transaction;
//	}
	
	/**
	 * 为业务对象设计器获取事务配置信息
	 * @param bizobjId
	 * @return
	 */
//	public static List<TransactionManagerEntity> getTransactionManagersFromBizobjId(String bizobjId) {
//		List<TransactionManagerEntity> transactions = new ArrayList<TransactionManagerEntity>();
//		
//		//根据bizobjId找到其对应的web工程真实路径
//		String webProjectRealPath = getWebProjectRealPathFromBizobjId(bizobjId);
//		
//		//读取xml文件
//		String xmlPath = webProjectRealPath + "/conf/applicationContext_resources_fix.xml";
//		try {
//			String className = "org.springframework.jdbc.datasource.DataSourceTransactionManager";
//			Document document = XmlUtil.read(xmlPath);
//			Element root = document.getRootElement();
//			//获取所有的bean节点
//			List<Element> beans = root.elements("bean");
//			if(beans != null) {
//				for (Iterator iterator = beans.iterator(); iterator.hasNext();) {
//					Element element = (Element) iterator.next();
//					if(element.attributeValue("class").equals(className)) {
//						TransactionManagerEntity entity = new TransactionManagerEntity();
//						entity.setId(element.attributeValue("id"));
//						entity.setClassName(element.attributeValue("class"));
//						transactions.add(entity);
//					}
//				}
//			}
//		} catch (Exception e) {
//			FixLoger.error(e.toString());
//			e.printStackTrace();
//		}
//		return transactions;
//	}
	
	/**
	 * 为方法组件设计器获取事务配置信息
	 * @param serviceId
	 * @return
	 */
//	public static List<TransactionManagerEntity> getTransactionManagersFromServiceRealPath(String serviceRealPath) {
//		List<TransactionManagerEntity> transactions = new ArrayList<TransactionManagerEntity>();
//		
//		serviceRealPath = serviceRealPath.replace('\\', '/');
//		
//		//根据serviceId找到其对应的web工程真实路径
//		String webProjectRealPath = getWebOrLogicProjectRealPathFromFileRealPath(serviceRealPath);
//		
//		if (webProjectRealPath.endsWith("logic")) {
//			webProjectRealPath = getWebProjectRealPathFromLogicProjectName(webProjectRealPath
//					.substring(webProjectRealPath.lastIndexOf("/") + 1,	webProjectRealPath.length()));
//			webProjectRealPath = webProjectRealPath.replace('\\', '/');
//		}
//		
//		//读取xml文件
//		String xmlPath = webProjectRealPath + "/conf/applicationContext_resources_fix.xml";
//		try {
//			String className = "org.springframework.jdbc.datasource.DataSourceTransactionManager";
//			Document document = XmlUtil.read(xmlPath);
//			Element root = document.getRootElement();
//			//获取所有的bean节点
//			List<Element> beans = root.elements("bean");
//			if(beans != null) {
//				for (Iterator iterator = beans.iterator(); iterator.hasNext();) {
//					Element element = (Element) iterator.next();
//					if(element.attributeValue("class").equals(className)) {
//						TransactionManagerEntity entity = new TransactionManagerEntity();
//						entity.setId(element.attributeValue("id"));
//						entity.setClassName(element.attributeValue("class"));
//						transactions.add(entity);
//					}
//				}
//			}
//		} catch (Exception e) {
//			FixLoger.error(e.toString());
//			e.printStackTrace();
//		}
//		return transactions;
//	}
	
	/**
	 * 通过文件绝对路径获取事务配置信息
	 * @param fileRealPath
	 * @return
	 */
//	public static List<TransactionManagerEntity> getTransactionManagersFromFileRealPath(String fileRealPath) {
//		List<TransactionManagerEntity> transactions = new ArrayList<TransactionManagerEntity>();
//		
//		//根据文件绝对路径找到其对应的web工程真实路径
//		String webProjectRealPath = getWebOrLogicProjectRealPathFromFileRealPath(fileRealPath);
//		if (webProjectRealPath != null
//				&& !StudioInterface.isWebProject(webProjectRealPath)) {
//			webProjectRealPath = StudioInterface
//					.getWebProjectRealPathFromLogicProjectName(webProjectRealPath
//							.substring(webProjectRealPath.lastIndexOf("/") + 1,
//									webProjectRealPath.length()));
//		}
//
//		//读取xml文件
//		String xmlPath = webProjectRealPath + "/conf/applicationContext_resources_fix.xml";
//		try {
//			String className = "org.springframework.jdbc.datasource.DataSourceTransactionManager";
//			Document document = XmlUtil.read(xmlPath);
//			Element root = document.getRootElement();
//			//获取所有的bean节点
//			List<Element> beans = root.elements("bean");
//			if(beans != null) {
//				for (Iterator iterator = beans.iterator(); iterator.hasNext();) {
//					Element element = (Element) iterator.next();
//					if(element.attributeValue("class").equals(className)) {
//						TransactionManagerEntity entity = new TransactionManagerEntity();
//						entity.setId(element.attributeValue("id"));
//						entity.setClassName(element.attributeValue("class"));
//						transactions.add(entity);
//					}
//				}
//			}
//		} catch (Exception e) {
//			FixLoger.error(e.toString());
//			e.printStackTrace();
//		}
//		return transactions;
//	}
	
	/**
	 * 为业务对象设计器提供获取dictionarytable.xml的信息
	 * @param bizobjId
	 * @return
	 */
//	public static List<DictionaryTableEntity> getDictionaryTables(String bizobjId) {
//		List<DictionaryTableEntity> dictionaryTableEntities = new ArrayList<DictionaryTableEntity>();
//		
//		//根据bizobjId找到其对应的web工程真实路径
//		String webProjectRealPath = getWebProjectRealPathFromBizobjId(bizobjId);
//		
//		//找到dictionarytable.xml文件
//		File dictionarytable = new File(webProjectRealPath + "/webconf/dictionarytable.xml");
//		if(dictionarytable.exists()) {
//			try {
//				Document document = XmlUtil.read(dictionarytable);
//				Element root = document.getRootElement();
//				//获取所有的bean节点
//				List<Element> dictionarytables = root.elements("dictionarytable");
//				if(dictionarytables != null) {
//					for (Iterator iterator = dictionarytables.iterator(); iterator.hasNext();) {
//						Element element = (Element) iterator.next();
//						DictionaryTableEntity entity = new DictionaryTableEntity();
//						
//						entity.setDataobjectId(element.attributeValue("dataobjectid"));
//						entity.setCategoryColumn(element.attributeValue("categorycolumn"));
//						entity.setKeyColumn(element.attributeValue("keycolumn"));
//						entity.setNameColumn(element.attributeValue("namecolumn"));
//						entity.setIsCache(element.attributeValue("iscache"));
//						entity.setQueryMethod(element.attributeValue("queryMethod"));
//						dictionaryTableEntities.add(entity);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				FixLoger.error(e.toString());
//			}
//		}
//		return dictionaryTableEntities;
//	}
	
	/**
	 * 根据文件绝对路径找到其对应的web或者logic工程真实路径
	 * @param fileRealPath
	 * @return
	 */
	public static String getWebOrLogicProjectRealPathFromFileRealPath(String fileRealPath) {
		String projectRealPath = "";
		if(!fileRealPath.equals("")) {
			fileRealPath = fileRealPath.replace('\\', '/');
			//处理路径获取到web工程目录
			fileRealPath = fileRealPath.substring(FixUtil.getWorkspaceRealPath().length());
			fileRealPath = fileRealPath.substring(0, fileRealPath.indexOf("/"));
			projectRealPath = FixUtil.getWorkspaceRealPath() + fileRealPath;
		}
		return projectRealPath;
	}
	
	/**
	 * 根据文件绝对路径找到其对应的web或者logic工程下src真实路径
	 * @param fileRealPath
	 * @param type
	 * @return
	 */
	public static String getWebOrLogicProjectSrcRealPathFromFileRealPath(String fileRealPath, String type) {
		String projectRealPath = fileRealPath;
		String modelName = "", returnStr = "";
		if(!projectRealPath.equals("")) {
			projectRealPath = projectRealPath.replace('\\', '/');
			//处理路径获取到web工程目录
			projectRealPath = projectRealPath.substring(FixUtil.getWorkspaceRealPath().length());
			projectRealPath = projectRealPath.substring(0, projectRealPath.indexOf("/"));
			projectRealPath = FixUtil.getWorkspaceRealPath() + projectRealPath;
			
			//处理获取模块路径
			if(fileRealPath.indexOf("/webapp/globalservices") == -1) {
				String tmpStr = "";
				if(fileRealPath.indexOf("webapp") == -1) {
					tmpStr = "/app/modules/";
				} else {
					tmpStr = "/webapp/modules/";
				}
				modelName = fileRealPath.substring(projectRealPath.length() + tmpStr.length());
				modelName = modelName.substring(0, modelName.indexOf("/"));
				
				returnStr = projectRealPath + "/src/com/fix/" + modelName + "/" + type;
			} else {
				//全局
				returnStr = projectRealPath + "/src/com/fix/global/" + type;
			}
		}
		return returnStr;
	}
	
	/**
	 * 根据表单html绝对路径找到其对应的web工程真实路径
	 * @param bizobjId
	 * @return
	 */
	public static String getWebProjectRealPathFromHtmlRealPath(String htmlRealPath) {
		String webProjectRealPath = "";
		if(!htmlRealPath.equals("")) {
			htmlRealPath = htmlRealPath.replace('\\', '/');
			//处理路径获取到web工程目录
			htmlRealPath = htmlRealPath.substring(FixUtil.getWorkspaceRealPath().length());
			htmlRealPath = htmlRealPath.substring(0, htmlRealPath.indexOf("/"));
			webProjectRealPath = FixUtil.getWorkspaceRealPath() + htmlRealPath;
		}
		return webProjectRealPath;
	}
	
	/**
	 * 根据bizobjId找到其对应的web工程真实路径
	 * @param bizobjId
	 * @return
	 */
	public static String getWebProjectRealPathFromBizobjId(String bizobjId) {
		String webProjectRealPath = "";
		//通过业务对象id找到其绝对路径
		String bizobjRealPath = getBizobjRealPathFromBizobjId(bizobjId);
		
		if(!bizobjRealPath.equals("")) {
			bizobjRealPath = bizobjRealPath.replace('\\', '/');
			//处理路径获取到web工程目录
			bizobjRealPath = bizobjRealPath.substring(FixUtil.getWorkspaceRealPath().length());
			bizobjRealPath = bizobjRealPath.substring(0, bizobjRealPath.indexOf("/"));
			if(!FixStudioInterface.isWebProject(FixUtil.getWorkspaceRealPath() + bizobjRealPath)) {
				webProjectRealPath = FixStudioInterface.getWebProjectRealPathFromLogicProjectName(bizobjRealPath);
			} else {
				webProjectRealPath = FixUtil.getWorkspaceRealPath() + bizobjRealPath;
			}
		}
		return webProjectRealPath;
	}
	
	/**
	 * 根据业务对象真实路径找到其对应的web工程真实路径
	 * @param bizobjRealPath
	 * @return
	 */
	public static String getWebProjectRealPathFromBizobjFilePath(String bizobjRealPath) {
		String webProjectRealPath = "";
		
		if(!bizobjRealPath.equals("")) {
			bizobjRealPath = bizobjRealPath.replace('\\', '/');
			//处理路径获取到web工程目录
			bizobjRealPath = bizobjRealPath.substring(FixUtil.getWorkspaceRealPath().length());
			bizobjRealPath = bizobjRealPath.substring(0, bizobjRealPath.indexOf("/"));
			if(!FixStudioInterface.isWebProject(FixUtil.getWorkspaceRealPath() + bizobjRealPath)) {
				webProjectRealPath = FixStudioInterface.getWebProjectRealPathFromLogicProjectName(bizobjRealPath);
			} else {
				webProjectRealPath = FixUtil.getWorkspaceRealPath() + bizobjRealPath;
			}
		}
		return webProjectRealPath;
	}
	
	/**
	 * 根据bizobjId找到其对应的logic工程真实路径
	 * @param bizobjId
	 * @return
	 */
	public static String getLogicProjectRealPathFromBizobjId(String bizobjId) {
		String logicProjectRealPath = "";
		//通过业务对象id找到其绝对路径
		String bizobjRealPath = getBizobjRealPathFromBizobjId(bizobjId);
		
		if(!bizobjRealPath.equals("")) {
			//处理路径获取到web工程目录
			bizobjRealPath = bizobjRealPath.substring(FixUtil.getWorkspaceRealPath().length());
			bizobjRealPath = bizobjRealPath.substring(0, bizobjRealPath.indexOf("/"));
			logicProjectRealPath = FixUtil.getWorkspaceRealPath() + bizobjRealPath;
		}
		return logicProjectRealPath;
	}
	
	/**
	 * 通过业务对象Id找到其对应的web工程下所建立的表单的目录
	 * @param bizobjId
	 * @return
	 */
	public static String getHtmlDirectoryRealPathFromBizobjId(String bizobjId) {
		return getWebProjectRealPathFromBizobjId(bizobjId) + "/WebRoot/forms/" + getModelNameFromBizobjId(bizobjId);
	}
	
	/**
	 * 通过业务对象Id找到其对应的web工程下所建立的所有表单相对路径
	 * @param bizobjId
	 * @return
	 */
	public static String[] getHtmlFilesRealPathFromBizobjId(String bizobjId) {
		String filterRealPath = FixStudioInterface.getHtmlDirectoryRealPathFromBizobjId(bizobjId) + "/" + bizobjId;
		String webRootPath = FixStudioInterface.getWebProjectRealPathFromBizobjId(bizobjId) + "/WebRoot/";
		
		List<String> list = new ArrayList<String>();
		String[] array = null;

		// 获取缓存中需要过滤的文件夹名称
		String hiddenFileName = FixUtil.getHiddenFileName();

		File tmpFile = new File(filterRealPath);
		if (tmpFile.exists()) {
			File[] files = tmpFile.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.getName().indexOf(hiddenFileName) == -1) {
						list.add(file.getAbsolutePath()
								.substring(webRootPath.length())
								.replace('\\', '/'));
					}
				}
			}
		}

		array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}

		return array;
	}
	
	/**
	 * 通过业务对象Id找到其对应工程下的Fix.xml文件路径
	 * @param bizobjId
	 * @return
	 */
	public static String getFixXmlRealPathFromBizobjId(String bizobjId) {
		return getWebProjectRealPathFromBizobjId(bizobjId) + "/webconf/FIX.xml";
	}
	
	/**
	  * getModelsNameFromProjectName(通过工程名称获取所属所有的模块名称)
	  * @Title: getModelsNameFromProjectName
	  * @Description: TODO
	  * @param @param projectName
	  * @param @return    设定文件
	  * @return String[]    返回类型
	  * @throws
	 */
	public static String[] getModelsNameFromProjectName(String projectName) {
		String[] array = new String[0];
		IProject project = FixUtil.getIProject(projectName);
		IFolder folder = null;
		String projectPath = FixUtil.getWorkspaceRealPath() + projectName;
		if(isWebProject(projectPath)) {
			folder = (IFolder) project.findMember("webapp/modules");
		} else {
			folder = (IFolder) project.findMember("app/modules");
		}
		if(folder == null) {
			return array;
		}
		try {
			IResource[] models = (IResource[]) folder.members();
			if(models != null && models.length > 0) {
				array = new String[models.length];
				for (int i = 0; i < models.length; i++) {
					IResource model = models[i];
					array[i] = model.getName();
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	/**
	 * 通过业务对象id查找模块名称
	 * @param bizobjId
	 * @return
	 */
	public static String getModelNameFromBizobjId(String bizobjId) {
		String modelName = "";
		
		//通过业务对象id找到其绝对路径
		String bizobjRealPath = getBizobjRealPathFromBizobjId(bizobjId);
		
		try {
			bizobjRealPath = bizobjRealPath.replace('\\', '/');
			//处理字符串
			for (int i = 0; i < 3; i++) {
				bizobjRealPath = bizobjRealPath.substring(0, bizobjRealPath.lastIndexOf("/"));
			}
			modelName = bizobjRealPath.substring(bizobjRealPath.lastIndexOf("/") + 1, 
					bizobjRealPath.length());
		} catch (Exception e) {
			FixLoger.error("通过业务对象查找模块名称失败，错误信息：\n" + e.getMessage());
			e.printStackTrace();
		}
		
		return modelName;
	}
	
	/**
	 * 通过业务对象id查找模块绝对路径
	 * @param bizobjId
	 * @return
	 */
	public static String getModelRealPathFromBizobjId(String bizobjId) {
		//通过业务对象id找到其绝对路径
		String bizobjRealPath = getBizobjRealPathFromBizobjId(bizobjId);
		
		try {
			//处理字符串
			for (int i = 0; i < 3; i++) {
				bizobjRealPath = bizobjRealPath.substring(0, bizobjRealPath.lastIndexOf("/"));
			}
		} catch (Exception e) {
			FixLoger.error("通过业务对象查找模块路径失败，错误信息：\n" + e.getMessage());
		}
		
		return bizobjRealPath;
	}
	
	/**
	 * 根据模块绝对路径获取模块对应的显示名称tranName
	 * @param modelRealPath
	 * @return
	 */
	public static String getModelTransNameFromRealPath(String modelRealPath) {
		String transName = "";
		File fixres = new File(modelRealPath + "/model.fixres");
		if(fixres.exists()) {
			Document document;
			try {
				document = XmlUtil.read(fixres, "UTF-8");
				Element root = document.getRootElement();
				transName = root.attributeValue("chinese");
			} catch (DocumentException e) {
				FixLoger.error(e.toString());
				e.printStackTrace();
			}
		}
		return transName;
	}
	
	/**
	 * 根据文件绝对路径获取其web工程和logic工程下所有模块全路径
	 * @param fileRealPath
	 */
	public static List<ModelEntity> getModelsRealPathFromFileRealPath(String fileRealPath) {
		//获取hiddenName
		String hiddenName = FixUtil.getHiddenFileName();
		
		//存放所有spring配置文件的全路径列表
		List<ModelEntity> allModels = new ArrayList<ModelEntity>();
		String logicProjectPath = "", webProjectPath = "";
		
		//通过真实路径找到工程路径
		String projectRealPath = getWebOrLogicProjectRealPathFromFileRealPath(fileRealPath);
		if(!FixStudioInterface.isWebProject(projectRealPath)) {
			logicProjectPath = projectRealPath;
			webProjectPath = getWebProjectRealPathFromLogicProjectName(logicProjectPath
					.substring(logicProjectPath.lastIndexOf("/") + 1, logicProjectPath.length()));
		} else if(FixStudioInterface.isWebProject(projectRealPath)) {
			webProjectPath = projectRealPath;
		}
		
		webProjectPath = webProjectPath.replace('\\', '/');
		
		//封装logic工程所有的spring文件全路径
		List<String> logicProjects = getLogicProjectsRealPathFromWebProjectName(webProjectPath
				.substring(webProjectPath.lastIndexOf("/") + 1, webProjectPath.length()));
		for (Iterator iterator = logicProjects.iterator(); iterator.hasNext();) {
			String logicRealPath = (String) iterator.next();
			//找到所有模块
			String springConfig = logicRealPath + "/app/modules/";
			File[] models = new File(springConfig).listFiles();
			if(models != null && models.length > 0) {
				for (int i = 0; i < models.length; i++) {
					File model = models[i];
					if(model.isDirectory() && !model.getName().equals(hiddenName)) {
						//读取model.fixres获取transName
						String transName = "";
						try {
							Document document = XmlUtil.read(model.getAbsolutePath() + "/model.fixres");
							Element root = document.getRootElement();
							transName = root.attributeValue("chinese");
						} catch (Exception e) {
							FixLoger.error(e.toString());
							e.printStackTrace();
						}
						//封装实体类
						ModelEntity entity = new ModelEntity();
						entity.setName(model.getName());
						entity.setRealPath(model.getAbsolutePath());
						entity.setTransName(transName);
						allModels.add(entity);
					}
				}
			}
		}
		
		//封装web工程所有的spring文件全路径
		String springConfig = webProjectPath + "/webapp/modules/";
		//找到所有模块
		File[] models = new File(springConfig).listFiles();
		if(models != null && models.length > 0) {
			for (int i = 0; i < models.length; i++) {
				File model = models[i];
				if(model.isDirectory() && !model.getName().equals(hiddenName)) {
					//读取model.fixres获取transName
					String transName = "";
					try {
						Document document = XmlUtil.read(model.getAbsolutePath() + "/model.fixres");
						Element root = document.getRootElement();
						transName = root.attributeValue("chinese");
					} catch (Exception e) {
						FixLoger.error(e.toString());
						e.printStackTrace();
					}
					//封装实体类
					ModelEntity entity = new ModelEntity();
					entity.setName(model.getName());
					entity.setRealPath(model.getAbsolutePath());
					entity.setTransName(transName);
					allModels.add(entity);
				}
			}
		}
		return allModels;
	}
	
	/**
	 * 分拆业务对象和方法、查看是否方法的动词为exeSQL（如果是、需要自动执行sql语句并填充表格数据）
	 * @return
	 */
//	public static List<BaseEntity> getSqlReturnData(String service) {
//		List<BaseEntity> baseEntities = null;
//		if(service == null || service.equals("")) {
//			return null;
//		}
//		String[] services = service.split("\\.");
//		if(services != null && services.length > 0) {
//			String bizobj = services[0];
//			String sev = services[1];
//			String filePath = StudioInterface.getBizobjRealPathFromBizobjId(bizobj);
//			if(filePath != null && !filePath.equals("")) {
//				try {
//					Document document = XmlUtil.read(new File(filePath), "UTF-8");
//					Element root = document.getRootElement();
//					List<Element> elements = root.elements("service");
//					if(elements != null && elements.size() > 0) {
//						for (Iterator iterator = elements
//								.iterator(); iterator.hasNext();) {
//							Element element = (Element) iterator
//									.next();
//							if(element.attributeValue("id").equals(sev)) {
//								String implement = element.element("implement").getText();
//								
//								//找到执行sql语句的方法
//								if(implement != null && implement.startsWith("exeSql")) {
//									//获取数据库resource对象
//									Resource resource = DataSourceOperation.findContextFile(implement.substring(
//											implement.indexOf("(") + 1,
//											implement.indexOf(",")));
//									//获取sql语句
//									String sql = implement.substring(implement.indexOf(")") + 1);
//									//需要自动执行sql语句并填充表格数据
//									DatabaseEntity dbEntity = StudioInterface.getColumnsFromSQL(resource, sql);
//									if (dbEntity != null) {
//										List<Map<String, Object>> rowData = dbEntity.getRowsData();
//										baseEntities = new ArrayList<BaseEntity>();
//										if (rowData != null) {
//											for (int i = 0; i < rowData.size(); i++) {
//												Map<String, Object> data = rowData.get(i);
//												String id = ((String) data
//														.get(DatabaseEntity.COLUMN_NAME)).toUpperCase();
//												String name = ((String) data
//														.get(DatabaseEntity.COLUMN_NAME)).toUpperCase();
//												String type = (String) data
//														.get(DatabaseEntity.DATA_TYPE);
////												Boolean isPrimaryKey = (Boolean) data
////														.get(DatabaseEntity.IS_PRIMARYKEY);
//												
//												BaseEntity entity = new BaseEntity();
//												entity.setId(id);
//												entity.setName(name);
//												entity.setType(type);
//												baseEntities.add(entity);
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					FixLoger.error(e.toString());
//				}
//			}
//		}
//		return baseEntities;
//	}
	
	/**
	 * 根据业务对象绝对路径找到其对应模块下的sev文件夹路径
	 * @param bizObjRealPath
	 * @return
	 */
	public static String getSevicesDirectiryRealPathFromBizobjRealPath(String bizObjRealPath) {
		String sevRealPath = bizObjRealPath.substring(0, bizObjRealPath.lastIndexOf("/"));
		sevRealPath = bizObjRealPath.substring(0, bizObjRealPath.lastIndexOf("/"));
		sevRealPath += "/services/";
		return sevRealPath;
	}
	
	/**
	 * 根据文件绝对路径找到所有sev文件和所有业务对象的全路径
	 * @param fileRealPath
	 * @return
	 */
	public static List<String> getSevicesAndBizObjsRealPathFromFileRealPath(String fileRealPath) {
		//获取hiddenName
		String hiddenName = FixUtil.getHiddenFileName();
		
		//存放所有spring配置文件的全路径列表
		List<String> allFilesPath = new ArrayList<String>();
		String logicProjectPath = "", webProjectPath = "";
		
		//通过真实路径找到工程路径
		String projectRealPath = getWebOrLogicProjectRealPathFromFileRealPath(fileRealPath);
		if(!FixStudioInterface.isWebProject(projectRealPath)) {
			logicProjectPath = projectRealPath;
			webProjectPath = getWebProjectRealPathFromLogicProjectName(logicProjectPath
					.substring(logicProjectPath.lastIndexOf("/") + 1, logicProjectPath.length()));
		} else if(FixStudioInterface.isWebProject(projectRealPath)) {
			webProjectPath = projectRealPath;
		}
		
		webProjectPath = webProjectPath.replace('\\', '/');
		
		//封装logic工程所有的service文件全路径
		List<String> logicProjects = getLogicProjectsRealPathFromWebProjectName(webProjectPath
				.substring(webProjectPath.lastIndexOf("/") + 1, webProjectPath.length()));
		for (Iterator iterator = logicProjects.iterator(); iterator.hasNext();) {
			String logicRealPath = (String) iterator.next();
			//找到所有模块
			String serviceConfig = logicRealPath + "/app/modules/";
			File[] models = new File(serviceConfig).listFiles();
			if(models != null && models.length > 0) {
				for (int i = 0; i < models.length; i++) {
					File model = models[i];
					if(model.isDirectory() && !model.getName().equals(hiddenName)) {
						//方法
						String servicePath = model.getAbsolutePath() + "/business/services/";
						File[] services = new File(servicePath).listFiles();
						if(services != null && services.length > 0) {
							for (int j = 0; j < services.length; j++) {
								File service = services[j];
								if(service.exists() && service.isFile()) {
									allFilesPath.add(service.getAbsolutePath());
								}
							}
						}
						//业务对象
						String bizobjPath = model.getAbsolutePath() + "/business/bizobj/";
						File[] bizobjs = new File(bizobjPath).listFiles();
						if(bizobjs != null && bizobjs.length > 0) {
							for (int j = 0; j < bizobjs.length; j++) {
								File bizobj = bizobjs[j];
								if(bizobj.exists() && bizobj.isFile()) {
									allFilesPath.add(bizobj.getAbsolutePath());
								}
							}
						}
					}
				}
			}
		}
		
		//封装web工程所有的service文件全路径
		String serviceConfig = webProjectPath + "/webapp/modules/";
		//找到所有模块
		File[] models = new File(serviceConfig).listFiles();
		if(models != null && models.length > 0) {
			for (int i = 0; i < models.length; i++) {
				File model = models[i];
				if(model.isDirectory() && !model.getName().equals(hiddenName)) {
					//方法
					String servicePath = model.getAbsolutePath() + "/business/services/";
					File[] services = new File(servicePath).listFiles();
					if(services != null && services.length > 0) {
						for (int j = 0; j < services.length; j++) {
							File service = services[j];
							if(service.exists() && service.isFile()) {
								allFilesPath.add(service.getAbsolutePath());
							}
						}
					}
					//业务对象
					String bizobjPath = model.getAbsolutePath() + "/business/bizobj/";
					File[] bizobjs = new File(bizobjPath).listFiles();
					if(bizobjs != null && bizobjs.length > 0) {
						for (int j = 0; j < bizobjs.length; j++) {
							File bizobj = bizobjs[j];
							if(bizobj.exists() && bizobj.isFile()) {
								allFilesPath.add(bizobj.getAbsolutePath());
							}
						}
					}
				}
			}
		}
		
		//web工程下还有一个全局的globalservices
		String globalservicesConfig = webProjectPath + "/webapp/globalservices/";
		//方法
		File[] services = new File(globalservicesConfig).listFiles();
		if(services != null && services.length > 0) {
			for (int i = 0; i < services.length; i++) {
				File service = services[i];
				if(service.exists() && service.isFile()) {
					allFilesPath.add(service.getAbsolutePath());
				}
			}
		}
		
		return allFilesPath;
	}
	
	/**
	 * 通过业务对象id遍历所属logic和web工程下所有spring配置文件全路径
	 * @param bizObjId
	 * @return
	 */
	public static List<String> getSpringsRealPathFromBizobjId(String bizObjId) {
		//通过bizobjId找到绝对路径
		String bizObjRealPath = getBizobjRealPathFromBizobjId(bizObjId);
		return getSpringsRealPathFromFileRealPath(bizObjRealPath);
	}
	
	/**
	 * 根据业务对象id读取spring配置信息，返回所有的bean
	 * @param bizobjId
	 */
//	public static List<BeanEntity> getSpringBeansFromBizobjId(String bizobjId) {
//		List<BeanEntity> beanEntities = new ArrayList<BeanEntity>();
//		//通过业务对象id找到其绝对路径
//		String bizobjRealPath = getBizobjRealPathFromBizobjId(bizobjId);
//		//通过该路径找到对应的Spring配置文件
//		String springConfig = bizobjRealPath.substring(0,
//				bizobjRealPath.lastIndexOf("/")).substring(
//				0, bizobjRealPath.substring(0, bizobjRealPath.lastIndexOf("/")).lastIndexOf("/")).substring(0, bizobjRealPath.substring(0,
//				bizobjRealPath.lastIndexOf("/")).substring(
//				0, bizobjRealPath.substring(0, bizobjRealPath.lastIndexOf("/")).lastIndexOf("/")).lastIndexOf("/")) + "/conffiles/spring";
//		File springConfigFile = new File(springConfig);
//		if(springConfigFile.exists()) {
//			//找到所有spring配置文件
//			File[] springConfigFiles = springConfigFile.listFiles();
//			if(springConfigFiles != null) {
//				for (int i = 0; i < springConfigFiles.length; i++) {
//					File spring = springConfigFiles[i];
//					if(spring.exists() && spring.isFile()) {
//						//读取文件
//						try {
//							Document document = XmlUtil.read(spring);
//							Element root = document.getRootElement();
//							List<Element> beansElement = root.elements("bean");  //读取所有的bean配置
//							if(beansElement != null) {
//								for (Iterator iterator = beansElement
//										.iterator(); iterator.hasNext();) {
//									Element element = (Element) iterator.next();
//									//封装参数
//									BeanEntity entity = new BeanEntity();
//									entity.setId(element.attributeValue("id"));
//									entity.setClassName(element.attributeValue("class"));
//									entity.setFileRealPath(spring.getAbsolutePath());
//									
//									beanEntities.add(entity);
//								}
//							} 
//						} catch (DocumentException e) {
//							FixLoger.error(e.toString());
//							e.printStackTrace();
//						}
//					}
//				}
//			} 
//		}
//		return beanEntities;
//	}
	
	/**
	 * 根据方法真实路径读取所有spring配置信息，返回所有的bean
	 * @param serviceRealPath
	 */
//	public static List<BeanEntity> getSpringBeansFromServiceRealPath(String serviceRealPath) {
//		serviceRealPath = serviceRealPath.replace('\\', '/');
//		
//		List<BeanEntity> beanEntities = new ArrayList<BeanEntity>();
//		//存放所有spring配置文件的全路径列表
//		List<String> allSpringFilesPath = getSpringsRealPathFromFileRealPath(serviceRealPath);
//		
//		//封装spring文件实例
//		if(allSpringFilesPath.size() > 0) {
//			for (Iterator iterator = allSpringFilesPath.iterator(); iterator
//					.hasNext();) {
//				String springRealPath = (String) iterator.next();
//				//读取文件
//				try {
//					Document document = XmlUtil.read(springRealPath);
//					Element root = document.getRootElement();
//					List<Element> beansElement = root.elements("bean");  //读取所有的bean配置
//					if(beansElement != null) {
//						for (Iterator iterator2 = beansElement
//								.iterator(); iterator2.hasNext();) {
//							Element element = (Element) iterator2.next();
//							//封装参数
//							BeanEntity entity = new BeanEntity();
//							entity.setId(element.attributeValue("id"));
//							entity.setClassName(element.attributeValue("class"));
//							entity.setFileRealPath(springRealPath);
//							
//							beanEntities.add(entity);
//						}
//					} 
//				} catch (DocumentException e) {
//					FixLoger.error(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}
//		return beanEntities;
//	}
	
	
	/**
	 * 根据方法真实路径读取所有spring配置信息，返回所有的bean
	 * @param serviceRealPath
	 */
//	public static List<BeanEntity> getSpringRealPath(String RealPath) {
//		RealPath = RealPath.replace('\\', '/');
//		
//		List<BeanEntity> beanEntities = new ArrayList<BeanEntity>();
//		//存放所有spring配置文件的全路径列表
////		List<String> allSpringFilesPath = getSpringsRealPathFromFileRealPath(RealPath);
//		
//		//封装spring文件实例
////		if(allSpringFilesPath.size() > 0) {
////			for (Iterator iterator = allSpringFilesPath.iterator(); iterator
////					.hasNext();) {
////				String springRealPath = (String) iterator.next();
//				//读取文件
//				try {
//					Document document = XmlUtil.read(RealPath);
//					Element root = document.getRootElement();
//					List<Element> beansElement = root.elements("bean");  //读取所有的bean配置
//					if(beansElement != null) {
//						for (Iterator iterator2 = beansElement
//								.iterator(); iterator2.hasNext();) {
//							Element element = (Element) iterator2.next();
//							//封装参数
//							BeanEntity entity = new BeanEntity();
//							entity.setId(element.attributeValue("id"));
//							entity.setClassName(element.attributeValue("class"));
//							entity.setFileRealPath(RealPath);
//							
//							beanEntities.add(entity);
//						}
//					} 
//				} catch (DocumentException e) {
//					FixLoger.error(e.toString());
//					e.printStackTrace();
//				}
////			}
////		}
//		return beanEntities;
//	}
	/**
	 * 根据业务对象id返回一个spring配置文件绝对路径
	 * @param bizobjId
	 * @return
	 */
	public static String getSpringRealPathFromBizobjId(String bizobjId) {
		String springFileRealPath = "";
		//通过业务对象id找到其绝对路径
		String bizobjRealPath = getBizobjRealPathFromBizobjId(bizobjId);
		//通过该路径找到对应的Spring配置文件
		String springConfig = bizobjRealPath.substring(0,
				bizobjRealPath.lastIndexOf("/")).substring(
				0, bizobjRealPath.substring(0, bizobjRealPath.lastIndexOf("/")).lastIndexOf("/")).substring(0, bizobjRealPath.substring(0,
				bizobjRealPath.lastIndexOf("/")).substring(
				0, bizobjRealPath.substring(0, bizobjRealPath.lastIndexOf("/")).lastIndexOf("/")).lastIndexOf("/")) + "/conffiles/spring";
		File springConfigFile = new File(springConfig);
		if(springConfigFile.exists()) {
			//找到所有spring配置文件
			File[] springConfigFiles = springConfigFile.listFiles();
			if(springConfigFiles != null) {
				for (int i = 0; i < springConfigFiles.length; i++) {
					File spring = springConfigFiles[i];
					if(spring.exists() && spring.isFile()) {
						//随机选择一个文件返回
						springFileRealPath = spring.getAbsolutePath();
						break;
					}
				}
			}
		}
		return springFileRealPath;
	}
	
	/**
	 * 根据方法真实路径返回本工程一个spring配置文件绝对路径，没有会创建一个
	 * @param serviceRealPath
	 * @return
	 */
	public static String getSpringRealPathFromServiceRealPath(String serviceRealPath) {
		//获取hiddenName
		String hiddenName = FixUtil.getHiddenFileName();
		
		String springFileRealPath = "";
		if(serviceRealPath == null || serviceRealPath.equals("")) {
			return "";
		}
		serviceRealPath = serviceRealPath.replace('\\', '/');
		if(serviceRealPath.indexOf("globalservices") == -1) { //如果该方法不存在于globalservices中
			//处理字符串
			serviceRealPath = serviceRealPath.substring(0, serviceRealPath.lastIndexOf("/"));
			serviceRealPath = serviceRealPath.substring(0, serviceRealPath.lastIndexOf("/"));
			serviceRealPath = serviceRealPath.substring(0, serviceRealPath.lastIndexOf("/"));
			serviceRealPath = serviceRealPath.substring(0, serviceRealPath.lastIndexOf("/"));
			serviceRealPath += "/conffiles/spring";
		} else {  //如果该方法存在于工程的模块中
			serviceRealPath = serviceRealPath.substring(0, serviceRealPath.lastIndexOf("/"));
			serviceRealPath = serviceRealPath.substring(0, serviceRealPath.lastIndexOf("/"));
			File model = new File(serviceRealPath + "/modules");
			File[] models = model.listFiles();
			if(models != null && models.length > 0) {
				for (int i = 0; i < models.length; i++) {
					File modelFile = models[i];
					if(modelFile.isDirectory() && !modelFile.getName().equals(hiddenName)) {
						//随机选择一个文件返回
						serviceRealPath = modelFile.getAbsolutePath() + "/conffiles/spring";
						break;
					}
				}
			}
		}
		File springConfigFile = new File(serviceRealPath);
		if(springConfigFile.exists()) {
			//找到所有spring配置文件
			File[] springConfigFiles = springConfigFile.listFiles();
			if(springConfigFiles != null) {
				for (int i = 0; i < springConfigFiles.length; i++) {
					File spring = springConfigFiles[i];
					if(spring.exists() && spring.isFile()) {
						springFileRealPath = spring.getAbsolutePath();
						break;
					}
				}
			}
			if (springFileRealPath.equals("")) {
				// 创建该文件
				try {
					Document document = XmlUtil.createDocument();
					Element root = document.addElement("beans");
					root.setAttributeValue(
							"xmlns",
							"xmlns=\"http://www.springframework.org/schema/beans\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:context=\"http://www.springframework.org/schema/context\" xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd\" default-lazy-init=\"true\"");
					XmlUtil.save(document, springConfigFile.getAbsolutePath()
							+ "/applicationContext_fix.xml", "UTF-8");
					springFileRealPath = springConfigFile.getAbsolutePath()
							+ "/applicationContext_fix.xml";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} 
		return springFileRealPath;
	}
	
	/**
	 * 通过文件全路径遍历所属logic和web工程下所有spring配置文件全路径
	 * @param fileRealPath
	 * @return
	 */
	public static List<String> getSpringsRealPathFromFileRealPath(String fileRealPath) {
		//获取hiddenName
		String hiddenName = FixUtil.getHiddenFileName();
		
		//存放所有spring配置文件的全路径列表
		List<String> allSpringFilesPath = new ArrayList<String>();
		String logicProjectPath = "", webProjectPath = "";
		
		//通过真实路径找到工程路径
		String projectRealPath = getWebOrLogicProjectRealPathFromFileRealPath(fileRealPath);
		if(!FixStudioInterface.isWebProject(projectRealPath)) {
			logicProjectPath = projectRealPath;
			webProjectPath = getWebProjectRealPathFromLogicProjectName(logicProjectPath
					.substring(logicProjectPath.lastIndexOf("/") + 1, logicProjectPath.length()));
		} else if(FixStudioInterface.isWebProject(projectRealPath)) {
			webProjectPath = projectRealPath;
		}
		
		webProjectPath = webProjectPath.replace('\\', '/');
		
		//封装logic工程所有的spring文件全路径
		List<String> logicProjects = getLogicProjectsRealPathFromWebProjectName(webProjectPath
				.substring(webProjectPath.lastIndexOf("/") + 1, webProjectPath.length()));
		for (Iterator iterator = logicProjects.iterator(); iterator.hasNext();) {
			String logicRealPath = (String) iterator.next();
			//找到所有模块
			String springConfig = logicRealPath + "/app/modules/";
			File[] models = new File(springConfig).listFiles();
			if(models != null && models.length > 0) {
				for (int i = 0; i < models.length; i++) {
					File model = models[i];
					if(model.isDirectory() && !model.getName().equals(hiddenName)) {
						String springPath = model.getAbsolutePath() + "/conffiles/spring/";
						File[] springs = new File(springPath).listFiles();
						if(springs != null && springs.length > 0) {
							for (int j = 0; j < springs.length; j++) {
								File spring = springs[j];
								if(spring.exists() && spring.isFile()) {
									allSpringFilesPath.add(spring.getAbsolutePath());
								}
							}
						}
					}
				}
			}
		}
		
		//封装web工程所有的spring文件全路径
		String springConfig = webProjectPath + "/webapp/modules/";
		//找到所有模块
		File[] models = new File(springConfig).listFiles();
		if(models != null && models.length > 0) {
			for (int i = 0; i < models.length; i++) {
				File model = models[i];
				if(model.isDirectory() && !model.getName().equals(hiddenName)) {
					String springPath = model.getAbsolutePath() + "/conffiles/spring/";
					File[] springs = new File(springPath).listFiles();
					if(springs != null && springs.length > 0) {
						for (int j = 0; j < springs.length; j++) {
							File spring = springs[j];
							if(spring.exists() && spring.isFile()) {
								allSpringFilesPath.add(spring.getAbsolutePath());
							}
						}
					}
				}
			}
		}
		
		//添加全局服务的spring,spring配置文件位置
		allSpringFilesPath.add(webProjectPath + "/webapp/globalservices/applicationContext_ibatis_fix.xml");
		return allSpringFilesPath;
	}
	
	/**
	 * 根据文件真实路径读取所有bpmn配置信息，返回所有的bean
	 * @param fileRealPath
	 */
//	public static List<ProcessEntity> getBpmnBeansFromFileRealPath(String fileRealPath) {
//		fileRealPath = fileRealPath.replace('\\', '/');
//		
//		List<ProcessEntity> processEntities = new ArrayList<ProcessEntity>();
//		//存放所有spring配置文件的全路径列表
//		List<String> allBpmnFilesPath = getBpmnsRealPathFromFileRealPath(fileRealPath);
//		
//		//封装spring文件实例
//		if(allBpmnFilesPath.size() > 0) {
//			for (Iterator iterator = allBpmnFilesPath.iterator(); iterator
//					.hasNext();) {
//				String bpmnRealPath = (String) iterator.next();
//				//读取文件
//				try {
//					Document document = XmlUtil.read(bpmnRealPath);
//					Element root = document.getRootElement();
//					Element processElement = root.element("process");  //读取所有的process配置
//					if(processElement != null) {
//						//封装参数
//						ProcessEntity entity = new ProcessEntity();
//						entity.setId(processElement.attributeValue("id"));
//						entity.setName(processElement.attributeValue("name"));
//							
//						processEntities.add(entity);
//					} 
//				} catch (DocumentException e) {
//					FixLoger.error(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}
//		return processEntities;
//	}
	
	/**
	 * 根据文件全路径获取所有其有关的工程下所有的bpmn文件全路径
	 * @param fileRealPath
	 */
	private static List<String> getBpmnsRealPathFromFileRealPath(String fileRealPath) {
		//获取hiddenName
		String hiddenName = FixUtil.getHiddenFileName();
		
		//存放所有spring配置文件的全路径列表
		List<String> allBpmnFilesPath = new ArrayList<String>();
		String logicProjectPath = "", webProjectPath = "";
		
		//通过真实路径找到工程路径
		String projectRealPath = getWebOrLogicProjectRealPathFromFileRealPath(fileRealPath);
		if(!FixStudioInterface.isWebProject(projectRealPath)) {
			logicProjectPath = projectRealPath;
			webProjectPath = getWebProjectRealPathFromLogicProjectName(logicProjectPath
					.substring(logicProjectPath.lastIndexOf("/") + 1, logicProjectPath.length()));
		} else if(FixStudioInterface.isWebProject(projectRealPath)) {
			webProjectPath = projectRealPath;
		}
		
		webProjectPath = webProjectPath.replace('\\', '/');

		//封装logic工程所有的bpmn文件全路径
		List<String> logicProjects = getLogicProjectsRealPathFromWebProjectName(webProjectPath
				.substring(webProjectPath.lastIndexOf("/") + 1, webProjectPath.length()));
		for (Iterator iterator = logicProjects.iterator(); iterator.hasNext();) {
			String logicRealPath = (String) iterator.next();
			//找到所有模块
			String bpmnConfig = logicRealPath + "/app/modules/";
			File[] models = new File(bpmnConfig).listFiles();
			if(models != null && models.length > 0) {
				for (int i = 0; i < models.length; i++) {
					File model = models[i];
					if(model.isDirectory() && !model.getName().equals(hiddenName)) {
						String bpmnPath = model.getAbsolutePath() + "/bpmn/";
						File[] bpmns = new File(bpmnPath).listFiles();
						if(bpmns != null && bpmns.length > 0) {
							for (int j = 0; j < bpmns.length; j++) {
								File bpmn = bpmns[j];
								if(bpmn.exists() && bpmn.isFile()) {
									allBpmnFilesPath.add(bpmn.getAbsolutePath());
								}
							}
						}
					}
				}
			}
		}
		
		//封装web工程所有的spring文件全路径
		String bpmnConfig = webProjectPath + "/webapp/modules/";
		//找到所有模块
		File[] models = new File(bpmnConfig).listFiles();
		if(models != null && models.length > 0) {
			for (int i = 0; i < models.length; i++) {
				File model = models[i];
				if(model.isDirectory() && !model.getName().equals(hiddenName)) {
					String bpmnPath = model.getAbsolutePath() + "/bpmn/";
					File[] bpmns = new File(bpmnPath).listFiles();
					if(bpmns != null && bpmns.length > 0) {
						for (int j = 0; j < bpmns.length; j++) {
							File bpmn = bpmns[j];
							if(bpmn.exists() && bpmn.isFile()) {
								allBpmnFilesPath.add(bpmn.getAbsolutePath());
							}
						}
					}
				}
			}
		}
		
		return allBpmnFilesPath;
	}
	
	/**
	 * 根据视图id获取视图对象
	 * @param viewId
	 * @return
	 */
	public static ViewPart getViewPartFromViewId(String viewId) {
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			return (ViewPart) page.findView(viewId);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 根据视图id获取显示视图
	 * @param viewId
	 * @return
	 */
	public static void showViewPartFromViewId(String viewId) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			page.showView(viewId);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据当前的编辑器定位树节点
	 */
//	public static void linkTreeElement() {
//		//根据当前打开的编辑器找到其对应的编辑器内部文件的绝对路径
//		String fileFullPath = FixUtil.getFileFullPathFromActiveEditor();
//		if(fileFullPath.startsWith("/")) {
//			fileFullPath = fileFullPath.substring(1);
//		}
//		if(fileFullPath != null && !fileFullPath.equals("")) {
//			//定位文件
//			ITreeElement element = FixUtil
//					.getSelectElementFromFileFullPath(fileFullPath);
//			if(element != null) {
//				TreeViewer treeViewer = ((ProjectView) StudioInterface
//						.getViewPartFromViewId(FixPerspective.PROJECT_VIEW))
//						.getTreeViewer();
//				linkTree(treeViewer, element);
//				treeViewer.setSelection(new StructuredSelection(element));
//			}
//		}
//	}
	
	/**
	 * 根据该树节点递归打开其父节点
	 * @param treeViewer
	 * @param element
	 */
	private static void linkTree(TreeViewer treeViewer, ITreeElement element) {
		if(element.getParent() != null) {
			linkTree(treeViewer, element.getParent());
		}
		if(element.getChildren() == null) {
			treeViewer.expandToLevel(element, 0);
		} else {
			treeViewer.expandToLevel(element, 1);
		}
	}
	
	/**
	 * 通过文件绝对路径刷新workspace下的该文件
	 * @param fileFullPath
	 */
	public static void refreshTruthFileFromFileFullPath(String fileFullPath) {
		String projectName = FixUtil.getProjectPath(fileFullPath);
		String fileName = FixUtil.getFilePath(fileFullPath);
		FixUtil.refreshFolder(projectName, fileName);
	}
	
	/**
	 * 通过业务对象id刷新其对应模块下的view(表单显示)树节点
	 * @param bizobjId
	 */
	public static void refreshViewTreeElementFromBizobjId(String bizobjId) {
		//通过业务对象id找到其绝对路径
		String bizobjPath = FixStudioInterface.getBizobjRealPathFromBizobjId(bizobjId);
		//根据当前的业务对象获取其对应的view文件夹真实路径
		String viewDirectory = bizobjPath.substring(0, bizobjPath.indexOf("business/bizobj")) + "formview";
		//根据文件对象全路径获取其对应的树节点
//		ITreeElement viewDirectoryElement = FixUtil.getSelectElementFromFileFullPath(viewDirectory);
		//刷新树节点
//		FixUtil.refreshSelectTreeElement(viewDirectoryElement);
	}
	
	/**
	 * 根据web工程获取其所有引用的logic工程的对象实例集合
	 * @param webProjectName
	 * @return
	 */
//	public static List<LogicEntity> getLogicEntitiesFromWebProjectName(String webProjectName){
//		List<LogicEntity> logicEntityList = new ArrayList<LogicEntity>();
//		
//		String classpath = FixUtil.getWorkspaceRealPath() + webProjectName + "/.classpath";
//		try {
//			Document document = XmlUtil.read(classpath);
//			Element root = document.getRootElement();
//			List<Element> elements = root.elements("classpathentry");
//			for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
//				Element element = (Element) iterator.next();
//				if(element.attributeValue("combineaccessrules") != null 
//						&& !element.attributeValue("combineaccessrules").equals("")) {
//					String prjPath = element.attributeValue("path");
//					String logicPrjRealPath = FixUtil.getWorkspaceRealPath() + 
//									prjPath.substring(prjPath.indexOf("/") + 1);
//					LogicEntity logicEntity = new LogicEntity();
//					logicEntity.setPath(logicPrjRealPath);
//					logicEntity.setName(prjPath.substring(1));
//					logicEntityList.add(logicEntity);
//				}
//			}
//		} catch (DocumentException e) {
//			FixLoger.error(e.toString());
//			e.printStackTrace();
//		}
//		
//		return logicEntityList;
//	}
	
	/**
	 * 根据web工程的名称获取其引用的所有logic工程全路径集合
	 * @param webProjectName
	 * @return
	 */
	public static List<String> getLogicProjectsRealPathFromWebProjectName(String webProjectName) {
		List<String> logicProjectsRealPath = new ArrayList<String>();
		//找到.classpath文件
		String classpath = FixUtil.getWorkspaceRealPath() + webProjectName + "/.classpath";
		try {
			Document document = XmlUtil.read(classpath);
			Element root = document.getRootElement();
			List<Element> elements = root.elements("classpathentry");
			for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				if(element.attributeValue("combineaccessrules") != null 
						&& !element.attributeValue("combineaccessrules").equals("")) {
					logicProjectsRealPath.add(FixUtil.getWorkspaceRealPath() + 
							element.attributeValue("path").substring(element.attributeValue("path").indexOf("/") + 1));
				}
			}
		} catch (DocumentException e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
		return logicProjectsRealPath;
	}
	
	/**
	 * 根据logic工程的名称获取其引用它的web工程全路径
	 * @param logicProjectName
	 * @return
	 */
	public static String getWebProjectRealPathFromLogicProjectName(String logicProjectName) {
		String webProjectRealPath = "";
		//找到workspace下所有的web工程
		File files = new File(FixUtil.getWorkspaceRealPath());
		if(files.exists()) {
			File[] workspaceFiles = files.listFiles();
			if(workspaceFiles != null && workspaceFiles.length > 0) {
				for (int i = 0; i < workspaceFiles.length; i++) {
					File file = workspaceFiles[i];
					if(file.exists() && file.isDirectory() && FixStudioInterface.isWebProject(file.getAbsolutePath())) {
						//读取其.classpath看是否引用的logic工程
						try {
							Document document = XmlUtil.read(file.getAbsolutePath() + "/.classpath");
							Element root = document.getRootElement();
							List<Element> elements = root.elements("classpathentry");
							for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
								Element element = (Element) iterator.next();
								if(element.attributeValue("combineaccessrules") != null 
										&& !element.attributeValue("combineaccessrules").equals("")
										&& element.attributeValue("path").equals("/" + logicProjectName)) {
									webProjectRealPath = file.getAbsolutePath();
									break;
								}
							}
						} catch (DocumentException e) {
							FixLoger.error(e.toString());
							e.printStackTrace();
						}
					}
				}
			}
		}
		return webProjectRealPath.replace('\\', '/');
	}
	
	/**
	 * 根据logic工程的路径获取其引用它的web工程全路径
	 * @param logicProjectRealPath
	 * @return
	 */
	public static String getWebProjectRealPathFromLogicProjectRealPath(String logicProjectRealPath) {
		logicProjectRealPath = logicProjectRealPath.replace('\\', '/');
		return getWebProjectRealPathFromLogicProjectName(logicProjectRealPath.substring(logicProjectRealPath.lastIndexOf("/") + 1, 
				logicProjectRealPath.length()));
	}
	
	/**
	 * 在工作空间下找到所有的java类文件
	 */
	public static List<JavaEntity> getAllJavaFilesFromWholeWorkspace() {
		List<JavaEntity> javaEntities = new ArrayList<JavaEntity>();
		
		//获取workspace路径
		String workspace = FixUtil.getWorkspaceRealPath();
		
		//获取workspace下所有工程
		File workspaceFile = new File(workspace);
		if(workspaceFile.exists()) {
			File[] projectFiles = workspaceFile.listFiles();
			if(projectFiles != null && projectFiles.length > 0) {
				//重新初始化
				entities = new ArrayList<JavaEntity>();
				
				for (int i = 0; i < projectFiles.length; i++) {
					//循环处理每个工程
					File projectFile = projectFiles[i];
					
					//定位src文件夹
					String projectSrcPath = projectFile.getAbsolutePath() + "/src";
					
					//递归返回
					List<JavaEntity> returnList = getProjectJavaFiles(projectSrcPath);
					
					//复制
					for (Iterator iterator = returnList.iterator(); iterator
							.hasNext();) {
						JavaEntity javaEntity = (JavaEntity) iterator.next();
						javaEntities.add(javaEntity);
					}
				}
			}
		}
		return javaEntities;
	}
	
	/**
	 * 根据所输入的字符串返回该工程下所有与java相关的包和类
	 * @param inputString
	 * @param filePath
	 * @return
	 */
	public static List<String> getAssociationFromInputString(String inputString, String filePath) {
		globalList = new ArrayList<String>();
		
		inputString = inputString.replace('.', '\\');
		
		//根据文件路径找到属于哪个工程
		String projectPath = getWebOrLogicProjectRealPathFromFileRealPath(filePath);
		
		//解析该工程下所有的java文件相关的包和类
		File projectFile = new File(projectPath);
		File srcFile = new File(projectPath + "/src/");
		if(projectFile.exists() && srcFile.exists()) {
			File packageFile = new File(srcFile.getAbsolutePath() + "\\" + inputString);
			if(packageFile.exists()) {
				//递归找出其下所有的文件夹和java类
				getChildrenFiles(packageFile.getAbsolutePath(), srcFile.getAbsolutePath());
			}
		}
		return globalList;
	}
	
	/**
	 * 递归找出所有的文件夹和java类
	 * @param filepath
	 */
	private static List<String> getChildrenFiles(String filepath, String srcPath) {
		//获取hiddenName
		String hiddenName = FixUtil.getHiddenFileName();

		File file = new File(filepath.replace('\\', '/'));
		if(file.exists()) {
			File[] files = file.listFiles();
			if(files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File tmpFile = files[i];
					if(tmpFile.isDirectory()) {
						if(tmpFile.getAbsolutePath().indexOf(hiddenName) == -1) {
							globalList = getChildrenFiles(tmpFile.getAbsolutePath().replace('\\', '/'), srcPath);
						}
					} else if(tmpFile.isFile()) {
						//如果是java文件，封装数据
						String path = tmpFile.getAbsolutePath().substring(srcPath.length() + 1, 
								tmpFile.getAbsolutePath().lastIndexOf(".")).replace('\\', '.');
						globalList.add(path);
					}
				}
			}
		}
		return globalList;
	}
	
	/**
	 * 递归获取每个工程src目录下的所有java类文件
	 * @param folderPath
	 */
	private static List<JavaEntity> getProjectJavaFiles(String folderPath) {
		//获取hiddenName
		String hiddenName = FixUtil.getHiddenFileName();

		File file = new File(folderPath.replace('\\', '/'));
		if(file.exists()) {
			File[] files = file.listFiles();
			if(files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File tmpFile = files[i];
					if(tmpFile.isDirectory()) {
						if(tmpFile.getAbsolutePath().indexOf(hiddenName) == -1) {
							entities = getProjectJavaFiles(tmpFile.getAbsolutePath().replace('\\', '/'));
						}
					} else if(tmpFile.isFile()) {
						//如果是java文件，封装数据
						JavaEntity entity = new JavaEntity();
						entity.setName(tmpFile.getName());
						entity.setRealPath(tmpFile.getAbsolutePath().replace('\\', '/'));
						
						entities.add(entity);
					}
				}
			}
		}
		return entities;
	}
	
	public static void main(String[] args) throws Exception {
//		int c;
//		URL hp = new URL("http://www.baidu.com");
//		URLConnection hpCon = null;
//		hpCon = hp.openConnection();
//
//		System.out.println("Date:   " + new Date(hpCon.getDate()));
//		System.out.println("Content-Type:   " + hpCon.getContentType());
//		System.out.println("Expires:   " + hpCon.getExpiration());
//		System.out.println("Last-Modified:   "
//				+ new Date(hpCon.getLastModified()));
//		int len = hpCon.getContentLength();
//		System.out.println("Content-Length:   " + len);
//		if (len > 0) {
//			System.out.println("===   Content   === ");
//			InputStream input = hpCon.getInputStream();
//			int i = len;
//			while (((c = input.read()) != -1) && (--i > 0)) {
//				System.out.print((char) c);
//			}
//			input.close();
//		} else {
//			System.out.println("   No   content   Available ");
//		}
		
//		List<JavaEntity> entities = getProjectJavaFiles("C:\\work\\eclipse\\runtime-EclipseApplication\\fixweb\\src");
//		System.out.println();
	}
}
