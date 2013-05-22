/**
 * Copyright c FOUNDER CORPORATION 2011 All Rights Reserved.
 * FixUtil.java
 */
package com.founder.fix.ocx.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
//import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
//import org.eclipse.bpmn2.modeler.ui.editor.BPMN2MultiPageEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.WorkbenchHelp;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

import com.founder.fix.apputil.Const;
import com.founder.fix.apputil.to.bizobj.BizObjTo;
import com.founder.fix.apputil.to.bizobj.ObjServiceOutColumnTo;
import com.founder.fix.apputil.to.bizobj.ObjServiceOutTo;
import com.founder.fix.apputil.to.bizobj.ObjServiceParameterTo;
import com.founder.fix.apputil.to.bizobj.ObjServiceTo;
import com.founder.fix.apputil.to.bizobj.ObjServiceVerifyTo;
import com.founder.fix.apputil.to.view.ObjViewTo;
import com.founder.fix.apputil.util.BizObjectUtil;
import com.founder.fix.apputil.util.ObjViewUtil;
import com.founder.fix.apputil.util.XmlUtil;
import com.founder.fix.base.platformdesigner.Entity.project.BizobjEntity;
import com.founder.fix.base.wpe.FixImageProvider;
import com.founder.fix.fixwpe.Activator;
import com.founder.fix.ocx.platformdesigner.JavaEntity.java.Cache;
import com.founder.fix.ocx.platformdesigner.JavaEntity.java.CacheManager;
import com.founder.fix.ocx.platformdesigner.interfaces.FixLoger;
import com.founder.fix.ocx.platformdesigner.interfaces.FixStudioInterface;
import com.founder.fix.ocx.platformdesigner.trees.EntityElement;
import com.founder.fix.ocx.platformdesigner.trees.ITreeElement;
import com.founder.fix.ocx.platformdesigner.trees.TreeViewerComparator;

/**
 * [类名]<br>
 * FixUtil.java<br>
 * <br>
 * [功能概要]<br>
 * 
 * <br>
 * [变更履历]<br>
 * 
 * <br>
 * 2011-6-20 ver1.0 <br>
 * <br>
 * 
 * @作者 wangzhiwei
 * 
 */
public class FixUtil {

	// 修改编辑器名称时获取的树节点
	private static EntityElement selectElement = null;

	/**
	 * 
	 */
	private FixUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	  * fixValidator
	  * (1.	统一不允许输入特殊字符,
		 2.	Id类的只能有_ 字母和数字，长度不超过30 Name类的长度不超过20个汉字)
	  * @Title: fixValidator
	  * @Description: TODO
	  * @param @param value
	  * @param @param type (id,name)
	  * @param @return    设定文件
	  * @return boolean    返回类型
	  * @throws
	 */
	public static String fixValidator(String text, String type) {
		if(type.equals("id")) {
			//长度不能超过30字符
			if(text.length() > 30) {
				return "长度不能超过30字符";
			}
			//不能以数字开头
			try {
				Integer.parseInt(String.valueOf(text.charAt(0)));
				return "不能以数字开头";
			} catch (Exception e) {
			}
			//不允许输入特殊字符
			for (int i = 0; i < text.length(); i++) {
				if(!((text.charAt(i) >= 'a' && text.charAt(i) <= 'z')
						|| (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z')
						|| (text.charAt(i) >= '0' && text.charAt(i) <= '9')
						|| text.charAt(i) == '_')) {
					return "不允许输入特殊字符";
				}
			}
		} else {
			//长度不能超过20字符
			if(text.length() > 20) {
				return "长度不能超过20字符";
			}
			//不能输入特殊字符
			for (int i = 0; i < text.length(); i++) {
				if(!((text.charAt(i) >= 'a' && text.charAt(i) <= 'z')
						|| (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z')
						|| (text.charAt(i) >= '0' && text.charAt(i) <= '9')
						|| text.charAt(i) == '_' || text.charAt(i) == '-'
						|| IsChineseOrEnglish.isChinese(text.charAt(i)))) {
					return "不允许输入特殊字符";
				}
			}
		}
		return "";
	}
	
	/**
	 * 打开本地文件
	 * @param filePath
	 */
	public static void openLocalFile(String filePath) {
		try {
			Desktop.getDesktop().open(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * compareChineseChars(中文拼音排序)
	  * @Title: compareChineseChars
	  * @Description: TODO
	  * @param @param first
	  * @param @param second
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public static int compareChineseChars(String first, String second) {
		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
		return cmp.compare(first, second);
	}
	
	/**
	  * isChineseString(判断字符串是否为中文)
	  * @Title: isChineseString
	  * @Description: TODO
	  * @param @param text
	  * @param @return    设定文件
	  * @return boolean    返回类型
	  * @throws
	 */
	public static boolean isChineseString(String text) {
		for (int i = 0; i < text.length(); i++) {
			if(!((text.charAt(i) >= 'a' && text.charAt(i) <= 'z')
					|| (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z')
					|| (text.charAt(i) >= '0' && text.charAt(i) <= '9')
					|| text.charAt(i) == '_')) {
				return true;
			}
		}
		return false;
	}
	
	/**
	  * getFileIconFromFileName(根据文件名获取其对应的图标)
	  * @Title: getFileIconFromFileName
	  * @Description: TODO
	  * @param @param fileName
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public static int getFileIconFromFileName(String fileName) {
		int icon = 0;
		if(fileName.endsWith(".js")) {
			icon = EntityElement.JS;
		} else if(fileName.endsWith(".htm")
				|| fileName.endsWith(".html")) {
			icon = EntityElement.HTML;
		} else if(fileName.endsWith(".xml")) {
			icon = EntityElement.XML;
		} else if(fileName.endsWith(".txt")) {
			icon = EntityElement.FILE;
		} else if(fileName.endsWith(".css")) {
			icon = EntityElement.CSS;
		} else if(fileName.endsWith(".java")) {
			icon = EntityElement.JAVA;
		} else if(fileName.endsWith(".gif")
				|| fileName.endsWith(".png")
				|| fileName.endsWith(".bmp")
				|| fileName.endsWith(".jpg")) {
			icon = EntityElement.IMG;
		} else {
			icon = EntityElement.FILE;
		}
		return icon;
	}

	/**
	 * 文件拷贝
	 * 
	 * @param oldFilePath
	 * @param newFilePath
	 */
	public static void fileCopy(String oldFilePath, String newFilePath) {
		File file = new File(oldFilePath);
		if (!file.exists()) {
			FixLoger.error(oldFilePath + " 文件不存在. ");
			return;
		}
		File fileb = new File(newFilePath);
		if (file.isFile()) {
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(fileb);
				byte[] bb = new byte[(int) file.length()];
				fis.read(bb);
				fos.write(bb);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (file.isDirectory() 
				&& !file.getName().equals(FixUtil.getHiddenFileName())) {
			if (!fileb.exists()) {
				fileb.mkdir();
			}
			String[] fileList;
			fileList = file.list();
			for (int i = 0; i < fileList.length; i++) {
				fileCopy(oldFilePath + "/" + fileList[i], newFilePath
						+ "/" + fileList[i]);
			}
		}
	}
	
	/**
	  * convertEnum2Array(将枚举转为字符串数据)
	  * @Title: convertEnum2Array
	  * @Description: TODO
	  * @param @param clazz
	  * @param @return    设定文件
	  * @return String[]    返回类型
	  * @throws
	 */
	public static String[] convertEnum2Array(Class clazz) {
		Field[] fields = clazz.getFields();
		String[] array = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			array[i] = fields[i].getName();
		}
		return array;
	}
	
	/**
	 * 
	  * deleteFile(删除文件包含文件夹)
	  * @Title: deleteFile
	  * @Description: TODO
	  * @param @param file    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public static  void deleteFile(File file) {
		if(file.exists()) {
			File[] children = file.listFiles();
			if(children != null && children.length > 0) {
				for (int i = 0; i < children.length; i++) {
					File child = children[i];
					deleteFile(child);
				}
				deleteFile(file);
			} else {
				file.delete();
			}
		}
	}
	
	/**
	  * deleteFile(删除平台文件夹)
	  * @Title: deleteFile
	  * @Description: TODO
	  * @param @param filePath    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public static void deleteFolder(String folderPath) {
		//删除文件
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(
				FixUtil.getProjectPath(folderPath));
		IFolder folder = project.getFolder(FixUtil.getFilePath(folderPath));
		try {
			folder.delete(true, true, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * deleteFile(删除平台文件)
	  * @Title: deleteFile
	  * @Description: TODO
	  * @param @param filePath    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public static void deleteFile(String filePath) {
		//删除文件
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(
				FixUtil.getProjectPath(filePath));
		IFile file = project.getFile(FixUtil.getFilePath(filePath));
		try {
			file.delete(true, true, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * getIProject(根据工程名称获取工程对象)
	  * @Title: getIProject
	  * @Description: TODO
	  * @param @param projectName
	  * @param @return    设定文件
	  * @return IProject    返回类型
	  * @throws
	 */
	public static IProject getIProject(String projectName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(projectName);
		return project;
	}
	
	/**
	 * 刷新平台目录树
	 * 
	 * @param projectView
	 */
//	public static void refreshProjectView(ViewPart projectView) {
//		TreeViewer treeViewer = ((ProjectView) projectView).getTreeViewer();
//		treeViewer.setContentProvider(new TreeViewerContentProvider());
//		treeViewer.setLabelProvider(new TreeViewerLabelProvider());
//		treeViewer.setInput(ProjectTreeViewerFactory.reloadProject());
//		treeViewer.setComparator(new TreeViewerComparator());
//		treeViewer.expandToLevel(2);
//	}
	
	/**
	 * 刷新平台目录树
	 * 
	 * @param treeViewer
	 */
//	public static void refreshProjectView(TreeViewer treeViewer) {
//		treeViewer.setInput(ProjectTreeViewerFactory.reloadProject());
//		treeViewer.setComparator(new TreeViewerComparator());
//		treeViewer.expandToLevel(2);
//	}

	/**
	 * 刷新工程
	 * 
	 * @param projectName
	 */
	public static void refreshProject(String projectName) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		try {
			project.open(null);
			project.refreshLocal(IResource.FOLDER, null);
		} catch (CoreException e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 刷新文件夹
	 * 
	 * @param projectName
	 * @param folderName
	 */
	public static void refreshFolder(String projectName, String folderName) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		try {
			project.open(null);
			// 刷新文件夹
			IFolder folder = project.getFolder(folderName);
			folder.refreshLocal(IResource.FOLDER, null);

			// 刷新文件
			IFile file = project.getFile(folderName);
			file.refreshLocal(IResource.FILE, null);
		} catch (CoreException e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 获取缓存中需要过滤的文件夹名称
	 */
	public static String getHiddenFileName() {
		// 获取缓存中需要过滤的文件夹名称
		String hiddenFileName = (String) CacheUtil
				.getCache(CacheUtil.HIDDENFILE);
		if (hiddenFileName == null) {
			hiddenFileName = new String();
		}
		return hiddenFileName;
	}

	/**
	 * 根据图片名称得到一个图像
	 * 
	 * @param fileName
	 * @return
	 */
	public static Image getImageFromURL(String fileName) {
		try {
			String imagePath = FixImageProvider.ICONS_PATH + fileName; // 要读取的图片文件的路径
			ImageDescriptor descriptor = AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID, imagePath); // com.founder.fix.studio是插件Id，Eclipse根据此名称查询对应位置
			return descriptor.createImage();
		} catch (Exception e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据图片名称得到一个图像
	 * 
	 * @param fileName
	 * @return
	 */
	public static ImageDescriptor getImageDescFromURL(String fileName) {
		try {
			String imagePath = FixImageProvider.ICONS_PATH + fileName; // 要读取的图片文件的路径
			ImageDescriptor descriptor = AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID, imagePath); // com.untworld.esystem.launcher是我的插件名称，Eclipse根据此名称查询对应位置
			return descriptor;
		} catch (Exception e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到eclipse内部一个图像(IWorkbenchGraphicConstants类中包含了很多静态图片名)
	 * 
	 * @param fileName
	 * @return
	 */
	public static ImageDescriptor getImageDescFromEclipse(String fileName) {
		ImageDescriptor descriptor = WorkbenchImages
				.getImageDescriptor(fileName);
		return descriptor;
	}

	/**
	 * 获取插件项目下(jar包中)的某个文件目录路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getPluginDevelopRealPath(String filePath) {
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL url = bundle.getResource(filePath);
		try {
			return FileLocator.toFileURL(url).getFile();
		} catch (IOException e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取eclipse的workspace真实目录
	 */
	public static String getWorkspaceRealPath() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// root.getFullPath();
		return root.getLocation().toString() + "/";
		// root.getLocationURI();
	}

	/**
	 * 注册eclipse的工程，在workspace下能自动生成以projectName为名的eclipse工程
	 * 
	 * @param projectName
	 */
	public static void registerProject(String projectName) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (!root.getProject(projectName).exists()) {
			try {
				root.getProject(projectName).create(null);
			} catch (CoreException e) {
				FixLoger.error(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 注册动态帮助
	 * @param control
	 * @param contextId
	 */
	public static void registerDynamicHelp(Control control, String contextId) {
		WorkbenchHelp.setHelp(control, contextId);
	}
	
	/**
	 * 删除eclipse的工程，在workspace下能自动删除以projectName为名的eclipse工程
	 * @param projectName
	 * @param monitor
	 */
	public static void deleteProject(String projectName, IProgressMonitor monitor) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root.getProject(projectName).exists()) {
			try {
				//删除工程
				root.getProject(projectName).delete(true, monitor);
				//刷新工作空间
				root.refreshLocal(IResource.PROJECT, monitor);
			} catch (CoreException e) {
				FixLoger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取当前工程目录
	 */
	public static String getProjectRealPath() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// 获取当前工程名称
		String projectPath = root.getLocation().toString() + "/"
				+ root.getProject();
		return projectPath;
	}

	/**
	 * 获取当前所有Web工程
	 */
	public static List<IProject> getWebProjectList() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] pros = root.getProjects();
		
		List<IProject> webProjectList = new ArrayList<IProject>();
		
		for (int i = 0; i < pros.length; i++) {
			IProject pro = pros[i];
			if (FixStudioInterface.isWebProject(pro.getLocation().toString())) {
				webProjectList.add(pro);
			}
		}
		
		return webProjectList;
	}
	
	/**
	 * 返回web工程
	 * @return
	 */
	public static IProject getWebProject() {
		for (Iterator iterator = getWebProjectList().iterator(); iterator.hasNext();) {
			IProject iProject = (IProject) iterator.next();
			if(iProject.getFile("isMainWeb").exists()) {
				return iProject;
			}
		}
		return getWebProjectList().get(0);
	}

	/**
	 * 获取eclipse安装目录
	 */
	public static String getEclipseRealRootPath() {
		return Platform.getInstallLocation().getURL().getPath();
	}

	/**
	 * 获取选择的树节点
	 * 
	 * @param treeViewer
	 * @return
	 */
	public static ITreeElement getSelectElement(TreeViewer treeViewer) {
		IStructuredSelection selection = (IStructuredSelection) treeViewer
				.getSelection();
				
//		List<ITreeElement> elements = selection.toList();
//		if(elements != null && elements.size() > 0) {
//			for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
//				ITreeElement iTreeElement = (ITreeElement) iterator.next();
//				iTreeElement.getRealName();
//			}
//		}
				
		return (ITreeElement) selection.getFirstElement();
	}

	/**
	 * 验证文件是否存在
	 * 
	 * @param fileRealPath
	 * @return
	 */
	public static boolean hasSameFileName(String fileRealPath) {
		// 如果存在同名文件
		File file = new File(fileRealPath);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 验证业务对象是否存在(全平台bizobj不能同名)
	 * 
	 * @param bizobjId
	 * @return
	 */
	public static boolean hasSameBizobj(String bizobjId) {
		// 从缓存中获取业务对象
		Cache cache = CacheManager.getCacheInfo(CacheUtil.BIZOBJ);
		if (cache != null) {
			// 获取业务对象列表
			List<BizobjEntity> bizobjs = (List<BizobjEntity>) cache.getValue();
			if (bizobjs != null) {
				for (Iterator iterator = bizobjs.iterator(); iterator.hasNext();) {
					BizobjEntity bizobjEntity = (BizobjEntity) iterator.next();
					// 如果存在同名bizobj
					if (bizobjId.equals(bizobjEntity.getId())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 通过文件绝对路径实现业务对象文件名与虚拟名相互获取
	 * 
	 * @param bool
	 *            (true表示通过文件名获取虚拟名、false表示通过虚拟名获取文件名)
	 * @param namePath
	 *            (文件名绝对路径)
	 * @return
	 */
	public static String getBizobjIdOrName(boolean bool, String namePath) {
		// xml读取
		try {
			Document document = XmlUtil.read(new File(namePath), "UTF-8");
			Element root = document.getRootElement();
			if (bool) {
				return root.attributeValue("name");
			} else {
				return root.attributeValue("id");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return namePath.substring(namePath.lastIndexOf("/") + 1);
	}

	/**
	 * 根据文件全路径获取无后缀文件名(如：从c:/eclipse/my.txt返回my)
	 * 
	 * @param fileFullName
	 */
	public static String getFileNameFromFileFullPath(String fileFullName) {
		if (fileFullName != null && !fileFullName.equals("")) {
			fileFullName = fileFullName.replace("\\", "/"); // 替换
			String fileName = fileFullName.substring(
					fileFullName.lastIndexOf("/") + 1,
					fileFullName.lastIndexOf("."));
			return fileName;
		}
		return fileFullName;
	}

	/**
	 * 根据文件全路径获取有后缀文件名(如：从c:/eclipse/my.txt返回my.txt)
	 * 
	 * @param fileFullName
	 */
	public static String getFileFullNameFromFileFullPath(String fileFullName) {
		if (fileFullName != null && !fileFullName.equals("")) {
			fileFullName = fileFullName.replace("\\", "/"); // 替换
			String fileName = fileFullName.substring(
					fileFullName.lastIndexOf("/") + 1, fileFullName.length());
			return fileName;
		}
		return fileFullName;
	}
	
	/**
	 * 获取当前激活的编辑器
	 */
	public static IEditorPart getActiveEditor() {
		try {
			IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			return workbenchPage.getActiveEditor();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取当前所有打开的编辑器
	 */
	public static IEditorPart[] getAllOpenedEditor() {
		try {
			IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			return workbenchPage.getEditors();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据当前打开的编辑器找到其对应的编辑器内部文件的绝对路径
	 * 
	 * @return
	 */
	public static String getFileFullPathFromActiveEditor() {
		// 找到编辑器
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorInput editorInput = null;
		try {
			editorInput = workbenchPage.getActiveEditor().getEditorInput();
		} catch (Exception e) {
		}
		if (editorInput != null) {
			if (editorInput instanceof FileEditorInput) {
				return ((FileEditorInput) editorInput).getURI().getPath();
			} else if (editorInput instanceof DiagramEditorInput) {
				// 获取uriString的范例：'platform:/resource/fixweb/.bpmn2/webapp/modules/system/bpmn/fixflow.bpmn2d#/'
				String uriString = ((DiagramEditorInput) editorInput)
						.getUriString();
				uriString = uriString.substring("platform:/resource/".length(),
						uriString.length());
				String projectName = uriString.substring(0,
						uriString.indexOf("/")); // 截取工程名称**web或者**logic
				uriString = uriString.substring(
						projectName.length() + ".bpmn2".length() + 1,
						uriString.lastIndexOf("2d#/"));
				uriString = FixUtil.getWorkspaceRealPath() + projectName + "/"
						+ uriString;
				return uriString;
			}
		}
		return "";
	}
	
	/**
	 * 根据当前打开的编辑器找到其对应的编辑器内部文件的绝对路径
	 * 
	 * @return
	 */
	public static String getFileFullPathFromEditor(IEditorPart editorPart) {
		IEditorInput editorInput = null;
		try {
			editorInput = editorPart.getEditorInput();
		} catch (Exception e) {
		}
		if (editorInput != null) {
			if (editorInput instanceof FileEditorInput) {
				return ((FileEditorInput) editorInput).getURI().getPath();
			} else if (editorInput instanceof DiagramEditorInput) {
				// 获取uriString的范例：'platform:/resource/fixweb/.bpmn2/webapp/modules/system/bpmn/fixflow.bpmn2d#/'
				String uriString = ((DiagramEditorInput) editorInput)
						.getUriString();
				uriString = uriString.substring("platform:/resource/".length(),
						uriString.length());
				String projectName = uriString.substring(0,
						uriString.indexOf("/")); // 截取工程名称**web或者**logic
				uriString = uriString.substring(
						projectName.length() + ".bpmn2".length() + 1,
						uriString.lastIndexOf("2d#/"));
				uriString = FixUtil.getWorkspaceRealPath() + projectName + "/"
						+ uriString;
				return uriString;
			}
		}
		return "";
	}

	/**
	 * 通过文件全路径找到当前打开的编辑器
	 * 
	 * @param fileRealPath
	 */
//	public static IEditorPart getEditorPartFromFileRealPath(String fileRealPath) {
//		fileRealPath = fileRealPath.replace('\\', '/');
//		IEditorPart editor = null;
//		// 找到打开的并隶属于该文件的编辑器并关闭
//		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		IEditorPart[] editorParts = workbenchPage.getEditors();
//		if (editorParts != null && editorParts.length != 0) {
//			for (int i = 0; i < editorParts.length; i++) {
//				IEditorPart part = editorParts[i];
//				if (part.getEditorInput() instanceof FileEditorInput) {
//					String editerFileRealPath = ((FileEditorInput) part
//							.getEditorInput()).getPath().toFile()
//							.getAbsolutePath();
//					editerFileRealPath = editerFileRealPath.replace('\\', '/');
//					// 找到这个文件对应的编辑器
//					if (fileRealPath.equals(editerFileRealPath)) {
//						editor = part;
//						break;
//					}
//				}
//				if (part.getEditorInput() instanceof FormViewInput) {
//					String editerFileRealPath = ((FormViewInput) part
//							.getEditorInput()).getViewFilePath();
//					editerFileRealPath = editerFileRealPath.replace('\\', '/');
//					// 找到这个文件对应的编辑器
//					if (fileRealPath.equals(editerFileRealPath)) {
//						editor = part;
//						break;
//					}
//				}
//				if (part.getEditorInput() instanceof ServiceMethodInput) {
//					String editerFileRealPath = ((ServiceMethodInput) part
//							.getEditorInput()).getServiceFilePath();
//					editerFileRealPath = editerFileRealPath.replace('\\', '/');
//					// 找到这个文件对应的编辑器
//					if (fileRealPath.equals(editerFileRealPath)) {
//						editor = part;
//						break;
//					}
//				}
//				if (part.getEditorInput() instanceof DiagramEditorInput) {
//					// 获取uriString的范例：'platform:/resource/fixweb/.bpmn2/webapp/modules/system/bpmn/fixflow.bpmn2d#/'
//					String uriString = ((DiagramEditorInput) part
//							.getEditorInput()).getUriString();
//					uriString = uriString.substring(
//							"platform:/resource/".length(), uriString.length());
//					String projectName = uriString.substring(0,
//							uriString.indexOf("/")); // 截取工程名称**web或者**logic
//					uriString = uriString.substring(projectName.length()
//							+ ".bpmn2".length() + 1,
//							uriString.lastIndexOf("2d#/"));
//					uriString = FixUtil.getWorkspaceRealPath() + projectName + "/"
//							+ uriString;
//					// 找到这个文件对应的编辑器
//					if (fileRealPath.equals(uriString)) {
//						editor = part;
//						break;
//					}
//				}
//			}
//		}
//		return editor;
//	}

	/**
	 * 加载并且注册上次eclipse关闭时还存在的编辑器
	 * 
	 * @param treeViewer
	 */
//	public static void registerOpenedEditers(TreeViewer treeViewer) {
//		try {
//			// 找到所有打开的编辑器
//			IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getActivePage();
//			IEditorPart[] editorParts = workbenchPage.getEditors();
//			if (editorParts != null && editorParts.length != 0) {
//				for (int i = 0; i < editorParts.length; i++) {
//					IEditorPart part = editorParts[i];
//					if (part instanceof BizEditor) { // 业务对象编辑器
//						// 获取文件名
//						String fileName = ((BizEditor) part).getPartName();
//						// 放入缓存维持状态
//						CacheUtil.putCache(
//								BizEditor.ID
//										+ "_"
//										+ fileName.substring(0,
//												fileName.lastIndexOf(".")),
//								part);
//					} else if (part instanceof MainEditor) { // 表单编辑器
//						// 获取文件名
//						String fileName = ((MainEditor) part).getPartName();
//						// 放入缓存维持状态
//						CacheUtil.putCache(
//								MainEditor.ID
//										+ "_"
//										+ fileName.substring(0,
//												fileName.lastIndexOf(".")),
//								part);
//					} else if (part instanceof FixFlowEditor) { // 流程编辑器
//						// 获取文件名
//						String fileName = ((FixFlowEditor) part).getPartName();
//						// 放入缓存维持状态
//						CacheUtil.putCache(
//								FixFlowEditor.EDITOR_ID
//										+ "_"
//										+ fileName.substring(0,
//												fileName.lastIndexOf(".")),
//								part);
//					}
//				}
//			}
//		} catch (Exception e) {
//		}
//	}

	/**
	 * 修改业务对象树节点名称
	 * @param bizobjId
	 * @param newElementName
	 */
//	public static void modifyBizobjElementName(String bizobjId, String newElementName) {
//		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		// 获取projectView
//		ProjectView projectView = (ProjectView) workbenchPage
//				.findView(FixPerspective.PROJECT_VIEW);
//		if (projectView != null) {
//			// 遍历treeViewer找到编辑器所对应的树节点
//			TreeViewer treeViewer = projectView.getTreeViewer();
//			// 找到根节点对象
//			EntityElement element = (EntityElement) treeViewer.getTree()
//					.getItem(0).getData();
//			// 获取全路径
//			String fileFullName = StudioInterface
//					.getBizobjRealPathFromBizobjId(bizobjId);
//			// 递归找到对应的树节点
//			searchElement(fileFullName, element.getChildren());
//			if (selectElement != null) {
//				selectElement.setName(newElementName); // 更改名称
//				treeViewer.refresh(); // 刷新
//			}
//		}
//	}
	
	/**
	 * 修改服务树节点名称
	 * @param fileFullName
	 * @param elementRealName
	 * @param newElementId
	 * @param newElementName
	 */
//	public static void modifyServiceElementName(String fileFullName, String elementRealName, 
//			String newElementId, String newElementName) {
//		ITreeElement bizobjElement = null;
//		
//		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		// 获取projectView
//		ProjectView projectView = (ProjectView) workbenchPage
//				.findView(FixPerspective.PROJECT_VIEW);
//		if (projectView != null) {
//			// 遍历treeViewer找到编辑器所对应的树节点
//			TreeViewer treeViewer = projectView.getTreeViewer();
//			// 找到根节点对象
//			EntityElement element = (EntityElement) treeViewer.getTree()
//					.getItem(0).getData();
//			// 递归找到对应的树节点
//			searchElement(fileFullName, "sevmethod", elementRealName, element.getChildren());
//			if (selectElement != null) {
//				//找到该业务对象
//				bizobjElement = selectElement;
//				//更改该对象
//				bizobjElement.setRealName(newElementId);
//				bizobjElement.setName(newElementName + "(" + newElementId + ")");
//				
//				treeViewer.refresh();
//			}
//		}
//	}
	
	/**
	 * 修改视图树节点名称
	 * @param fileFullName
	 * @param elementRealName
	 * @param newElementId
	 * @param newElementName
	 */
//	public static void modifyViewElementName(String fileFullName, String elementRealName, String elementId,
//			String newElementId, String newElementName) {
//		ITreeElement bizobjElement = null;
//		
//		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		// 获取projectView
//		ProjectView projectView = (ProjectView) workbenchPage
//				.findView(FixPerspective.PROJECT_VIEW);
//		if (projectView != null) {
//			// 遍历treeViewer找到编辑器所对应的树节点
//			TreeViewer treeViewer = projectView.getTreeViewer();
//			// 找到根节点对象
//			EntityElement element = (EntityElement) treeViewer.getTree()
//					.getItem(0).getData();
//			// 递归找到对应的树节点
//			searchElement(fileFullName, elementId, elementRealName, element.getChildren());
//			if (selectElement != null) {
//				//找到该业务对象
//				bizobjElement = selectElement;
//				//更改该对象
//				bizobjElement.setRealName(newElementId);
//				bizobjElement.setName(newElementName);
//				
//				treeViewer.refresh();
//			}
//		}
//	}

	/**
	 * 根据文件对象全路径获取其对应的树节点
	 * 
	 * @param fileFullPath
	 * @return
	 */
//	public static ITreeElement getSelectElementFromFileFullPath(
//			String fileFullPath) {
//		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		// 获取projectView
//		ProjectView projectView = (ProjectView) workbenchPage
//				.findView(FixPerspective.PROJECT_VIEW);
//		if (projectView != null) {
//			// 遍历treeViewer找到编辑器所对应的树节点
//			TreeViewer treeViewer = projectView.getTreeViewer();
//			// 找到根节点对象
//			EntityElement element = (EntityElement) treeViewer.getTree()
//					.getItem(0).getData();
//			// 递归找到对应的树节点
//			searchElement(fileFullPath, element.getChildren());
//			if (selectElement != null) {
//				return selectElement;
//			}
//		}
//		return null;
//	}

	/**
	 * 根据工程视图和文件对象全路径获取其对应的树节点
	 * 
	 * @param projectView
	 * @param fileFullPath
	 * @return
	 */
//	public static ITreeElement getSelectElementFromFileFullPath(
//			ProjectView projectView, String fileFullPath) {
//		if (projectView != null) {
//			// 遍历treeViewer找到编辑器所对应的树节点
//			TreeViewer treeViewer = projectView.getTreeViewer();
//			// 找到根节点对象
//			ITreeElement element = (ITreeElement) treeViewer.getTree()
//					.getItem(0).getData();
//			// 递归找到对应的树节点
//			searchElement(fileFullPath, element.getChildren());
//			if (selectElement != null) {
//				return selectElement;
//			}
//		}
//		return null;
//	}

	/**
	 * 递归找到编辑器所对应的树节点
	 * 
	 * @param fileFullPath
	 * @param elements
	 */
	private static void searchElement(String fileFullPath, 
			List<ITreeElement> elements) {
		if (elements != null && elements.size() > 0) {
			for (int i = 0; i < elements.size(); i++) {
				EntityElement element = (EntityElement) elements.get(i);
				if(element.getParent().getId().equals("js_com")) {
					System.out.println();
				}
				if (element != null
						&& (element.getRealPath().replace('\\', '/').startsWith("/") ? element
								.getRealPath().replace('\\', '/').substring(1) : element
								.getRealPath().replace('\\', '/')).equals(fileFullPath.replace('\\', '/'))) {
					selectElement = element;
					break;
				} else {
					searchElement(fileFullPath, element.getChildren());
				}
			}
		}
	}
	
	/**
	 * 递归找到编辑器所对应的树节点
	 * 
	 * @param fileFullPath
	 * @param elements
	 */
	private static void searchElement(String fileFullPath, String elementId,
			String elementRealName, List<ITreeElement> elements) {
		if (elements != null && elements.size() > 0) {
			for (int i = 0; i < elements.size(); i++) {
				EntityElement element = (EntityElement) elements.get(i);
				if (element != null
						&& element.getRealPath().replace('\\', '/').equals(fileFullPath)
						&& element.getId().equals(elementId)
						&& element.getRealName().equals(elementRealName)) {
					selectElement = element;
					break;
				} else {
					searchElement(fileFullPath, elementId,
							elementRealName, element.getChildren());
				}
			}
		}
	}

	/**
	 * 根据传递的树节点对象刷新该节点下所有文件
	 * 
	 * @param selectElement
	 */
//	public static void refreshSelectTreeElement(ITreeElement selectElement) {
//		if (selectElement == null) {
//			return;
//		}
//		String fullRealPath = selectElement.getRealPath();
//		File folder = new File(fullRealPath);
//		// 只刷新文件夹下的文件
//		if (folder.isDirectory()) {
//			// 获取缓存中需要过滤的文件夹名称
//			String hiddenFileName = FixUtil.getHiddenFileName();
//
//			// 刷新前重置该节点下所有子节点
//			selectElement.setChildren(new ArrayList<ITreeElement>());
//
//			if (selectElement.getName().equals("业务视图")) {
//				// 刷新业务视图下所有html文件
//				FileOperation.refreshWebRootHtml((EntityElement) selectElement);
//			} else {
//				// 递归获取每个文件夹下的所有文件webProjectPath
//				FileOperation.getChildrenFiles((EntityElement) selectElement,
//						folder, fullRealPath, hiddenFileName, false);
//			}
//			// 刷新树节点
//			((ProjectView) StudioInterface
//					.getViewPartFromViewId(FixPerspective.PROJECT_VIEW))
//					.getTreeViewer().refresh();
//		}
//	}

	/**
	 * 点击完成后创建新的文件
	 * 
	 * @param oldProjectElement
	 * @param fileName
	 * @param icon
	 */
//	public static void createNewConfigFile(ITreeElement oldProjectElement,
//			String fileName, int icon) {
//		fileName = fileName.replace('\\', '/');
//		String fileRealName = fileName.substring(fileName.lastIndexOf("/") + 1,
//				fileName.length());
//
//		// 获取ProjectView
//		ProjectView projectView = (ProjectView) StudioInterface
//				.getViewPartFromViewId(FixPerspective.PROJECT_VIEW);
//
//		// 获取treeviewer
//		TreeViewer treeViewer = projectView.getTreeViewer();
//
//		// 获取选取的节点
//		ITreeElement selectElement = oldProjectElement;
//		if (selectElement == null) {
//			return;
//		}
//
//		// 根据传入的‘模块名称’创建新的节点
//		ITreeElement newEntity = new EntityElement(selectElement.getChildren().get(1), "file",
//				fileRealName, fileRealName, icon, fileName);
//
//		// 在entity当前节点下的子节点'表单'下新增
//		selectElement.getChildren().get(1).addChild(newEntity);
//
//		// 刷新
//		treeViewer.refresh();
//
//		// 自动选中
//		treeViewer.setSelection(new StructuredSelection(newEntity));
//
//		// 在增加子节点之后展开该节点
//		if (!treeViewer.getExpandedState(selectElement)) { // 返回该节点对象是否展开的布尔值
//			treeViewer.expandToLevel(selectElement, 1); // 展开一层
//		}
//	}

	/**
	 * 点击完成后创建新的文件
	 * 
	 * @param projectView
	 * @param selectElement
	 * @param fileName
	 * @param chineseName
	 * @param icon
	 * @param isEntityBizobj
	 */
//	public static void createNewConfigFile(ViewPart projectView,
//			ITreeElement selectElement, String fileName, String chineseName,
//			int icon, boolean isEntityBizobj) {
//		fileName = fileName.replace('\\', '/');
//		// 根据传入的‘模块名称’创建新的节点
//		ITreeElement newEntity = new EntityElement(selectElement, "file",
//				chineseName, fileName.substring(fileName.lastIndexOf("/") + 1),
//				icon, fileName);
//		
//		//如果是业务对象才需要加入‘表单’和‘’子节点
//		if(newEntity.getRealName().endsWith(".biz")) {
//			//视图文件夹
//			ITreeElement viewFile = new EntityElement(newEntity, "objview", "视图", fileName.substring(fileName.lastIndexOf("/") + 1),
//					EntityElement.VIEW, "");
//			
//			//表单文件夹
//			ITreeElement formFile = new EntityElement(newEntity, "objhtml", "表单", fileName.substring(fileName.lastIndexOf("/") + 1),
//					EntityElement.OBJFORM, "");
//			
//			//服务文件夹
//			ITreeElement sevFile = new EntityElement(newEntity, "objsev", "方法", fileName.substring(fileName.lastIndexOf("/") + 1),
//					EntityElement.SEV, "");
//			
//			//如果是实体对象要加上默认的虚服务
//			if(isEntityBizobj) {
//				for (int i = 0; i < Const.BizDefaultService.values().length - 3; i++) {
//					String svzFilePath = fileName.substring(0, fileName.lastIndexOf(".")) + "_SVZ/";
//					
//					EntityElement serviceElement = new EntityElement(sevFile, "sevmethod", 
//							CreateBizObjWizard.servicesName[i] + "(" + Const.BizDefaultService.values()[i].toString() + ")",
//							Const.BizDefaultService.values()[i].toString(), 
//							EntityElement.SEV_METHOD, 
//							svzFilePath + Const.BizDefaultService.values()[i].toString() + ".svz");
//					
//					sevFile.addChild(serviceElement);
//				}
//			}
//			
//			newEntity.addChild(viewFile);
//			newEntity.addChild(formFile);
//			newEntity.addChild(sevFile);
//		}
//		
//		// 在entity当前节点下增加新的子节点trueEntity
//		selectElement.addChild(newEntity);
//
//		TreeViewer treeViewer = ((ProjectView) projectView).getTreeViewer();
//		// 刷新
//		treeViewer.refresh();
//
//		// 自动选中
//		treeViewer.setSelection(new StructuredSelection(newEntity));
//
//		// 在增加子节点之后展开该节点
//		if (!treeViewer.getExpandedState(selectElement)) { // 返回该节点对象是否展开的布尔值
//			treeViewer.expandToLevel(selectElement, 1); // 展开一层
//		}
//	}

	/**
	 * 打开文件
	 */
//	public static void openFile(TreeViewer treeViewer) {
//		// 获取点击的节点对象
//		final ITreeElement selectElement = FixUtil.getSelectElement(treeViewer);
//		// 获取平台配置page
//		IWorkbenchPage page = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		// 获取workspace根目录对象
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//
//		// 打开业务对象编辑器
//		if (selectElement.getId().equals("file")
//				&& selectElement.getRealPath().endsWith(".biz")) {
//			IProject project = root.getProject(FixUtil
//					.getProjectPath(selectElement.getRealPath()));
//			try {
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				IFile file = project.getFile(FixUtil.getFilePath(selectElement
//						.getRealPath()));
//
//				try {
//					// 打开编辑器
//					IEditorPart openedEditorPart = IDE.openEditor(page, file,
//							BizEditor.ID);
//
//					// 放入缓存维持状态
////					CacheUtil.putCache(
////							BizEditor.ID + "_" + selectElement.getRealPath(),
////							openedEditorPart);
//				} catch (PartInitException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//
//		// 打开表单编辑器
//		else if ((selectElement.getId().equals("file") || selectElement.getId()
//				.equals("html"))
//				&& selectElement.getRealPath().endsWith(".html")) {
//			IProject project = root.getProject(FixUtil
//					.getProjectPath(selectElement.getRealPath()));
//			try {
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				IFile file = project.getFile(FixUtil.getFilePath(selectElement
//						.getRealPath()));
//
//				try {
//					// 打开编辑器
//					IEditorPart openedEditorPart = IDE.openEditor(page, file,
//							MainEditor.ID);
//
//					// 放入缓存维持状态
////					CacheUtil.putCache(
////							MainEditor.ID + "_" + selectElement.getRealPath(),
////							openedEditorPart);
//				} catch (PartInitException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//
//		// 打开流程编辑器
//		else if ((selectElement.getId().equals("file") || selectElement.getId()
//				.equals("html"))
//				&& selectElement.getRealPath().endsWith(".bpmn")) {
//			IProject project = root.getProject(FixUtil
//					.getProjectPath(selectElement.getRealPath()));
//			try {
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				IFile file = project.getFile(FixUtil.getFilePath(selectElement
//						.getRealPath()));
//				
//				//流程比较特殊，要先验证是否在创建流程的时候编辑器已经打开，已经打开不能再打开
//				IEditorPart[] editorParts = getAllOpenedEditor();
//				if(editorParts != null && editorParts.length > 0) {
//					for (int i = 0; i < editorParts.length; i++) {
//						IEditorPart iEditorPart = editorParts[i];
////						if(iEditorPart instanceof BPMN2MultiPageEditor) {
////							BPMN2MultiPageEditor bpmn2Editor = (BPMN2MultiPageEditor) iEditorPart;
////							String filePath = getFileFullPathFromEditor(bpmn2Editor);
////							if(filePath.equals(file.getLocation().toString())) {
////								page.activate(bpmn2Editor);
////								return;
////							}		
////						}
//					}
//				}
//				
////				try {
////					// 打开编辑器
//////					IEditorPart openedEditorPart = IDE.openEditor(page, file,
//////							BPMN2Editor.EDITOR_ID);
////
////					// 放入缓存维持状态
//////					CacheUtil.putCache(BPMN2Editor.EDITOR_ID + "_"
//////							+ selectElement.getRealPath(), openedEditorPart);
////					// }
////				} catch (PartInitException e1) {
////					e1.printStackTrace();
////				}
//			}
//		}
//
//		// 打开java编辑器
//		else if ((selectElement.getId().equals("file") || selectElement.getId()
//				.equals("html"))
//				&& selectElement.getRealPath().endsWith(".java")) {
//			IProject project = root.getProject(FixUtil
//					.getProjectPath(selectElement.getRealPath()));
//			try {
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				IFile file = project.getFile(FixUtil.getFilePath(selectElement
//						.getRealPath()));
//
//				try {
//					// 打开编辑器
//					IEditorPart openedEditorPart = IDE.openEditor(page, file);
//
//					// 放入缓存维持状态
////					CacheUtil.putCache(
////							MainEditor.ID + "_" + selectElement.getRealPath(),
////							openedEditorPart);
//				} catch (PartInitException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//
//		// 打开sev编辑器
//		else if (selectElement.getId().equals("file")
//				&& selectElement.getRealPath().endsWith(".sev")) {
//			IProject project = null;
//			try {
//				project = root.getProject(FixUtil.getProjectPath(selectElement
//						.getRealPath()));
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//			
//			if (treeViewer.getExpandedState(selectElement)) {
//				// 如果展开就收起
//				treeViewer.collapseToLevel(selectElement, 1);
//			} else {
//				// 展现下一层文件夹
//				treeViewer.expandToLevel(selectElement, 1);
//			}
//
////			if (project.exists()) {
////				IFile file = project.getFile(FixUtil.getFilePath(selectElement
////						.getRealPath()));
////
////				try {
////					// 打开xml编辑器
////					IEditorPart openedEditorPart = IDE.openEditor(page, file,
////							ServicesEditor.ID);
////
////					// 放入缓存维持状态
////					CacheUtil.putCache(
////							ServicesEditor.ID + "_"
////									+ selectElement.getRealPath(),
////							openedEditorPart);
////				} catch (PartInitException e1) {
////					e1.printStackTrace();
////				}
////			}
//		}
//		
//		// 打开视图编辑器
//		else if (selectElement.getId().endsWith("form")) {
//			IProject project = null;
//			try {
//				project = root.getProject(FixUtil.getProjectPath(selectElement
//						.getRealPath()));
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				 IFile file = project.getFile(FixUtil.getFilePath(selectElement.getRealPath()));
//				try {
////					String bizobjFilePath = "";
////					if (selectElement.getParent().getParent() == null) {
////						bizobjFilePath = StudioInterface
////								.getBizobjRealPathFromBizobjId(selectElement
////										.getRealName().substring(
////												0, selectElement.getRealName()
////														.indexOf(".")));
////					} else {
////						bizobjFilePath = selectElement.getParent().getParent()
////								.getRealPath();
////					}
//					
//					// 打开xml编辑器
////					IEditorPart openedEditorPart = PlatformUI
////							.getWorkbench()
////							.getActiveWorkbenchWindow()
////							.getActivePage()
////							.openEditor(
////									new FormViewInput(
////											selectElement.getRealName(),
////											selectElement.getName(),
////											selectElement.getId(),
////											selectElement.getRealPath(),
////											bizobjFilePath),
////											FormViewEditor.ID, true);
//
//					IEditorPart openedEditorPart = IDE.openEditor(page, file,
//							FormViewEditor.ID);
//					
//					// 放入缓存维持状态
////					CacheUtil.putCache(
////							FormViewEditor.ID + "_"
////									+ selectElement.getRealPath() + "_"
////									+ selectElement.getRealName(),
////							openedEditorPart);
//				} catch (PartInitException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//
//		// 打开sev中方法编辑器
//		else if (selectElement.getId().equals("sevmethod")) {
//			IProject project = null;
//			try {
//				project = root.getProject(FixUtil.getProjectPath(selectElement
//						.getRealPath()));
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				 IFile file = project.getFile(FixUtil.getFilePath(selectElement.getRealPath()));
//				try {
//					// 打开xml编辑器
////					IEditorPart openedEditorPart = PlatformUI
////							.getWorkbench()
////							.getActiveWorkbenchWindow()
////							.getActivePage()
////							.openEditor(
////									new ServiceMethodInput(
////											selectElement.getRealName(),
////											selectElement.getRealPath()),
////									ServiceMethodEditor.ID, true);
//
//					IEditorPart openedEditorPart = IDE.openEditor(page, file,
//							ServiceMethodEditor.ID);
//					
//					// 放入缓存维持状态
////					CacheUtil.putCache(
////							ServiceMethodEditor.ID + "_"
////									+ selectElement.getRealPath() + "_"
////									+ selectElement.getRealName(),
////							openedEditorPart);
//				} catch (PartInitException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//
//		// 打开菜单节点对话框
//		else if (selectElement.getId().equals("menu")) {
//			IProject project = null;
//			try {
//				project = root.getProject(FixUtil.getProjectPath(selectElement
//						.getRealPath()));
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				IFile file = project.getFile(FixUtil.getFilePath(selectElement
//						.getRealPath()));
//				try {
//					// 打开编辑器
//					IEditorPart openedEditorPart = IDE.openEditor(page, file,
//							MenuDesigner.ID);
//
//					// 放入缓存维持状态
////					CacheUtil.putCache(ServiceMethodEditor.ID + "_"
////							+ selectElement.getRealPath(), openedEditorPart);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//
//		// 打开字典表节点对话框
//		else if (selectElement.getId().equals("dictionarytable")) {
//			IProject project = null;
//			try {
//				project = root.getProject(FixUtil.getProjectPath(selectElement
//						.getRealPath()));
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
////				IFile file = project.getFile(FixUtil.getFilePath(selectElement.getRealPath()));
//				try {
//					//直接打开修改对话框
//					/*Dialog dialog = new ModifyDicDialog(null, new DictionaryTO(), treeViewer, selectElement);
//					dialog.open(); // 打开
//*/				} catch (Exception e) {
//					FixLoger.error(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		// 打开表单组件编辑器
//		else if (selectElement.getParent().getId().equals("js_com")) {
//			IProject project = null;
//			try {
//				project = root.getProject(FixUtil.getProjectPath(selectElement
//						.getRealPath()));
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				IFile file = project.getFile(FixUtil.getFilePath(selectElement
//						.getRealPath()));
//				try {
//					// 打开编辑器
//					IEditorPart openedEditorPart = IDE.openEditor(page, file,
//							ComponentsEditor.ID);
//
//					// 放入缓存维持状态
////					CacheUtil.putCache(ComponentsEditor.ID + "_"
////							+ selectElement.getRealPath(), openedEditorPart);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//			//自动展现下一层
//			treeViewer.expandToLevel(selectElement, 1);
//		}
//		
//		// 打开服务组件编辑器
//		else if (selectElement.getParent().getId().equals("sevice_com")) {
//			IProject project = null;
//			try {
//				project = root.getProject(FixUtil.getProjectPath(selectElement
//						.getRealPath()));
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				IFile file = project.getFile(FixUtil.getFilePath(selectElement
//						.getRealPath()));
//				try {
//					// 打开编辑器
//					IEditorPart openedEditorPart = IDE.openEditor(page, file,
//							ComponentsServiceEditor.ID);
//
//					// 放入缓存维持状态
////					CacheUtil.putCache(ComponentsServiceEditor.ID + "_"
////							+ selectElement.getRealPath(), openedEditorPart);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//			//自动展现下一层
//			treeViewer.expandToLevel(selectElement, 1);
//		}
//
//		// 其他文件都用xml编辑器打开
//		else if ((selectElement != null && selectElement.getRealPath().indexOf(
//				".") != -1)) {
//			//如果是字典表
//			if(selectElement.getRealName().equals("dictionarytable.xml")) {
//				if (treeViewer.getExpandedState(selectElement)) {
//					// 如果展开就收起
//					treeViewer.collapseToLevel(selectElement, 1);
//				} else {
//					// 展现下一层文件夹
//					treeViewer.expandToLevel(selectElement, 1);
//				}
//				return;
//			}
//			
//			IProject project = null;
//			try {
//				project = root.getProject(FixUtil.getProjectPath(selectElement
//						.getRealPath()));
//				project.open(null); // 一定要先打开，不然找不到打开的文件
//				// 刷新
//				StudioInterface.refreshTruthFileFromFileFullPath(selectElement
//						.getRealPath());
//			} catch (CoreException e2) {
//				e2.printStackTrace();
//			}
//
//			if (project.exists()) {
//				IFile file = project.getFile(FixUtil.getFilePath(selectElement
//						.getRealPath()));
//
//				try {
//					// 打开xml编辑器
//					IEditorPart openedEditorPart = IDE.openEditor(page, file);
//
//					// 放入缓存维持状态
////					CacheUtil.putCache(
////							XMLEditor.ID + "_" + selectElement.getRealPath(),
////							openedEditorPart);
//				} catch (PartInitException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//
//		// 其他双击文件夹的时候可以展开子文件夹
//		else {
//			if (treeViewer.getExpandedState(selectElement)) {
//				// 如果展开就收起
//				treeViewer.collapseToLevel(selectElement, 1);
//			} else {
//				// 展现下一层文件夹
//				treeViewer.expandToLevel(selectElement, 1);
//			}
//		}
//	}

	/**
	 * 关闭编辑器
	 * 
	 * @param editor
	 */
	public static boolean closeEditor(IEditorPart editor, boolean save) {
		// 找到打开的并隶属于该文件的编辑器并关闭
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		// 关闭
		return workbenchPage.closeEditor(editor, save);
	}
	
	/**
	 * 关闭编辑器
	 * 
	 * @param editor
	 */
	public static void closeEditor(String filePath, boolean save) {
		IEditorPart[] editorParts = FixUtil.getAllOpenedEditor();
		if(editorParts != null && editorParts.length > 0) {
			for (int i = 0; i < editorParts.length; i++) {
				IEditorPart iEditorPart = editorParts[i];
				if(iEditorPart.getEditorInput() instanceof FileEditorInput) {
					FileEditorInput editorInput = (FileEditorInput) iEditorPart.getEditorInput();
					if(editorInput.getFile().getLocation().toString()
							.equals(filePath.replace('\\', '/'))) {
						FixUtil.closeEditor(iEditorPart, false);
					}
				}
			}
		}
	}
	
	/**
	 * 关闭编辑器并且重新打开
	 * @param bizobjId
	 */
//	public static void closeAndOpenBizEditorFromBizobjId(String bizobjId) {
//		closeAndOpenBizEditorFromFileRealPath(StudioInterface.getBizobjRealPathFromBizobjId(bizobjId));
//	}
	
	/**
	 * 关闭业务对象编辑器并且重新打开
	 * @param fileRealPath
	 */
//	public static void closeAndOpenBizEditorFromFileRealPath(String fileRealPath) {
//		//根据文件路径找到对应的编辑器
//		IEditorPart editor = getEditorPartFromFileRealPath(fileRealPath);
//		
//		if(editor instanceof BizEditor) {
//			if(MessageDialog.openConfirm(null, "提示", "业务对象‘" 
//					+ editor.getTitle() + "’已更改，是否重新打开（未保存信息将丢失）？")) {
//				//找到打开的并隶属于该文件的编辑器并关闭
//				IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getActivePage();
//				
//				//关闭
//				workbenchPage.closeEditor(editor, false);
//				
//				//刷新旧的真实文件
//				StudioInterface.refreshTruthFileFromFileFullPath(fileRealPath);
//				
//				//重新打开编辑器
//				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//				IProject project = root.getProject(FixUtil.getProjectPath(fileRealPath));
//				IFile file = project.getFile(FixUtil.getFilePath(fileRealPath));
//				try {
//					IEditorPart openedEditorPart = IDE.openEditor(workbenchPage, file,
//							BizEditor.ID);
//
//					// 放入缓存维持状态
////					CacheUtil.putCache(
////							BizEditor.ID + "_" + fileRealPath,
////							openedEditorPart);
//				} catch (Exception e) {
//					FixLoger.error(e.toString());
//					e.printStackTrace();
//				}
//			} 
//		}
//	}
	
	/**
	 * 关闭视图编辑器并且重新打开
	 * @param fileRealPath
	 */
//	public static void closeAndOpenViewEditorFromFileRealPath(String fileRealPath) {
//		//根据文件路径找到对应的编辑器
//		IEditorPart editor = getEditorPartFromFileRealPath(fileRealPath);
//		
//		if(editor instanceof FormViewEditor) {
//			//找到打开的并隶属于该文件的编辑器并关闭
//			IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getActivePage();
//			
//			//关闭
//			workbenchPage.closeEditor(editor, false);
//			
//			//刷新旧的真实文件
//			StudioInterface.refreshTruthFileFromFileFullPath(fileRealPath);
//			
//			//重新打开编辑器
//			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//			IProject project = root.getProject(FixUtil.getProjectPath(fileRealPath));
//			IFile file = project.getFile(FixUtil.getFilePath(fileRealPath));
//			try {
//				IEditorPart openedEditorPart = IDE.openEditor(workbenchPage, file,
//						FormViewEditor.ID);
//			} catch (Exception e) {
//				FixLoger.error(e.toString());
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * 保存业务对象服务
	 * @param bizObjTo
	 * @param filePath
	 */
	public static void saveBizObjServiceTo(ObjServiceTo objServiceTo, String filePath,
			String serviceOldId, String serviceId) {
		ObjServiceTo serviceTo = null;
		
		if(objServiceTo.getId().equals(serviceOldId)) {
			serviceTo = objServiceTo;
		}

		//根据to对象对业务对象的服务节点进行更改
		try {
			Document document = XmlUtil.read(new File(filePath), "UTF-8");
			Element element = document.getRootElement();
			
			if(element.attributeValue("id").equals(serviceOldId)) {
				
				//重新设置值
				element.setAttributeValue("id", serviceId); 
				element.setAttributeValue("name", serviceTo.getName()); 
				element.setAttributeValue("securityLevel", serviceTo.getSecurityLevel());
				element.setAttributeValue("invokeType",
						serviceTo.getInvokeType() == null ? "" : serviceTo.getInvokeType().toString());
				element.setAttributeValue("doDataSubset", String.valueOf(serviceTo.isDoDataSubset()));
				
				//description
				element.remove(element.element("description")); //先删除
				String description = serviceTo.getDescription();
				Element descriptionEle = element.addElement("description");
				descriptionEle.addCDATA(description);
				
				//parameter
				for (Iterator iterator = element.elements("parameter").iterator(); iterator
						.hasNext();) {
					Element parameter = (Element) iterator.next();
					element.remove(parameter); //先删除
				}
				List<ObjServiceParameterTo> parameters = serviceTo.getParameters();
				if(parameters != null && parameters.size() > 0) {
					for (Iterator iterator2 = parameters.iterator(); iterator2
							.hasNext();) {
						ObjServiceParameterTo objServiceParameterTo = (ObjServiceParameterTo) iterator2
								.next();
						Element parameter = element.addElement("parameter");
						parameter.setAttributeValue("id", objServiceParameterTo.getId());
						parameter.setAttributeValue("name", objServiceParameterTo.getName());
						parameter.setAttributeValue("default", objServiceParameterTo.getDefauld());
						parameter.setAttributeValue("type", objServiceParameterTo.getType());
					}
				}
				
				//transaction
				element.remove(element.element("transaction")); //先删除
				List<String> transactions = serviceTo.getTransaction();
				if(transactions != null && transactions.size() > 0) {
					for (Iterator iterator2 = transactions.iterator(); iterator2
							.hasNext();) {
						String value = (String) iterator2.next();
						Element transaction = element.addElement("transaction");
						transaction.setAttributeValue("value", value);
					}
				}
				
				//verify
				element.remove(element.element("verify")); //先删除
				List<ObjServiceVerifyTo> befVerifies = serviceTo.getBefVerifies();
				if(befVerifies != null && befVerifies.size() > 0) {
					for (Iterator iterator2 = befVerifies.iterator(); iterator2
							.hasNext();) {
						ObjServiceVerifyTo objServiceVerifyTo = (ObjServiceVerifyTo) iterator2
								.next();
						Element verify = element.addElement("verify");
						verify.setAttributeValue("id", objServiceVerifyTo.getId());
						verify.setAttributeValue("name", objServiceVerifyTo.getName());
						verify.setAttributeValue("type", objServiceVerifyTo.getType());
						verify.setAttributeValue("exp", objServiceVerifyTo.getExp());
						verify.setAttributeValue("expectValue", objServiceVerifyTo.getExpectValue());
						verify.setAttributeValue("errorMsg", objServiceVerifyTo.getErrorMsg());
						
						//parameter
						List<ObjServiceParameterTo> vparameters = serviceTo.getParameters();
						if(vparameters != null && vparameters.size() > 0) {
							for (Iterator iterator3 = vparameters.iterator(); iterator3
									.hasNext();) {
								ObjServiceParameterTo objServiceParameterTo = (ObjServiceParameterTo) iterator3
										.next();
								Element parameter = verify.addElement("parameter");
								parameter.setAttributeValue("id", objServiceParameterTo.getId());
								parameter.setAttributeValue("name", objServiceParameterTo.getName());
								parameter.setAttributeValue("default", objServiceParameterTo.getDefauld());
								parameter.setAttributeValue("type", objServiceParameterTo.getType());
							}
						}
					}
				}
				
				//implement
				element.remove(element.element("implement")); //先删除
				Element implement = element.addElement("implement");
				implement.addCDATA(serviceTo.getImplement() == null ? ""
						: serviceTo.getImplement());
				
				//out
				for (Iterator iterator = element.elements("out").iterator(); iterator
						.hasNext();) {
					Element out = (Element) iterator.next();
					element.remove(out); //先删除
				}
				for (Iterator iterator = element.elements("implementOut").iterator(); iterator
						.hasNext();) {
					Element out = (Element) iterator.next();
					element.remove(out); //先删除
				}
				List<ObjServiceOutTo> outs = serviceTo.getOut();
				if(outs != null && outs.size() > 0) {
					for (Iterator iterator2 = outs.iterator(); iterator2
							.hasNext();) {
						ObjServiceOutTo objServiceOutTo = (ObjServiceOutTo) iterator2
								.next();
						
						Element out = null;
						
						//如果outType是implementOut的话，节点的名称要变成implementOut
						if(objServiceOutTo.getOutType() != null
								&& objServiceOutTo.getOutType().equals(BizObjectUtil.IMPLEMENT_SERVICE)) {
							out = element.addElement("implementOut");
						} else {
							out = element.addElement("out");
						}
						
						out.setAttributeValue("id", objServiceOutTo.getId());
						out.setAttributeValue("name", objServiceOutTo.getName());
						out.setAttributeValue("type", objServiceOutTo.getType());
						out.setAttributeValue("outType", objServiceOutTo.getOutType());
						out.setAttributeValue("value", objServiceOutTo.getValue());
					}
				}
				
				//二维表返回值
				element.remove(element.element("outColumns")); //先删除
				List<ObjServiceOutColumnTo> outColumns = serviceTo.getOutColumn();
				if(outColumns != null && outColumns.size() > 0) {
					Element outColumnsEle = element.addElement("outColumns");
					
					for (Iterator iterator2 = outColumns.iterator(); iterator2
							.hasNext();) {
						ObjServiceOutColumnTo objServiceOutColumnTo = (ObjServiceOutColumnTo) iterator2
								.next();
						Element column = outColumnsEle.addElement("column");
						column.setAttributeValue("id", objServiceOutColumnTo.getId());
						column.setAttributeValue("name", objServiceOutColumnTo.getName());
						column.setAttributeValue("type", objServiceOutColumnTo.getType());
					}
				}
				
			}
			
			XmlUtil.save(document, filePath, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			FixLoger.error(e.toString());
		}
	}

	/**
	 * 向导式创建文件最后保存并打开动作
	 * 
	 * @param fileFullName
	 * @param is
	 * @param editorID
	 * @param container
	 * @param shell
	 * @return
	 */
//	public static boolean saveFileToProject(final String fileFullName,
//			final InputStream is, final String editorID,
//			final IRunnableContext container, final Shell shell,
//			final boolean isOpenEditor, final BizObjTo bizObj) {
//		final String project = getProjectPath(fileFullName);
//		final String filePath = getFilePath(fileFullName);
//
//		IRunnableWithProgress op = new IRunnableWithProgress() {
//			public void run(IProgressMonitor monitor)
//					throws InvocationTargetException {
//				try {
//					doFinish(project, filePath, is, monitor, editorID, shell,
//							isOpenEditor, bizObj);
//				} catch (CoreException e) {
//					throw new InvocationTargetException(e);
//				} finally {
//					monitor.done();
//				}
//			}
//		};
//		try {
//			container.run(true, false, op);
//		} catch (Exception e) {
//			FixLoger.error(e.toString());
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
	
	
	
	/**
	 * 向导式创建文件最后保存并打开动作
	 * 
	 * @param fileFullName
	 * @param is
	 * @param editorID
	 * @param container
	 * @param shell
	 * @return
	 */
//	public static boolean saveFileToProject(final String fileFullName,
//			final InputStream is, final String editorID,
//			final IRunnableContext container, final Shell shell,
//			final boolean isOpenEditor, final BizObjTo bizObj
//			,final int rowFieldsNum
//			,final DetailType detailType[]
//			,final String mainTableColumneIDs[]) {
//		final String project = getProjectPath(fileFullName);
//		final String filePath = getFilePath(fileFullName);
//
//		IRunnableWithProgress op = new IRunnableWithProgress() {
//			public void run(IProgressMonitor monitor)
//					throws InvocationTargetException {
//				try {
//					doFinish(project, filePath, is, monitor, editorID, shell,
//							isOpenEditor, bizObj
//							,rowFieldsNum
//							,detailType
//							,mainTableColumneIDs);
//				} catch (CoreException e) {
//					throw new InvocationTargetException(e);
//				} finally {
//					monitor.done();
//				}
//			}
//		};
//		try {
//			container.run(true, false, op);
//		} catch (Exception e) {
//			FixLoger.error(e.toString());
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}

	/**
	 * 完成操作
	 * 
	 * @param projectName
	 * @param fileName
	 * @param is
	 * @param monitor
	 * @param editorId
	 * @param shell
	 * @throws CoreException
	 */
//	private static void doFinish(String projectName, String fileName,
//			InputStream is, IProgressMonitor monitor, final String editorId,
//			final Shell shell, final boolean isOpenEditor, final BizObjTo bizObj) throws CoreException {
//		monitor.beginTask("创建文件" + fileName, 2);
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IProject project = root.getProject(projectName);
//		project.open(null);
//		final IFile file = project.getFile(fileName);
//
//		// 刷新文件
//		refreshFolder(projectName, fileName);
//
//		try {
//			if (file.exists()) {
//				file.setContents(is, true, false, monitor);
//			} else {
//				file.create(is, true, monitor);
//			}
//			is.close();
//		} catch (IOException e) {
//		}
//		
//		if(bizObj != null) {
//			//修改模板文件用以灵动表单
////			HandleMobileHtml handleMobileHtml = new HandleMobileHtml(bizObj);
////			handleMobileHtml.modifyMobileModel(file.getLocation().toString());
//			
//			AutoCreateHtml auto = new AutoCreateHtml(bizObj,file.getLocation().toString());
//			auto.autoCreateHtml();
//			auto.saveFileText(file.getLocation().toString());
//		}
//		
//		monitor.worked(1);
//		monitor.setTaskName("创建文件..");
//		if (isOpenEditor) {
//			shell.getDisplay().asyncExec(new Runnable() {
//				public void run() {
//					IWorkbenchPage page = PlatformUI.getWorkbench()
//							.getActiveWorkbenchWindow().getActivePage();
//					try {
//						// 打开编辑器
//						IEditorPart openedEditorPart = IDE.openEditor(page,
//								file, editorId);
//
//						// 获取文件相对路径
//						String filePath = file.getFullPath().toFile().getPath();
//						filePath = filePath.replace('\\', '/');
//						filePath = filePath.substring(1, filePath.length()); // 去掉第一个'/'
//
//						// 放入缓存维持状态
////						CacheUtil.putCache(
////								editorId + "_" + FixUtil.getWorkspaceRealPath()
////										+ filePath, openedEditorPart);
//					} catch (PartInitException e) {
//					}
//				}
//			});
//		}
//		monitor.worked(1);
//	}
	
	/**
	 * 完成操作
	 * 
	 * @param projectName
	 * @param fileName
	 * @param is
	 * @param monitor
	 * @param editorId
	 * @param shell
	 * @throws CoreException
	 */
//	private static void doFinish(String projectName, String fileName,
//			InputStream is, IProgressMonitor monitor, final String editorId,
//			final Shell shell, final boolean isOpenEditor, final BizObjTo bizObj
//			,final int rowFieldsNum
//			,final DetailType detailType[]
//			,final String mainTableColumneIDs[]) throws CoreException {
//		monitor.beginTask("创建文件" + fileName, 2);
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IProject project = root.getProject(projectName);
//		project.open(null);
//		final IFile file = project.getFile(fileName);
//
//		// 刷新文件
//		refreshFolder(projectName, fileName);
//
//		try {
//			if (file.exists()) {
//				file.setContents(is, true, false, monitor);
//			} else {
//				file.create(is, true, monitor);
//			}
//			is.close();
//		} catch (IOException e) {
//		}
//		
//		if(bizObj != null) {
//			//修改模板文件用以灵动表单
////			HandleMobileHtml handleMobileHtml = new HandleMobileHtml(bizObj);
////			handleMobileHtml.modifyMobileModel(file.getLocation().toString());
//			
//			AutoCreateHtml auto = new AutoCreateHtml(bizObj,file.getLocation().toString()
//					,rowFieldsNum
//					,detailType
//					,mainTableColumneIDs);
//			auto.autoCreateHtml();
//			auto.saveFileText(file.getLocation().toString());
//		}
//		
//		monitor.worked(1);
//		monitor.setTaskName("创建文件..");
//		if (isOpenEditor) {
//			shell.getDisplay().asyncExec(new Runnable() {
//				public void run() {
//					IWorkbenchPage page = PlatformUI.getWorkbench()
//							.getActiveWorkbenchWindow().getActivePage();
//					try {
//						// 打开编辑器
//						IEditorPart openedEditorPart = IDE.openEditor(page,
//								file, editorId);
//
//						// 获取文件相对路径
//						String filePath = file.getFullPath().toFile().getPath();
//						filePath = filePath.replace('\\', '/');
//						filePath = filePath.substring(1, filePath.length()); // 去掉第一个'/'
//
//						// 放入缓存维持状态
////						CacheUtil.putCache(
////								editorId + "_" + FixUtil.getWorkspaceRealPath()
////										+ filePath, openedEditorPart);
//					} catch (PartInitException e) {
//					}
//				}
//			});
//		}
//		monitor.worked(1);
//	}


	/**
	 * 根据文件名获取工程名称
	 * 
	 * @param fileFullName
	 * @return
	 */
	public static String getProjectPath(String fileFullName) {
		fileFullName = fileFullName.replace('\\', '/'); // 替换符号

		String workSpacePath = FixUtil.getWorkspaceRealPath();
		if(workSpacePath.startsWith("/") && FixStudioInterface.isWindows()) {
			workSpacePath = workSpacePath.substring(1);
		}

		String projectAndFilePath = null;
		int index = fileFullName.indexOf(workSpacePath);
		if (index > -1) {
			projectAndFilePath = fileFullName.substring(workSpacePath.length(),
					fileFullName.length());
		}
		return projectAndFilePath.substring(0, (projectAndFilePath.indexOf("/") == -1) ? 
				projectAndFilePath.length() : projectAndFilePath.indexOf("/"));
	}

	/**
	 * 获取文件相对路径
	 * 
	 * @param fileFullName
	 * @return
	 */
	public static String getFilePath(String fileFullName) {
		fileFullName = fileFullName.replace('\\', '/'); // 替换符号

		String workSpacePath = FixUtil.getWorkspaceRealPath();
		if(workSpacePath.startsWith("/") && FixStudioInterface.isWindows()) {
			workSpacePath = workSpacePath.substring(1);
		}

		String projectAndFilePath = null;
		int index = fileFullName.indexOf(workSpacePath);
		if (index > -1) {
			projectAndFilePath = fileFullName.substring(workSpacePath.length(),
					fileFullName.length());
		}

		return projectAndFilePath.substring(
				projectAndFilePath.indexOf("/") + 1,
				projectAndFilePath.length());
	}

	/**
	 * 通过数据对象ID获取数据对象
	 * 
	 * @param id
	 *            数据对象ID
	 * @return
	 * @throws FileNotFoundException
	 */
	public static BizObjTo getBizObjById(String id) {
		String fullPath = CacheUtil.getBizobjRealPathFromId(id);

		try {
			FileInputStream fis = new FileInputStream(fullPath);
			return BizObjectUtil.readDataObjInfo(fis);
		} catch (Exception e) {
//			FixLoger.error(e.toString());
//			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过ID获取视图对象,注：业务对象和视图对象约定为相同
	 * 
	 * @param id
	 * @return
	 */
	public static ObjViewTo getObjViewTo(String id) {
		String fullPath = getObjViewFilePath(id);
		try {
			FileInputStream fis = new FileInputStream(fullPath);
			return ObjViewUtil.readObjView(fis, "UTF-8");
		} catch (Exception e) {
			FixLoger.error(e.toString());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取视图对象文件路径
	 * 
	 * @param id
	 * @return
	 */
	public static String getObjViewFilePath(String id) {
		String fileFullName = CacheUtil.getBizobjRealPathFromId(id);
		if(fileFullName.equals("")) {
			return "";
		}

		java.io.File file = new java.io.File(fileFullName);
		// 获取文件名不包括扩展名
		String objViewShortName = file.getName().substring(0,
				file.getName().lastIndexOf(".biz"));
		java.io.File parentDir = file.getParentFile().getParentFile()
				.getParentFile();

		String objViewPath = parentDir.getAbsolutePath()
				+ java.io.File.separator + "formview";

		java.io.File objViewFilePath = new java.io.File(objViewPath);
		if (!objViewFilePath.exists())
			objViewFilePath.mkdir();
		return objViewPath + java.io.File.separator + objViewShortName + ".viw";

	}

	/**
	 * 打开编辑器
	 * 
	 * @param id
	 */
//	public static void openBizObjEditor(String id) {
//		IWorkbenchPage page = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		// 获取workspace根目录对象
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//
//		IProject project = root.getProject(FixUtil.getProjectPath(CacheUtil
//				.getBizobjRealPathFromId(id)));
//		try {
//			project.open(null); // 一定要先打开，不然找不到打开的文件
//		} catch (CoreException e2) {
//			FixLoger.error(e2.getMessage());
//			e2.printStackTrace();
//		}
//
//		if (project.exists()) {
//			IFile file = project.getFile(FixUtil.getFilePath(CacheUtil
//					.getBizobjRealPathFromId(id)));
//
//			try {
//				// 打开编辑器
//				IEditorPart openedEditorPart = IDE.openEditor(page, file,
//						BizEditor.ID);
//			} catch (PartInitException e1) {
//				FixLoger.error(e1.getMessage());
//				e1.printStackTrace();
//			}
//		}
//	}

	/**
	 * 打开java文件编辑器
	 * 
	 * @param id
	 *            业务对象ID
	 * @param filePath
	 *            java文件全路径 如C:/aa/bb.java
	 */
//	public static void openJavaFileEditor(String id, String filePath) {
//		if (filePath == null || filePath.equals(""))
//			return;
//		IWorkbenchPage page = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		// 获取workspace根目录对象
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//
//		IProject project = root.getProject(FixUtil.getProjectPath(CacheUtil
//				.getBizobjRealPathFromId(id)));
//		try {
//			project.open(null); // 一定要先打开，不然找不到打开的文件
//		} catch (CoreException e2) {
//			FixLoger.error(e2.getMessage());
//			e2.printStackTrace();
//		}
//
//		if (project.exists()) {
//			IFile file = project.getFile(FixUtil.getFilePath(filePath));
//
//			try {
//				// 打开编辑器
//				IDE.openEditor(PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getActivePage(), file, true);
//			} catch (PartInitException e1) {
//				FixLoger.error(e1.getMessage());
//				e1.printStackTrace();
//			}
//		}
//	}

	/**
	 * 用缺省的编辑器打开文件
	 * 
	 * @param fileRealPath
	 *            文件全路径 如C:/aa/bb.java c:/abc/dd/1.xml
	 */
//	public static void openEditor(String fileRealPath) {
//		IWorkbenchPage page = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//		// 获取workspace根目录对象
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IProject project = root
//				.getProject(FixUtil.getProjectPath(fileRealPath));
//		try {
//			project.open(null); // 一定要先打开，不然找不到打开的文件
//		} catch (CoreException e2) {
//			FixLoger.error(e2.getMessage());
//			e2.printStackTrace();
//		}
//
//		if (project.exists()) {
//			IFile file = project.getFile(FixUtil.getFilePath(fileRealPath));
//
//			try {
//				// 打开编辑器
//				IDE.openEditor(PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getActivePage(), file, true);
//			} catch (PartInitException e1) {
//				FixLoger.error(e1.getMessage());
//				e1.printStackTrace();
//			}
//		}
//	}

	/**
	 * 获取元素的父节点
	 * 
	 * @param elements
	 * @param parentElement
	 * @return
	 */
	public static List<ITreeElement> parentElements(
			List<ITreeElement> elements, ITreeElement parentElement) {
		if (parentElement.getParent() != null) {

			elements.add(parentElement);
			parentElements(elements, parentElement.getParent());
		}

		return elements;

	}

	/**
	 * 
	 * @param treeList
	 * @return
	 */
	public static String getTreePath(List<ITreeElement> treeList) {
		if (treeList == null || treeList.isEmpty())
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = treeList.size() - 1; i >= treeList.size(); i--) {
			if (sb.length() > 0)
				sb.append("\\");
			sb.append(treeList.get(i).getName());
		}
		return sb.toString();
	}

//	public static String getFilePathInVirtualDirectory(String fileFullPath) {
//		ITreeElement current = getSelectElementFromFileFullPath(fileFullPath);
//
//		List<ITreeElement> trees = parentElements(
//				new ArrayList<ITreeElement>(), current);
//
//		return getTreePath(trees);
//	}

//	public static void openEditor(String fileRealPath, String editorId) {
//		IWorkbenchPage page = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IProject project = root
//				.getProject(FixUtil.getProjectPath(fileRealPath));
//		try {
//			project.open(null);
//		} catch (CoreException e2) {
//			FixLoger.error(e2.getMessage());
//			e2.printStackTrace();
//		}
//
//		if (project.exists()) {
//			IFile file = project.getFile(FixUtil.getFilePath(fileRealPath));
//
//			try {
//				if (editorId == null || editorId.equals("")) {
//					IDE.openEditor(PlatformUI.getWorkbench()
//							.getActiveWorkbenchWindow().getActivePage(), file);
//				} else {
//					IDE.openEditor(PlatformUI.getWorkbench()
//							.getActiveWorkbenchWindow().getActivePage(), file,
//							editorId);
//				}
//
//			} catch (PartInitException e1) {
//				FixLoger.error(e1.getMessage());
//				e1.printStackTrace();
//			}
//		}
//	}

}
