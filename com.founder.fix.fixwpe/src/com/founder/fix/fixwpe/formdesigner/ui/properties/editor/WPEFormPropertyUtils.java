package com.founder.fix.fixwpe.formdesigner.ui.properties.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.json.JSONException;
import org.json.JSONObject;

import com.founder.fix.apputil.util.JSONUtil;
import com.founder.fix.studio.FixPerspective;
import com.founder.fix.studio.formdesigner.common.FormConst;
import com.founder.fix.studio.formdesigner.common.FormConst.ElementInfo;
import com.founder.fix.studio.formdesigner.common.VerifyUtil;
import com.founder.fix.studio.formdesigner.ui.MainEditor;
import com.founder.fix.studio.formdesigner.ui.htmleditor.DHtmlConst;
import com.founder.fix.studio.platformdesigner.Entity.project.EventEntity;
import com.founder.fix.studio.platformdesigner.Entity.project.ViewComponentEntity;
import com.founder.fix.studio.platformdesigner.interfaces.StudioInterface;
import com.founder.fix.studio.platformdesigner.views.EventView;

public class WPEFormPropertyUtils {

	
//	//OCX版
//	/**
//	 * #{func}:advance取引用对象表达式
//	 * 前面是对象的name，后面是分级，无则为全部
//	 * @param eleStr
//	 * @return
//	 */
//	public static ElementInfo getElConfExp(String eleStr,Map<String,Object> map)
//	{
//		String[] str = eleStr.split(":");
//		String Str1 = str[0];
//		if ( Str1.startsWith("#{") )
//		{
//			Str1 = Str1.substring(2, Str1.length()-1);
//			Object o = map.get(Str1);
//			if ( o==null ) o = "";
//			Str1 = o.toString();
//		}
//
//		str[0] = Str1;
//		
//		ElementInfo ei = new ElementInfo();
//		ei.name = str[0];
//		if ( str.length > 1 )
//		ei.categorys = str[1];
//		
//		return ei;
//	}
	
	
	/**
	 * #{func}:advance取引用对象表达式
	 * 前面是对象的name，后面是分级，无则为全部
	 * @param eleStr
	 * @return
	 */
	public static ElementInfo getElConfExp(String eleStr,JSONObject json)
	{
		String[] str = eleStr.split(":");
		String Str1 = str[0];
		if ( Str1.startsWith("#{") )
		{
			Str1 = Str1.substring(2, Str1.length()-1);
			Object o;
			try {
				o = json.get(Str1);
				if ( o==null ) o = "";
				Str1 = o.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		str[0] = Str1;
		
		ElementInfo ei = new ElementInfo();
		ei.name = str[0];
		if ( str.length > 1 )
		ei.categorys = str[1];
		
		return ei;
	}
	
	/**把常规类别的字符串转成正常的JSON
	 * @param str
	 * @return
	 */
	public static String getNormalJsonString(Object value)
	{
		if ( value == null || value.equals("") )
			value = "{}";
		else if (!value.toString().startsWith("{"))
		{
			String tmpValue = value.toString(); 
			Map<String, Object> tmpVerify = new HashMap<String,Object>();
			tmpVerify = JSONUtil.parseJSON2MapFirstLevel("{"+tmpValue+"}");
			if ( tmpVerify.size() == 0 )
			{
				tmpValue = ""; 
				String[] valueList = value.toString().split(";");
				for ( int i = 0; i < valueList.length; i++ )
				{
					String VName = valueList[i];
					String VValue = VName.substring(VName.indexOf(":")+1);
					VName = VName.substring(0,VName.length()-VValue.length()-1);
					tmpValue += VName.trim().toLowerCase() + ":\"" + VValue.trim() + "\",";
				}
				if ( tmpValue.length() > 0 )
					tmpValue = tmpValue.substring(0,tmpValue.length()-1);
				
			}
			else
			{
				tmpValue = "";
				for(String VName : tmpVerify.keySet())
				{
					String VValue = tmpVerify.get(VName).toString();
					tmpValue += VName.trim().toLowerCase() + ":\"" + VValue.trim() + "\",";
				}
				if ( tmpValue.length() > 0 )
					tmpValue = tmpValue.substring(0,tmpValue.length()-1);
			}
			value = "{"+tmpValue+"}";
		}
		
		
		return value.toString();
	}
	
	/**
	 * 显示颜色选择
	 * @param color
	 * @return
	 */
	public static String showColorDialog(Composite parent)
	{
		
		ColorDialog cd = new ColorDialog(parent.getShell());
		RGB rvl = cd.open();
		if ( rvl != null )
		{
			String R = Integer.toHexString(rvl.red);
			String G = Integer.toHexString(rvl.green);
			String B = Integer.toHexString(rvl.blue);
			R = R.equals("0")?"00":R;
			G = G.equals("0")?"00":G;
			B = B.equals("0")?"00":B;
			return ("#"+R+G+B).toUpperCase();
		}
		return null;
	}
	
	
//	/**
//	 * 取属性的列表
//	 * @param mainEditor
//	 * @param list
//	 * @return
//	 */
//	public static List<String> getPropComboList(MainEditor mainEditor,String showstate,Map<String,Object> prop)
//	{
//		Map<String,Object> map = mainEditor.getDhtml().getElementProperty();	
//		
//		List<String> list = null;
//		//要求列出组件
//		if ( "components".equals(showstate.toString()) )
//		{
//			String tagName = "";
//			if ( map != null )
//				tagName = map.get(FormConst.FIXCOMPONENTSKEY).toString();
//			list = mainEditor.getDhtml().getComponentsTagName(tagName);
//			//在第一行加个空，以便取消选择的组件
//			list.add(0, "");
//		}
//		//验证规则
//		else if ("verifies".equals(showstate.toString())) {
//			list = VerifyUtil.getAllVerifiesNames();
//		}
//		else
//			list = (ArrayList<String>)prop.get("combobox");
//		
//		if ( list == null ) list = new ArrayList<String>();
//		
//		//需要列出样式来
//		if ( list.size() > 0 && list.get(0).startsWith("%{style:"))
//		{
//			String cssTagName = list.get(0).substring(8,list.get(0).length()-1);
//			String cssNames = mainEditor.getDhtml().Execute(DHtmlConst.EXECUTE_GetCssNames, cssTagName, "");
//			list.clear();
//			if ( cssNames != null )
//			{
//				String[] css = cssNames.split(",");
//				for ( int i = 0; i < css.length; i++ )
//				{
//					list.add(css[i]);
//				}
//				
//			}
//		}
//		
//		return list;
//		
//	}
	
	
	/**
	 * 取属性的列表
	 * @param mainEditor
	 * @param list
	 * @return
	 */
	public static List<String> getPropComboList(JSONObject tag_json,String showstate,Map<String,Object> prop)
	{
//		Map<String,Object> map = mainEditor.getDhtml().getElementProperty();	
		
		List<String> list = null;
		//要求列出组件
		if ( "components".equals(showstate.toString()) )
		{
			String tagName = "";
			if ( tag_json != null )
				try {
					tagName = tag_json.get(FormConst.FIXCOMPONENTSKEY).toString();
					
					list = (List<String>) tag_json.get(tagName);
					//在第一行加个空，以便取消选择的组件
					list.add(0, "");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		//验证规则
		else if ("verifies".equals(showstate.toString())) {
			list = VerifyUtil.getAllVerifiesNames();
		}
		else
			list = (ArrayList<String>)prop.get("combobox");
		
		if ( list == null ) list = new ArrayList<String>();
		
		//需要列出样式来
//		if ( list.size() > 0 && list.get(0).startsWith("%{style:"))
//		{
//			String cssTagName = list.get(0).substring(8,list.get(0).length()-1);
//			String cssNames = mainEditor.getDhtml().Execute(DHtmlConst.EXECUTE_GetCssNames, cssTagName, "");
//			list.clear();
//			if ( cssNames != null )
//			{
//				String[] css = cssNames.split(",");
//				for ( int i = 0; i < css.length; i++ )
//				{
//					list.add(css[i]);
//				}
//				
//			}
//		}
		
		return list;
		
	}
	
	/**
	 * 初始化表单事件数据
	 */
	public static void initEventView() {
		// 重新初始化‘验证列表’视图数据
		EventView eventView = (EventView) StudioInterface
				.getViewPartFromViewId(FixPerspective.EVENT_VIEW);
		if (eventView != null) {
			eventView.init();
		}
	}
	
	
	/**
	 * 取表单所有事件列表
	 * EventEntity中的JsComponentId中控件的ID，JsEventName是事件的属性名(如click)，JsComponentId_JsEventName拼接后就是事件的函数名
	 * @param mainEditor
	 * @return
	 */
	public static List<ViewComponentEntity> getViewComponentEntitys(MainEditor mainEditor)
	{
		List<ViewComponentEntity> resultList = new ArrayList<ViewComponentEntity>();
		String json = mainEditor.getDhtml().Execute(DHtmlConst.EXECUTE_GetComponentEventList, "", "");
		
		if (json == null) {
			return null;
		}
		
		List<Object> list = JSONUtil.parseJSON2List(json);
		for (int i = 0; i < list.size(); i++ )
		{
			
			Map<String,Object> map = (Map)list.get(i);
			String jsComponentId = map.get("jsComponentId").toString();
			List<Object> listEvent = (List)map.get("event");
			
			ViewComponentEntity _ViewComponentEntity = new ViewComponentEntity();
			_ViewComponentEntity.setJsComponentId(jsComponentId);
			_ViewComponentEntity.setJsComponentName(jsComponentId);
			resultList.add(_ViewComponentEntity);
			
			List<EventEntity> listEventEntity = new ArrayList<EventEntity>();
			
			for ( int j =0; j < listEvent.size(); j++ )
			{
				Map<String,Object> mapEvent = (Map)listEvent.get(j);
				//"jsEventId":"change","jsContext":"function text_0_change()\n{\n\t111\n}\n","jsEventName":"text_0_change"
				String jsEventId = mapEvent.get("jsEventId").toString();
				String jsEventName = mapEvent.get("jsEventName").toString();
				String jsContext = mapEvent.get("jsContext").toString();
				
				//jsEventId是事件名称，jsEventName是事件函数值
				EventEntity _EventEntity = new EventEntity();
				_EventEntity.setJsComponentId(jsComponentId);
				_EventEntity.setJsEventName(jsEventId);
				_EventEntity.setJsContext(jsContext);
				listEventEntity.add(_EventEntity);
				_ViewComponentEntity.setEvents(listEventEntity);
			}
		}
		return resultList;
	}
	
}
