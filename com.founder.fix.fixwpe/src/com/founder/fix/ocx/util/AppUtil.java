/**
 * @Title: AppUtil.java
 * @Package com.founder.fix.studio.platformdesigner.utils
 * @Description: TODO
 * Copyright: Copyright (c) 2012 
 * Company:方正国际软件有限公司
 * 
 * @author Comsys-Administrator
 * @date 2012-4-1 上午09:59:34
 * @version v1.0
 */
package com.founder.fix.ocx.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;


import com.founder.fix.apputil.to.bizobj.ObjServiceTo;
import com.founder.fix.apputil.to.view.BaseViewTo;
import com.founder.fix.apputil.to.view.FormViewTo;
import com.founder.fix.apputil.to.view.UniformViewTo;
import com.founder.fix.apputil.util.BizObjectUtil;
import com.founder.fix.apputil.util.ObjViewUtil;
import com.founder.fix.apputil.util.XmlUtil;
import com.founder.fix.ocx.platformdesigner.interfaces.FixLoger;
import com.founder.fix.ocx.platformdesigner.interfaces.FixStudioInterface;

/**
 * @ClassName: AppUtil
 * @Description: TODO
 * @author Comsys-wangzhiwei
 * @date 2012-4-1 上午09:59:34
 *
 */
public class AppUtil {

	/**
	 * 创建一个新的实例 AppUtil. 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */
	public AppUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	  * getAllInternationVersion(获取所有国际化版本名称)
	  * @Title: getAllInternationVersion
	  * @Description: TODO
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String[]    返回类型
	  * @throws
	 */
	public static String[] getAllInternationVersion() throws Exception {
		String[] array = null;
		List<String> list = new ArrayList<String>();
		IFolder resource = FixUtil.getWebProject().getFolder("webconf/resource/");
		if(resource.exists()) {
			IResource[] resources = resource.members();
			if(resources != null && resources.length > 0) {
				for (int i = 0; i < resources.length; i++) {
					IResource iResource = resources[i];
					if(iResource instanceof IFolder) {
						IFolder versionFolder = (IFolder) iResource;
						list.add(versionFolder.getName());
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
	 * 获取所有RMI地址配置列表
	 * @return
	 */
//	public static List<RMIEntity> getAllRMIAddress() {
//		List<RMIEntity> list = new ArrayList<RMIEntity>();;
//		IFile file = FixUtil.getWebProject().getFile("webconf/designer/rmiconfig.xml");
//		if(file.exists()) {
//			try {
//				Document document = XmlUtil.read(file.getLocation().toFile(), "UTF-8");
//				Element root = document.getRootElement();
//				List<Element> elements = root.elements("address");
//				if(elements != null && elements.size() > 0) {
//					for (Iterator iterator = elements.iterator(); iterator
//							.hasNext();) {
//						Element element = (Element) iterator.next();
//						RMIEntity entity = new RMIEntity();
//						entity.setRMIAddress(element.getTextTrim());
//						list.add(entity);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				MessageDialog.openError(null, "异常", e.toString());
//			}
//		}
//		if(list.size() == 0) {
//			RMIEntity entity = new RMIEntity();
//			entity.setRMIAddress("127.0.0.1:8080");
//			list.add(entity);
//		}
//		return list;
//	}
	
	/**
	  * loadProperty(加载property文件)
	  * @Title: loadProperty
	  * @Description: TODO
	  * @param @param property
	  * @param @return    设定文件
	  * @return Map<String,String>    返回类型
	  * @throws
	 */
	public static Map<String, String> loadProperty(File property) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(property));
			String line = reader.readLine();
			while (line != null && !line.equals("")
					&&line.contains("=")
					&&line.length()>line.indexOf("=")+"=".length()) {
				String[] array = line.split("=");
				if(array != null && array.length == 2) {
					map.put(line.split("=")[0], line.split("=")[1]);
				} else if(array != null && array.length != 2) {
					map.put(line.split("=")[0], "");
				}
				line = reader.readLine();
			}
		} catch (Exception e) {
			MessageDialog.openError(null, "异常", e.toString());
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	  * saveProperty(保存property文件)
	  * @Title: saveProperty
	  * @Description: TODO
	  * @param @param property
	  * @param @param value    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public static void saveProperty(File property, Map<String, String> map) {
		String value = "";
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			value += (key + "=" + map.get(key)) + "\n";
		}
		System.out.println(property.getAbsolutePath());
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(property));
			writer.write(value);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(null, "异常", e.toString());
		}
	}
	
	/**
	 * java执行windows命令行
	 * @param command
	 */
	public static synchronized void javaConsoleCommand(String command) {
		try {
			FixLoger.debug("正在进行转码...命令行：" + command);
			Runtime.getRuntime().exec(command);
			FixLoger.debug("转码成功...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 记录表单国际化
	 * @param bizobjId
	 * @param htmlName
	 * @param key
	 * @param value
	 */
	public static void saveHtmlInternational(String bizobjId, String htmlName, 
			String key, String value) {
		String _path = getInternationalDefauldFolder().getFolder(bizobjId + "/" + htmlName +
				"_html.properties").getLocation().toString();
		File _file = new File(_path);
		if(!_file.exists()) {
			try {
				_file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		try {
			Map<String, String> map = loadProperty(_file);
			map.put(key, value);
			
			saveProperty(_file, map);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		FixStudioInterface.refreshTruthFileFromFileFullPath(_path);
	}
	
	/**
	 * 返回国际化文件所在文件夹
	 * @return
	 */
	public static IFolder getInternationalDefauldFolder() {
		return FixUtil.getWebProject().getFolder("webconf/resource/defauld/bizobjects/");
	}
	
	/**
	 * 
	  * getBizobjPathFromViewPath(通过视图的文件路径返回其对应的业务对象的文件路径)
	  * @Title: getBizobjPathFromViewPath
	  * @Description: TODO
	  * @param @param viewPath
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String getBizobjPathFromViewPath(String viewPath) {
		if(viewPath == null || viewPath.equals("")) {
			return "";
		} else {
			viewPath.replace('\\', '/');
		}
		
		String bizjobjName = viewPath.substring(0, viewPath.lastIndexOf("/"));
		bizjobjName = bizjobjName.substring(bizjobjName.lastIndexOf("/") + 1, bizjobjName.lastIndexOf("_"));
		
		String bizobjPath = viewPath.substring(0, viewPath.lastIndexOf("formview")).replace('\\', '/')
					+ "business/bizobj/" + bizjobjName + ".biz";
		
		return bizobjPath;
	}

	/**
	  * 通过业务对象的文件路径返回其方法的文件夹全路径
	  * getBizobjServiceFolderPathFromBizobjFilePath(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getBizobjServiceFolderPathFromBizobjFilePath
	  * @Description: TODO
	  * @param @param bizobjPath
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String getBizobjServiceFolderPathFromBizobjFilePath(String bizobjPath) {
		return bizobjPath.substring(0, bizobjPath.lastIndexOf(".")) + "_SVZ";
	}
	
	/**
	  * 通过业务对象的ID返回其方法的文件夹全路径
	  * getBizobjServiceFolderPathFromBizobjId(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getBizobjServiceFolderPathFromBizobjId
	  * @Description: TODO
	  * @param @param bizobjId
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String getBizobjServiceFolderPathFromBizobjId(String bizobjId) {
		//调用缓存工具类
		String bizobjFilePath = CacheUtil.getBizobjRealPathFromId(bizobjId);
		return getBizobjServiceFolderPathFromBizobjFilePath(bizobjFilePath);
	}
	
	/**
	  * 通过业务对象的文件路径返回其视图的文件夹全路径
	  * getBizobjServiceFolderPathFromBizobjFilePath(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getBizobjServiceFolderPathFromBizobjFilePath
	  * @Description: TODO
	  * @param @param bizobjPath
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String getBizobjViewsFolderPathFromBizobjFilePath(String bizobjPath) {
		if(bizobjPath == null || bizobjPath.equals("")) {
			return "";
		}
		
		String bizobjName = bizobjPath.substring(bizobjPath.lastIndexOf("/") + 1,
				bizobjPath.lastIndexOf("."));
		
		bizobjPath = bizobjPath.substring(0, bizobjPath.lastIndexOf("/"));
		bizobjPath = bizobjPath.substring(0, bizobjPath.lastIndexOf("/"));
		bizobjPath = bizobjPath.substring(0, bizobjPath.lastIndexOf("/"));
		bizobjPath = bizobjPath + "/formview/";
		
		return bizobjPath + bizobjName + "_VIW";
	}
	
	/**
	  * 通过业务对象的ID返回其视图的文件夹全路径
	  * getBizobjServiceFolderPathFromBizobjId(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getBizobjServiceFolderPathFromBizobjId
	  * @Description: TODO
	  * @param @param bizobjId
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String getBizobjViewsFolderPathFromBizobjId(String bizobjId) {
		//调用缓存工具类
		String bizobjFilePath = CacheUtil.getBizobjRealPathFromId(bizobjId);
		return getBizobjViewsFolderPathFromBizobjFilePath(bizobjFilePath);
	}
	
	/**
	  * 通过业务对象的ID返回其所有方法的TO对象
	  * getObjServicesFromBizobjId(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getObjServicesFromBizobjId
	  * @Description: TODO
	  * @param @param bizobjId
	  * @param @return    设定文件
	  * @return List<ObjServiceTo>    返回类型
	  * @throws
	 */
	public static List<ObjServiceTo> getObjServicesFromBizobjId(String bizobjId) {
		List<ObjServiceTo> services = new ArrayList<ObjServiceTo>();
		
		//调用缓存工具类
		String serviceFolderPath = getBizobjServiceFolderPathFromBizobjId(bizobjId);
		
		File serviceFolder = new File(serviceFolderPath);
		if(serviceFolder.exists() && serviceFolder.isDirectory()) {
			File[] files = serviceFolder.listFiles();
			if(files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if(file.isFile()) {
						try {
							ObjServiceTo service = BizObjectUtil.readDataObjServiceInfo(new FileInputStream(
									file.getAbsoluteFile()));
							services.add(service);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
			}
		}
		
		return services;
	}
	
	/**
	  * 通过业务对象的ID返回其所有视图的TO对象
	  * getObjServicesFromBizobjId(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getObjServicesFromBizobjId
	  * @Description: TODO
	  * @param @param bizobjId
	  * @param @return    设定文件
	  * @return List<ObjServiceTo>    返回类型
	  * @throws
	 */
	public static List<BaseViewTo> getObjViewsFromBizobjId(String bizobjId) {
		List<BaseViewTo> views = new ArrayList<BaseViewTo>();
		
		//调用缓存工具类
		String viewsFolderPath = getBizobjViewsFolderPathFromBizobjId(bizobjId);
		
		File viewFolder = new File(viewsFolderPath);
		if(viewFolder.exists() && viewFolder.isDirectory()) {
			File[] files = viewFolder.listFiles();
			if(files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if(file.isFile()) {
						try {
							BaseViewTo baseViewTo = null;
							if(file.getName().endsWith(".ufm")) {
								baseViewTo = ObjViewUtil.readUniformView(new FileInputStream(file),
										null);
							} else {
								baseViewTo = ObjViewUtil.readFormView(new FileInputStream(file),
										null);
							}
							views.add(baseViewTo);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
			}
		}
		
		return views;
	}
	
	/**
	  * 通过业务对象的ID返回其列表视图的TO对象
	  * getObjUniformViewsFromBizobjId(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getObjServicesFromBizobjId
	  * @Description: TODO
	  * @param @param bizobjId
	  * @param @return    设定文件
	  * @return List<ObjServiceTo>    返回类型
	  * @throws
	 */
	public static List<UniformViewTo> getObjUniformViewsFromBizobjId(String bizobjId) {
		if(bizobjId == null || bizobjId.equals("")) {
			return null;
		}
		
		List<UniformViewTo> views = new ArrayList<UniformViewTo>();
		
		//调用缓存工具类
		String viewsFolderPath = getBizobjViewsFolderPathFromBizobjId(bizobjId);
		
		File viewFolder = new File(viewsFolderPath);
		if(viewFolder.exists() && viewFolder.isDirectory()) {
			File[] files = viewFolder.listFiles();
			if(files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if(file.isFile() && file.getName().endsWith(".ufm")) {
						try {
							UniformViewTo uniformView = ObjViewUtil.readUniformView(new FileInputStream(file),
									null);
							views.add(uniformView);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
			}
		}
		
		return views;
	}
	
	/**
	  * 通过业务对象的ID返回其表单视图的TO对象
	  * getObjFormViewsFromBizobjId(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getObjServicesFromBizobjId
	  * @Description: TODO
	  * @param @param bizobjId
	  * @param @return    设定文件
	  * @return List<ObjServiceTo>    返回类型
	  * @throws
	 */
	public static List<FormViewTo> getObjFormViewsFromBizobjId(String bizobjId) {
		List<FormViewTo> views = new ArrayList<FormViewTo>();
		
		//调用缓存工具类
		String viewsFolderPath = getBizobjViewsFolderPathFromBizobjId(bizobjId);
		
		File viewFolder = new File(viewsFolderPath);
		if(viewFolder.exists() && viewFolder.isDirectory()) {
			File[] files = viewFolder.listFiles();
			if(files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if(file.isFile() && file.getName().endsWith(".fom")) {
						try {
							FormViewTo formViewTo = ObjViewUtil.readFormView(new FileInputStream(file),
									null);
							views.add(formViewTo);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
			}
		}
		
		return views;
	}
	
	/**
	  * getComConfigPathFromId(这里用一句话描述这个方法的作用)
	  * @Title: getComConfigPathFromId
	  * @Description: TODO
	  * @param @param id
	  * @param @param comType(js、service、function、biz)
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String getComConfigPathFromId(String id, String comType) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		if(projects != null && projects.length > 0) {
			for (int i = 0; i < projects.length; i++) {
				IProject iProject = projects[i];
				//web工程
				IFolder folder = iProject.getFolder("WebRoot");
				if(folder.exists()) {
					return (iProject.getLocation().toString() + "/components/" + comType + "/" + id + ".xml");							
				}
			}
		}
		return "";
	}
	
	
	
	/**
	  * getJsComConfigPathFromId(通过JS组件的ID获取其配置文件路径)
	  * @Title: getJsComConfigPathFromId
	  * @Description: TODO
	  * @param @param id
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	public static String getIncludeJsComConfigPathFromId(String id) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		if(projects != null && projects.length > 0) {
			for (int i = 0; i < projects.length; i++) {
				IProject iProject = projects[i];
				//web工程
				IFolder folder = iProject.getFolder("WebRoot");
				if(folder.exists()) {
					return (iProject.getLocation().toString() + "/WebRoot/components/"+ id);
				}
			}
		}
		return "";
	}
	
	
	public static String getIncludeServiceComConfigPathFromId(String id) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		if(projects != null && projects.length > 0) {
			for (int i = 0; i < projects.length; i++) {
				IProject iProject = projects[i];
				//web工程
				IFolder folder = iProject.getFolder("WebRoot");
				if(folder.exists()) {
					return (iProject.getLocation().toString() + "/webapp/globalservices/"+ id );
				}
			}
		}
		return "";
	}
	
	/**
	 * 
	  * loadJavaClass(加载javaclass类)
	  * @Title: loadJavaClass
	  * @Description: TODO
	  * @param @param jarPath
	  * @param @param className
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return Object    返回类型
	  * @throws
	 */
//	public static Class loadJavaClass(String jarPath, String className)
//			throws Exception {
//		// jarPath = FixUtil.getWorkspaceRealPath() +
//		// "fixweb/WebRoot/WEB-INF/lib/webcore.jar";
//
//		String sDestPath = FixUtil.getEclipseRealRootPath() + "temp/webcore/";
//		if (sDestPath.startsWith("/")) {
//			sDestPath = sDestPath.substring(1);
//		}
//		ReadZipFile.zipToFile(jarPath, sDestPath);
//
//		URL[] urls = new URL[] { new URL("file:/" + sDestPath) };
//		URLClassLoader loader = new URLClassLoader(urls);
//		Class clas = loader.loadClass(className);
//		// clas.getDeclaredMethods();
//		return clas;
//	}
}
