package com.founder.fix.fixwpe.wpeformdesigner.ui.properties.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.json.JSONException;
import org.json.JSONObject;

import com.founder.fix.apputil.util.JSONUtil;
import com.founder.fix.fixwpe.formdesigner.ui.properties.editor.FixComboBoxPropertyDescriptor;
import com.founder.fix.fixwpe.formdesigner.ui.properties.editor.FixTextPropertyDescriptor;
import com.founder.fix.fixwpe.wpeformdesigner.XmlPropBufferProvider;
import com.founder.fix.fixwpe.wpeformdesigner.jst.pagedesigner.itemcreation.AbstractTagCreatorProvider;
import com.founder.fix.ocx.util.FormConst;
import com.founder.fix.ocx.util.FormConst.ElementInfo;
import com.founder.fix.ocx.util.HtmlNodePropertySource;

/**
 * [类名]<br>
 * 用以设定属性窗口的各类属性 <br>
 * [功能概要]<br>
 * 属性的第三层 <br>
 * [变更履历]<br>
 * 
 * <br>
 * 2011-6-20 ver1.0 <br>
 * <br>
 * 
 * @作者 BB
 * 
 */
public class WPEComponentDialogProperty extends HtmlNodePropertySource {

//	//OCX版
//	// 主设计器
//	private MainEditor mainEditor = null;
	
	
	private Map<String, Object> _node_map = null;
	private Map<String, Object> _childNode_map = null;
	
	private JSONObject _node_json = null;
	private JSONObject _childNode_json = null;
	
	private HashMap<String,String> _chanslateMap;
	
	
	private ElementInfo elementInfo = null;
	private WPEPropertySheetDialog dialog;

	
	private List<Map<String, String>> isNullList;


	ArrayList<HashMap<String, Object>> list = null;

	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}

	public ArrayList<String> getBizObjPropertyList() {
		ArrayList<String> list1 = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			String name = map.get("name").toString();
			if (map.containsKey("dialogfunc")) {
				String dialogfunc = map.get("dialogfunc").toString();
				if (dialogfunc.startsWith("bizobj:"))
					list1.add(name);
			}
		}
		return list1;
	}

	boolean isInit = true;

	/**
	 * 
	 */
	public WPEComponentDialogProperty(ElementInfo elementInfo, 
			HashMap<String, Object> node_json, JSONObject node_map,
			WPEPropertySheetDialog dialog) {
		this.elementInfo = elementInfo;
		this.elementInfo.level = 1;
		
		this._node_map = node_json;
		this._node_json = node_map;
		
		this.dialog = dialog;
		isNullList = new ArrayList<Map<String, String>>();
		
	}

	/*
	 * 重置属性视图
	 * 
	 * @see
	 * com.founder.fix.studio.formdesigner.adapter.tree.HtmlNodePropertySource
	 * #getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		_chanslateMap = new HashMap();
		
		list = XmlPropBufferProvider.globleXmlMap.get(elementInfo.name);
				
		if(list == null){
			return new PropertyDescriptor[0];
		}
		
		PropertyDescriptor[] pds = new PropertyDescriptor[list.size() - 1];
		
		/*
		 *	@author Fifteenth
		 *		是个嵌套一层的Map，每个子Map对应一个pds[]
		 */
		_childNode_map = new HashMap();

		// 以下为属性赋入键值，以及分组
		for (int i = 1; i < list.size(); i++) {
			HashMap tempMap = list.get(i);
			String name = tempMap.get("name").toString();
			String caption = tempMap.get("caption").toString();
			
			_childNode_map.put(name, list.get(i));
			
			/*
			 *	@author Fifteenth
			 *		wpe版需要翻译一下
			 */
			_chanslateMap.put(name, caption);
			
			Object displaytype = tempMap.get("displaytype");
			if (displaytype == null)
				displaytype = "edit";
			String dispType = displaytype.toString();

			Object showstate = tempMap.get("showstate");
			if (showstate == null)
				showstate = "normal";
			boolean readOnly = !showstate.toString().equals("normal");

			Object categoryObj = tempMap.get("category");
			if (categoryObj == null)
				categoryObj = "normal";
			String category = categoryObj.toString();
			if (FormConst.Categorys.containsKey(category))
				category = FormConst.Categorys.get(category);
			List<String> comboboxlist = new ArrayList<String>();
			String[] comboboxs = new String[] {};
			
			if (dispType.equals("combobox")) {
				comboboxlist = WPEFormPropertyUtils.getPropComboList(
						_node_json, showstate.toString(), tempMap);

				if (comboboxlist != null)
					comboboxs = (String[]) comboboxlist
							.toArray(new String[] {});
			}

			//封装各个属性框的组件
			PropertyDescriptor propDes = null;
			if (dispType.equals("combobox"))
				propDes = new FixComboBoxPropertyDescriptor(name, caption,
						comboboxs, (HashMap<String, Object>) tempMap, dialog.getText());
			else if (dispType.equals("dialog")) {
				//OCX版
//				map.put(FormConst.FIXMAINEDITOR_INSTANCE, this.mainEditor);
				//WPE版
				tempMap.put(FormConst.FIXCOMPONENT_ID,
						this._node_map.get(FormConst.FIXCOMPONENT_ID));
				tempMap.put(FormConst.FIXCOMPONENTSKEY,
						this._node_map.get(FormConst.FIXCOMPONENTSKEY));
				tempMap.put(FormConst.COMPONENTDIALOGPROPERTYKEY, this);
				
				/*
				 *	@author Fifteenth
				 *		在开始处理前，先得到子节点信息
				 */
				String childKey =  _node_map.get("caption").toString();
				try {
					if(	_node_json.has(childKey) &&
							_node_json.get(childKey) instanceof JSONObject){
						_childNode_json = (JSONObject) _node_json.get(childKey);
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					/*
					 *	@author Fifteenth
					 *		初始化好所有的子属性json
					 */
					if(_childNode_json.get(caption) instanceof JSONObject){
						_childNode_json = _childNode_json.getJSONObject(caption);
						/*
						 *	@author Fifteenth
						 *		list.get(i)这个参数是用来设置属性框的property和value
						 */
						propDes = new WPECustomDialogPropertyDescriptor(
								(HashMap<String, Object>) list.get(i),
								_childNode_json,_chanslateMap,dialog.getText(),null,"");
					}else{
						propDes = new FixTextPropertyDescriptor(name, caption,
								(HashMap<String, Object>) tempMap, dialog.getText());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (readOnly) {
				propDes = new PropertyDescriptor(name, caption);
			} else
				propDes = new FixTextPropertyDescriptor(name, caption,
						(HashMap<String, Object>) tempMap, dialog.getText());

			Object description = tempMap.get("description");
			if (description != null) {
				propDes.setDescription(description.toString());
//				dialog.getText().setText(
//						((dialog.getText().getText() == null
//								|| dialog.getText().getText().equals("")) ? ""
//								: dialog.getText().getText() + "\r\n") +
//								"[" + caption + "] -- " + description.toString());
			}
			propDes.setCategory(category);
			// propDes.
			pds[i - 1] = propDes;

			/*
			if (isInit && this.elementInfo.name.equals("style")
					&& "normal".equals(category)) {
				String value = this.mainEditor.getDhtml().GetElementProperty(
						"style:" + name);
				if (value != null && !value.equals("")) {
					if (this.PropertyInfo.containsKey(name)) {
						this.PropertyInfo.remove(name);
					}
					this.PropertyInfo.put(name, value);
				}
			}
			 */
		}

		isInit = false;
		return pds;
	}

	/*
	 * 重画属性视图是赋值
	 * 
	 * @see
	 * com.founder.fix.studio.formdesigner.adapter.tree.HtmlNodePropertySource
	 * #getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id) {

		HashMap tempMap = (HashMap) _childNode_map.get(id.toString());
		String key = (String) _chanslateMap.get(id.toString());
		

		//OCX版
//		Map<String, Object> map = this.PropertyInfo;

		String FixControlKey = this.elementInfo.name;

//		Map<String, Object> prop = this.mainEditor.getXmlPropBuffer()
//				.getProperty(FixControlKey, tmpKey);

		// if ( map.containsKey(id) )
		{
			Object value = "";
			if (_node_json.has(key))
				try {
					value = _node_json.get(key).toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			Object category = tempMap.get("category");

			if ((value == null || value.equals(""))
					&& tempMap.containsKey("default")
					&& !tempMap.get("default").toString().equals("")) {

				value = tempMap.get("default").toString();
				// 修改缓存的MAP，以保存修改的结果
//				if (this.PropertyInfo.containsKey(key)) {
//					this.PropertyInfo.remove(key);
//				}
//				if (value != null && !value.equals("")){
//					this.PropertyInfo.put(key, value);
//				}
					
			}

			
			if (tempMap.containsKey("displaytype")
					&& tempMap.get("displaytype").toString().equals("combobox")) {
				Object showstate = tempMap.get("showstate");
				if (showstate == null)
					showstate = "normal";
				List<String> list = WPEFormPropertyUtils.getPropComboList(
						_node_json, showstate.toString(), tempMap);
				if (value != null) {
					value = list.indexOf(value);
				}
			}

			// 事件
			if ("event".equals(category)) {

				String ctlId = FixControlKey;
				if (_node_json.has("id"))
					try {
						ctlId = _node_json.get("id").toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				value = ctlId + "_" + id;
			}
			
			// 筛选必填项
			if(tempMap.get("isNull") != null
					&& tempMap.get("isNull").toString().equals("false")) {
				Map<String, String> descMap = new HashMap<String, String>();
				descMap.put("key", key);
				descMap.put("caption", String.valueOf(tempMap.get("caption")));
				isNullList.add(descMap);
			} 
			
			return value;
		}
		// return null;
	}

	/*
	 * 修改属性时取值以更新到DHTML
	 * 
	 * @see
	 * com.founder.fix.studio.formdesigner.adapter.tree.HtmlNodePropertySource
	 * #setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	public void setPropertyValue(Object id, Object value) {
//		Map<String, Object> prop = this.mainEditor.getXmlPropBuffer()
//				.getProperty(this.elementInfo.name, id.toString());
		
		Map<String, Object> prop = XmlPropBufferProvider.getXMLProperty(this.elementInfo.name, id.toString());
		
		Object displaytype = prop.get("displaytype");
		Object category = prop.get("category");
		if (category == null)
			category = "normal";

		if ("combobox".equals(displaytype)) {
			Object showstate = prop.get("showstate");
			if (showstate == null)
				showstate = "normal";
			List<String> comboboxlist = WPEFormPropertyUtils.getPropComboList(
					_node_json, showstate.toString(), prop);

			value = comboboxlist.get(Integer.parseInt(value.toString()));
		}

		// 修改缓存的MAP，以保存修改的结果
		if (_node_json.has(id.toString())) {
			_node_json.remove(id.toString());
		}
		if (value != null && !value.equals(""))
			try {
				_node_json.put(id.toString(), value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public String getValue() {
		String Value = "";

		Iterator iterator = _node_json.keys();
		
		//先进行为空判断，若必填项未填则不添加进keys中
		List<String> keys = new ArrayList<String>();
		while (iterator.hasNext()) {
			keys.add(iterator.next().toString());
		}
		//isNullList在getPropertyValue方法中赋值
		for (int i = 0; i < isNullList.size(); i++) {
			Map<String, String> descMap = isNullList.get(i);
			String isNull = descMap.get("key");
			//必填项未填完全则返回提示
			if(!keys.contains(isNull)) {
				return "isNull." + descMap.get("caption");
			} else
				try {
					if(this._node_json.get(isNull).toString().equals("")) {
						return "isNull." + descMap.get("caption");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		//通过为空判断才能继续往下走
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = "";
			try {
				value = _node_json.get(key).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Map<String, Object> prop = _node_map;
			if (prop == null)
				continue;
			Object jsontype = prop.get("jsontype");
			String jsonType = jsontype == null ? "string" : jsontype.toString();

			if (this.elementInfo.name.equals("style")
					|| jsonType.equals("number")
					|| jsonType.equals("bool"))
//				Value += "\""+key +"\"" + ":" + value + "";
				Value += key + ":" + value + "";
			else if (jsonType.equals("object"))// &&
			{
				if (!value.startsWith("{"))
					value = "{" + value + "}";
//				Value += "\""+key +"\"" + ":" + value + "";
				Value += key + ":" + value + "";
			} else if (jsonType.startsWith("array"))// &&!value.startsWith("["))
			{
				if (!value.startsWith("["))
					value = "[" + value + "]";
//				Value += "\""+key +"\"" + ":" + value + "";
				Value += key  + ":" + value + "";
			} else if (jsonType.equals("boolean"))// &&!value.startsWith("["))
			{
//				Value += "\""+key +"\"" + ":" + value + "";
				Value += key  + ":" + value + "";
			} else // if (jsonType.equals("string"))
			{
//				Value += "\""+key +"\""+ ":\"" + value + "\"";
				Value += key + ":\"" + value + "\"";
			}

			if (this.elementInfo.name.equals("style"))
				Value += ";";
			else
				Value += ",";
		}

		if (Value != null && !Value.equals(""))
			Value = Value.substring(0, Value.length() - 1);

		if (this.elementInfo.name.equals("style"))
		{
			//样式表不需要进行前后端加{}操作
		}
		else
		{
			Object jsontype = this._node_map.get("jsontype");
			String jsonType = jsontype == null ? "string" : jsontype.toString();
			if (jsonType.equals("object") && !Value.startsWith("{"))
				Value = "{" + Value + "}";
			else if (jsonType.startsWith("array") && !Value.startsWith("[")) {
				if((_node_map.get("isArrayDialog") != null 
						&& _node_map.get("isArrayDialog").toString().equals("true"))) {
					Value = "{" + Value + "}";
				} else {
					Value = "[" + Value + "]";
				}
			}
	
			// 如果value中不被{}包含的话要加上
			if (!Value.startsWith("{") && !Value.startsWith("[")
					&& !Value.endsWith("}") && !Value.endsWith("]")) {
				Value = "{" + Value + "}";
			}
		}
		return Value;
	}

	public List<Map<String, String>> getIsNullList() {
		return isNullList;
	}

	public void setIsNullList(List<Map<String, String>> isNullList) {
		this.isNullList = isNullList;
	}

}
