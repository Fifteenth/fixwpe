/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * CacheUtil.java
 */
package com.founder.fix.ocx.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.founder.fix.apputil.util.XmlUtil;
import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;
import com.founder.fix.ocx.platformdesigner.JavaEntity.java.Cache;
import com.founder.fix.ocx.platformdesigner.JavaEntity.java.CacheManager;
import com.founder.fix.ocx.platformdesigner.interfaces.FixStudioInterface;

/**
 * [类名]<br>
 * CacheUtil.java<br>
 * <br>
 * [功能概要]<br>
 * 缓存工具类
 * <br>
 * [变更履历]<br>
 * 
 * <br>
 * 2011-7-25 ver1.0 <br>
 * <br>
 * 
 * @作者 wangzhiwei
 * 
 */
public class CacheUtil {
	
	public static String BIZOBJ = "bizobj";
	
	public static String HIDDENFILE = "hiddenFile";

	/**
	 * 
	 */
	private CacheUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 将数据放入缓存
	 * @param key
	 * @param object
	 */
	public static void putCache(String key, Object object) {
		Cache cache = new Cache();
		cache.setValue(object);
		CacheManager.putCache(key, cache);
	}
	
	/**
	 * 取缓存数据
	 * @param key
	 */
	public static Object getCache(String key) {
		Cache cache = CacheManager.getCacheInfo(key);
		if(cache != null) {
			return cache.getValue();
		}
		return null;
	}
	
	/**
	 * 将property.xml文件中定义的过滤文件夹名称放入缓存
	 */
	public static void putHiddenFile2Cache() {
		String filePath = FixUtil.getPluginDevelopRealPath("/conf/property.xml");
		try {
			Document document = XmlUtil.read(filePath);
			Element root = document.getRootElement();
			String hiddenFiles = root.element("platform").element("hiddenfile").getTextTrim();
			if(hiddenFiles != null) {
				//放入缓存
				putCache(HIDDENFILE, hiddenFiles);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据业务对象id获取其文件的全路径
	 * @param bizobjId
	 * @return
	 */
	public static String getBizobjRealPathFromId(String bizobjId) {
		if(bizobjId != null && !bizobjId.equals("")) {
			//获取业务对象列表
			List<BizobjEntity> bizobjs = FixStudioInterface._bizobjList;
			if(bizobjs != null) {
				for (Iterator iterator = bizobjs.iterator(); iterator.hasNext();) {
					BizobjEntity bizobjEntity = (BizobjEntity) iterator.next();
					//将传入的id与缓存中对比找到该业务对象
					if(bizobjId.equals(bizobjEntity.getId())) {
						return bizobjEntity.getRealFullPath();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 获取缓存中所有业务对象文件全路径
	 * @return
	 */
	public static List<BizobjEntity> getAllBizobjRealPath() {
		//获取业务对象列表
		List<BizobjEntity> bizobjPaths = (List<BizobjEntity>) getCache(BIZOBJ);
		if(bizobjPaths != null && bizobjPaths.size() > 0) {
			return bizobjPaths;
		}
		return null;
	}

	/**
	 * 新增业务对象进缓存
	 * @param fileFullPath
	 */
	public static boolean addBizobj2Cache(String bizobjId, String bizobjName, String fileFullPath) {
		//从全路径获取文件名作为缓存id
//		String fileName = FixUtil.getFileNameFromFileFullPath(fileFullPath);
		//获取业务对象列表
		List<BizobjEntity> bizobjPaths = (List<BizobjEntity>) getCache(BIZOBJ);
		if(bizobjPaths == null) {
			bizobjPaths = new ArrayList<BizobjEntity>();
		}
		//实例化业务对象实体类 
		BizobjEntity entity = new BizobjEntity();
		entity.setId(bizobjId);
		entity.setName(bizobjName);
		entity.setRealFullPath(fileFullPath);
		//添加实体类
		bizobjPaths.add(entity);
		//加入缓存
		putCache(BIZOBJ, bizobjPaths);
		
		//加入fxiflow缓存
		addFixFlowDataObj(bizobjPaths);
		
		return true;
	}
	
	/**
	 * 从缓存中删除业务对象
	 * @param bizobjId
	 * @return
	 */
	public static boolean deleteBizobjFromCache(String bizobjId) {
		if(bizobjId == null || bizobjId.equals("")) {
			return false;
		}
		//获取业务对象列表
		List<BizobjEntity> bizobjPaths = (List<BizobjEntity>) getCache(BIZOBJ);
		if(bizobjPaths != null) {
			for (Iterator iterator = bizobjPaths.iterator(); iterator.hasNext();) {
				BizobjEntity bizobjEntity = (BizobjEntity) iterator.next();
				if(bizobjEntity.getId().equals(bizobjId)) {
					//删除业务对象
					bizobjPaths.remove(bizobjEntity);
					putCache(BIZOBJ, bizobjPaths);
					
					//刪除流程緩存
//					DataObjCache.deleteDataObj(bizobjId);
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 初始化所有业务对象的全路径放入缓存
	 */
	public static void initAllBizobjRealPath2Cache() {
		//获取缓存中需要过滤的文件夹名称
		String hiddenFileName = FixUtil.getHiddenFileName();
		
		//存放业务对象列表
		List<BizobjEntity> bizobjList = new ArrayList<BizobjEntity>();
		//读取工程目录下所有logic工程
		String workspacePath = FixUtil.getWorkspaceRealPath();
		File workspaceFile = new File(workspacePath);
		if(workspaceFile.exists() && workspaceFile.isDirectory() 
				&& hiddenFileName.indexOf(workspaceFile.getName()) == -1) {
			File[] logicProjects = workspaceFile.listFiles();
			if(logicProjects != null && logicProjects.length > 0) {
				for (int i = 0; i < logicProjects.length; i++) {
					File logicProject = logicProjects[i];
					//找到logic工程
					if(logicProject != null && !FixStudioInterface.isWebProject(logicProject.getAbsolutePath())) {
						//定位到模块目录下
						String modelsPath = workspacePath + logicProject.getName() + "/app/modules/";
						File modelsFile = new File(modelsPath);
						if(modelsFile.exists() && modelsFile.isDirectory()
								&& hiddenFileName.indexOf(workspaceFile.getName()) == -1) {
							File[] models = modelsFile.listFiles();
							for (int j = 0; j < models.length; j++) {
								File model = models[j];
								//定位到bizobj目录下
								String bizobjsPath = modelsPath + model.getName() + "/business/bizobj/";
								File bizobjsFile = new File(bizobjsPath);
								if(bizobjsFile != null && bizobjsFile.isDirectory()
										&& hiddenFileName.indexOf(workspaceFile.getName()) == -1) {
									File[] bizobjs = bizobjsFile.listFiles();
									if(bizobjs != null && bizobjs.length > 0) {
										for (int k = 0; k < bizobjs.length; k++) {
											File bizobj = bizobjs[k];
											if(bizobj != null && hiddenFileName.indexOf(bizobj.getName()) == -1
													&& bizobj.isFile()) {
												//获取文件的信息
												String bizobjId = bizobj.getName().substring(0, bizobj.getName().lastIndexOf(".")); //业务对象名称
												String bizobjRealPath = bizobj.getAbsolutePath(); //文件绝对路径
												bizobjRealPath = bizobjRealPath.replace('\\', '/'); 
												//创建实体对象
												BizobjEntity entity = new BizobjEntity();
												entity.setId(bizobjId);
												entity.setName(getBizobjName(bizobjRealPath));
												entity.setRealFullPath(bizobjRealPath);
												//放入业务对象列表中
												bizobjList.add(entity);
											}
										}
									}
								}
							}
						}
					}
					
					//找到web工程
					if(logicProject != null && FixStudioInterface.isWebProject(logicProject.getAbsolutePath())) {
						//定位到模块目录下
						String modelsPath = workspacePath + logicProject.getName() + "/webapp/modules/";
						File modelsFile = new File(modelsPath);
						if(modelsFile.exists() && modelsFile.isDirectory()
								&& hiddenFileName.indexOf(workspaceFile.getName()) == -1) {
							File[] models = modelsFile.listFiles();
							for (int j = 0; j < models.length; j++) {
								File model = models[j];
								//定位到bizobj目录下
								String bizobjsPath = modelsPath + model.getName() + "/business/bizobj/";
								File bizobjsFile = new File(bizobjsPath);
								if(bizobjsFile != null && bizobjsFile.isDirectory()
										&& hiddenFileName.indexOf(workspaceFile.getName()) == -1) {
									File[] bizobjs = bizobjsFile.listFiles();
									if(bizobjs != null && bizobjs.length > 0) {
										for (int k = 0; k < bizobjs.length; k++) {
											File bizobj = bizobjs[k];
											if(bizobj != null && bizobj.isFile()
													&& hiddenFileName.indexOf(bizobj.getName()) == -1
													&& !bizobj.getName().startsWith(".DS")) {
												//获取文件的信息
												String bizobjId = bizobj.getName().substring(0, bizobj.getName().lastIndexOf(".")); //业务对象名称
												String bizobjRealPath = bizobj.getAbsolutePath(); //文件绝对路径
												bizobjRealPath = bizobjRealPath.replace('\\', '/'); 
												//创建实体对象
												BizobjEntity entity = new BizobjEntity();
												entity.setId(bizobjId);
												entity.setName(getBizobjName(bizobjRealPath));
												entity.setRealFullPath(bizobjRealPath);
												//放入业务对象列表中
												bizobjList.add(entity);
											}
										}
									}
								}
							}
						}
					}
				}
				//放入缓存
				CacheUtil.putCache(BIZOBJ, bizobjList);
				
//				DataObjCache.getDataObjImports().clear();
				//放入fixflow 
				addFixFlowDataObj(bizobjList);
			}
		}
	}
	
	/**
	 * 将业务对象放入流程中
	 * @param bizobjEntities
	 */
	private static void addFixFlowDataObj(List<BizobjEntity> bizobjEntities) {
		if(bizobjEntities != null && bizobjEntities.size() > 0) {
//			for (int i = 0; i < DataObjCache.getDataObjImports().size();) {
//				DataObjCache.getDataObjImports().remove(i);
//			}
//			
//			for (BizobjEntity bizobjEntity : bizobjEntities) {
//				DataObjImportImpl dataObjImport = new DataObjImportImpl();
//				dataObjImport.setId(bizobjEntity.getId());
//				dataObjImport.setName(bizobjEntity.getName());
//				dataObjImport.setType("BizObj业务对象");
//				dataObjImport.setFilePath(bizobjEntity.getRealFullPath());
//
//				DataObjCache.getDataObjImports().add(dataObjImport);
//			}
		}
	}
	
	/**
	 * 读取.biz文件获取对象名称
	 * @param bizobjRealPath
	 */
	private static String getBizobjName(String bizobjRealPath) {
		try {
			Document document = XmlUtil.read(bizobjRealPath);
			Element root = document.getRootElement();
			return root.attributeValue("name");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return "";
	}
}
