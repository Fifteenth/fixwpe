package com.founder.fix.ocx.util;

import java.util.HashMap;

import com.founder.fix.fixwpe.Activator;




/**
 * [类名]<br>
 * Const.java<br>
 * <br>
 * [功能概要]<br>
 * 		常量类，用以在插件中所有常量
 * <br>
 * [变更履历]<br>
 *
 * <br>
 * 2011-6-23 ver1.0 <br>
 * <br>
 *
 * @作者 BB
 *
 */
public class FormConst 
{
	/**
	 * ActiveX类型名称
	 */
	public static final String COMCLASSNAME = "FixFormStudio.FormStudio";//"DHTMLEdit.DHTMLEdit";//
		
	
	/**
	 * 插件的ID
	 */
	public static final String PLUGINID = Activator.PLUGIN_ID;
	
	/**
	 * 设计器HTML控件默认属性的xml文件名
	 */
	public static final String PROPERTIESFILENAME = "fix.studio.form.html.properties.xml";
	
	/**
	 * 设计器表单配置模板文件的xml文件名
	 */
	public static final String FORMCONFIGFILENAME = "fix.studio.form.config.template.xml";
	
	
	/**
	 * MAP中保存控件ID（input|text）的地方
	 */
	public static final String FIXCOMPONENTSKEY = "fixcomponents_key";
	
	/**
	 * MAP中保存ComponentDialogProperty ID的地方
	 */
	public static final String COMPONENTDIALOGPROPERTYKEY = "componentdialogproperty_key";
	
	/**
	 * MAP中保存mainEditor的地方
	 */
	public static final String FIXMAINEDITOR_INSTANCE = "FIXMAINEDITOR_INSTANCE_KEY";
	/**
	 * MAP中保存控件ID的地方
	 */
	public static final String FIXCOMPONENT_ID = "FIXCOMPONENT_ID_KEY";
	/**
	 * MAP中保存控件Event值的地方的地方
	 */
	public static final String FIXCOMPONENTEVENTVALUE = "FIXCOMPONENTEVENTVALUE_KEY";
	/**
	 * 设置custom脚本块
	 */
	public static final String FIXFORMCUSTOMEVENT = "body_custom";
	/**
	 * 设置组件类型
	 */
	public static final String FIXBUTTONCOMPONENTFUNC = "func";
	/**
	 * 设置组件属性
	 */
	public static final String FIXBUTTONCOMPONENTREFC = "refc";
	
	/**
	 * 上面的中文，用以显示在属性框的第一个
	 */
	public static final String FIXCOMPONENTSKEYCN = "控件类型";
	
	/**
	 * [类名]<br>
	 * Const.EditorTab.java<br>
	 * <br>
	 * [功能概要]<br>
	 * 		编辑器的多个分页的文本
	 * <br>
	 * [变更履历]<br>
	 *
	 * <br>
	 * 2011-6-23 ver1.0 <br>
	 * <br>
	 *
	 * @作者 BB
	 *
	 */
	public static class EditorTab
	{
		public static final String EDITOR = "设计";
		public static final String HTML = "源码";
		public static final String JS = "JS";
		public static final String JAVA  ="JAVA";
	}
	
	/**
	 * 引用对象的结构
	 * @author BB
	 *
	 */
	public static class ElementInfo
	{
		//对象名称如style,button,combobox
		public String name;
		//组别：如all,normal,advance
		public String categorys;
		//级别，主属性为0
		public int level = 0;
	}
	
	/**
	 * 选中的业务对象信息
	 * @author BB
	 *
	 */
	public static class SelfBizObjInfo
	{
		//数据源
		public String DataSource;
		//业务对象名
		public String BizObjName;
		//字段
		public DBEntity[] Columns;
		//字段列表
		public String ColumnNames;
	}
	
	/**
	 * 属性页分类
	 */
	public static HashMap<String,String> Categorys = (new _Categorys()).type;
	static class _Categorys
	{
		public static final HashMap<String,String> type = new HashMap<String,String>();
		private _Categorys()
		{
			type.put("advance", "高级");
			type.put("normal", "常规");
			type.put("server", "后台");
			type.put("event", "事件");
			type.put("verify", "校验");
			type.put("action", "操作");
		}
	}
	
	
	/**
	 * 配置中需要取大文本的配置项
	 * 值标识是否进行trim操作
	 */
	public static HashMap<String,Boolean> XmlConfigTextItem = (new _XmlConfigTextItem()).item;
	static class _XmlConfigTextItem
	{
		public static final HashMap<String,Boolean> item = new HashMap<String,Boolean>();
		private _XmlConfigTextItem()
		{
			item.put("description", true);
			item.put("jsonconfig", false);
			item.put("instanse", false);
			item.put("default", false);
			item.put("eventdefault", false);
		}
	}
	
	
}
