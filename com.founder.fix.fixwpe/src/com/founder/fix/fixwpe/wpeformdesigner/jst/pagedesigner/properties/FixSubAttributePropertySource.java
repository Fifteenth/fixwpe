package com.founder.fix.fixwpe.wpeformdesigner.jst.pagedesigner.properties;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.json.JSONException;
import org.json.JSONObject;

import com.founder.fix.fixwpe.wpeformdesigner.XmlPropBufferProvider;
import com.founder.fix.fixwpe.wpeformdesigner.ui.properties.editor.WPECustomDialogPropertyDescriptor;
import com.founder.fix.fixwpe.wpeformdesigner.ui.properties.editor.WPEFormPropertyUtils;
import com.founder.fix.ocx.util.FormConst;


public class FixSubAttributePropertySource implements IPropertySource {

	/*
	 *	@author Fifteenth
	 *		子属性页中没有模型json的概念
	 *	只有操作json(_actionJson)的概念
	 *	来源有两情况：
	 *		1.属性页模型json数组的一个元素
	 *		2.子属性页的操作json
	 */
	private JSONObject _actionJson;
	
	/*
	 *	@author Fifteenth
	 *		childActionJson:调用的子属性页的_actionJson
	 */
	private JSONObject childActionJson;
	

	public void setChildActionJson(JSONObject childActionJson) {
		this.childActionJson = childActionJson;
	}

	private int _propertyJsonNum;
	
	/*
	 *	@author Fifteenth
	 *		该实例是子属性页的实例
	 *	调用该实例有两种情况
	 *		1.属性页实例调用(_fixPropertySource)
	 *		2.子属性页实例调用(_fixsubPropertySource)
	 */
	private FixPropertySource _fixPropertySource;
	private FixSubAttributePropertySource _fixsubPropertySource;
	
	/*
	 *	@author Fifteenth
	 *		修改属性退出标记
	 */
	private Boolean outFlag;
	private String _parentNodeName;
	private HashMap<String,ArrayList<HashMap<String,Object>>> _parentNodeMap;
	
	private HashMap<String,HashMap<String,Object>> _parentProperyMap = new HashMap<String,HashMap<String,Object>>();
	
	private Object saveValue;
	private Object saveId;
	private boolean childSaveMark=false;
	
	private HashMap<String, String> _chanslateMap;
	
	private int _childLevel;
	
	
	private HashMap<String,ArrayList<HashMap<String,Object>>> _childPropertyMap = new HashMap<String,ArrayList<HashMap<String,Object>>>();
	
	

	FixSubAttributePropertySource(FixPropertySource fixPropertySource,String openedNodeName,
			int propertyJsonNum,HashMap<String,ArrayList<HashMap<String,Object>>> parentNodeMap){
		_fixPropertySource = fixPropertySource;
		_propertyJsonNum = propertyJsonNum;
		_actionJson = _fixPropertySource.getChildModelJsons()[_propertyJsonNum];
		_parentNodeName = openedNodeName;
		_childLevel = 1;//第一层子节点
		_parentNodeMap = parentNodeMap;
		
		outFlag = false;
	}
	
	
	FixSubAttributePropertySource(FixSubAttributePropertySource subPropertySource,String openedNodeName,
			int childLevel,HashMap<String,ArrayList<HashMap<String,Object>>> parentNodeMap){
		_fixsubPropertySource = subPropertySource;
		_actionJson = subPropertySource.childActionJson;
		_parentNodeName = openedNodeName;
		_childLevel = childLevel+1;
		_parentNodeMap = parentNodeMap;
	}
	
	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		
		_chanslateMap = new HashMap<String,String>();
		
		List<HashMap<String,Object>> propertylist;
		
		/*
		 *	@author Fifteenth
		 *		两种情况：
		 *		1.属性页调用子属性页(_childLevel == 1)
		 *		2.子属性也调用子属性页(_childLevel > 1)
		 */
			
		propertylist = _parentNodeMap.get(_parentNodeName);
		
		List <PropertyDescriptor>descriptorList = new ArrayList<PropertyDescriptor>();
		
		int descriptorsCount = 0;
		
		Iterator childJsonKeys=_actionJson.keys();
		
		if(propertylist.size()==_actionJson.length()+1){
			descriptorsCount = 1;
		}
		
		if(childJsonKeys!=null){
			while(childJsonKeys.hasNext()){
				//stringTemp键值
				String key = childJsonKeys.next().toString();
				/*
				 *	@author Fifteenth
				 *		tag_prop_map是组件的某个属性的map
				 */
				HashMap<String,Object> tag_prop_map = propertylist.get(descriptorsCount);
				String name = tag_prop_map.get("name").toString();
				String caption = tag_prop_map.get("caption").toString();
				
				
				Object categoryObj = tag_prop_map.get("category");
				
				if ( categoryObj == null ) {
					categoryObj = "normal";
				}
				_parentProperyMap.put(name, tag_prop_map);
				/*
				 *	@author Fifteenth
				 *		1.name可以理解为_en
				 *	getPropertyValue和setPropertyValue传递的id值
				 *		2.caption解决为_cn
				 *		所以操作模型的时候需要_chanslateMap
				 */
				_chanslateMap.put(name, caption);
				
				Object displaytype = tag_prop_map.get("displaytype");
				if ( displaytype == null ) displaytype = "edit";
				String dispType = displaytype.toString();
				
				Object showstate = tag_prop_map.get("showstate");
				if ( showstate == null ) showstate = "normal";
				boolean readOnly = !showstate.toString().equals("normal");
				
				String category = categoryObj.toString();
				if ( FormConst.Categorys.containsKey(category) )
					category = FormConst.Categorys.get(category);
				List<String> comboboxlist = new ArrayList<String>();
				String[] comboboxs = new String[]{};
				if ( dispType.equals("combobox") )
				{
					//WPE版
					comboboxlist = (List<String>) tag_prop_map.get("combobox");
					
					if ( comboboxlist != null )
						comboboxs = (String[])comboboxlist.toArray(new String[]{});				
				}			
				
				PropertyDescriptor propDes = null;
				if ( dispType.equals("combobox") ){
					//第一个参数是下面的id值来源
					propDes = new ComboBoxPropertyDescriptor(name,caption,comboboxs);
				}
				else if ( dispType.equals("dialog") )
				{
					if(tag_prop_map.containsKey("element")){
						_childPropertyMap.put(name, XmlPropBufferProvider.
								globleXmlMap.get(tag_prop_map.get("element").toString()));
						// 打开子属性页必须禁用编辑，不然会有异常
						propDes = new PropertyDescriptor(name,caption);
					}else{
						propDes = new WPECustomDialogPropertyDescriptor(
								tag_prop_map,_actionJson,_chanslateMap,_parentNodeMap,_parentNodeName);
					}
					
//					propDes = new WPECustomDialogPropertyDescriptor(
//							tag_prop_map,_actionJson,_chanslateMap,_parentNodeMap,_parentNodeName);
					
					
//					propDes = new PropertyDescriptor(name,caption);
				}else if( dispType.equals("jsEditor") ){
					
				}
//				else if ( descriptorsCount == 0 )
//				{
//					propDes = new PropertyDescriptor(FormConst.FIXCOMPONENTSKEY,FormConst.FIXCOMPONENTSKEYCN);
//				}
				else if ( readOnly )
				{
					propDes = new PropertyDescriptor(name,caption);
				}
				else {
					propDes = new TextPropertyDescriptor(name,caption);
				}
				Object description = tag_prop_map.get("description");
				if ( description != null )
				{
					propDes.setDescription(description.toString());
				}
				descriptorList.add(propDes);
				descriptorsCount++;
			}
		}
		int descriptorLength = descriptorList.size();
		PropertyDescriptor[] descriptors = new PropertyDescriptor[descriptorLength];
		
		for(int i=0;i<descriptorLength;i++){
			descriptors[i] = descriptorList.get(i);
		}
		return descriptors;
	}

	public Object getPropertyValue(Object id) {
		
		
		/*
		 *	@author Fifteenth
		 *		key是组件的某个属性
		 */
		String key_en = id.toString();
		/*
		 *	@author Fifteenth
		 *		tag_ttr_map是组件（第一个参数）的某个属性（第二个参数）的map
		 */
		try {
			
			/*
			 *	@author Fifteenth
			 *		需要将key值翻译一下
			 */
			if(_chanslateMap.get(key_en)!=null){
//				String tempKey_cn = _chanslateMap.get(key_en).toString();
				if(_childPropertyMap!=null&&_actionJson.has(key_en)){
					Object valueObject = _actionJson.get(key_en);
					
					/*
					 *	@author Fifteenth
					 *		分为有子节点和无子节点两种
					 *			有子节点又分为打开和弹出两种情况
					 */
//					_parentNodeName
					if(valueObject instanceof JSONObject
							&&_parentProperyMap.get(key_en)!=null
							&&_parentProperyMap.get(key_en).get("displaytype")!=null
							&&_parentProperyMap.get(key_en).get("displaytype").equals("dialog")){
						/*
						 *	@author Fifteenth
						 *		1. 根据配置子属性页或者弹出
						 * 
						 * 		2. 需要在这里触发调用子属性页
						 */
//							if(1==2){
//								//弹出
//								return valueObject;
//							}else{
							childActionJson = (JSONObject)valueObject;
							//子属性页
							FixSubAttributePropertySource fixSubPropertySourc = 
									new FixSubAttributePropertySource(this,id.toString()
											,_childLevel,_childPropertyMap);
							return fixSubPropertySourc;
//							}
					}else{
						//当不带子属性时，沿用ocx体系
						Map<String,Object> tag_ttr_map = _parentProperyMap.get(key_en);
						return FixPropertySource.ocxValue(valueObject,tag_ttr_map);
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ""; //$NON-NLS-1$
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	public void setPropertyValue(Object id, Object value) {
		
//		String key_cn = _chanslateMap.get(id);
		
		if(outFlag==null||!outFlag){
			
			if(_childLevel==1){
				
				if(value!=null){
					
					setModelJson(id,value);
				}
				
				//通知父节点修改属性
				_fixPropertySource.setChildModelJsons(new JSONObject[]{null,_actionJson});
				//与else情况不同的地方，这里需向_fixPropertySource报告
				_fixPropertySource.setChildSaveMark(true);
			}
			else{
				
				setModelJson(id,value);
				_fixsubPropertySource.setChildActionJson(_actionJson);
				
				/*
				 *	@author Fifteenth
				 *		两种情况：
				 *		1.该实例没有子属性实例
				 *		2.该实例的子属性实例已处理完成，
				 *	当子属性实例处理完成也会调用父属性实例Fifteenth@1
				 */
				
			}
		}
		/*
		 *	@author Fifteenth	
		 *		先见Fifteenth@1处描述
		 *		_fixPropertySource!=null表示此时该实例为_childLevel=1，
		 *	当_childLevel=1时需要置标志下次进入该方法时退出
		 */
		if(_fixPropertySource!=null){
			outFlag = true;
		}else{
			if(_childLevel==1){
				System.out.println("warning!!!!!!!!!!!!!!!position:FixSubAttributePropertySource");
			}
		}
	}
	
	
	public void setModelJson(Object id,Object value){
		
		
		saveId = id;
		saveValue = value;
		
		
		String key_en = id.toString();
//		String key_ch = _chanslateMap.get(key_en).toString();
		
		
		
		Map<String,Object> tag_ttr_map = _parentProperyMap.get(key_en);
		Object displaytype = tag_ttr_map.get("displaytype");
		Object showstate = tag_ttr_map.get("showstate");
		Object jsontype = tag_ttr_map.get("jsontype");
		
		if (displaytype.equals("combobox")){	
			List<String> comboboxlist = WPEFormPropertyUtils.getPropComboList(_actionJson,
					showstate.toString(),tag_ttr_map);	
			
			value = comboboxlist.get(Integer.parseInt(value.toString()));
			
			if(jsontype!=null&&jsontype.equals("boolean")){
				if(value.equals("true")){
					value = true;
				}else{
					value = false;
				}
			}
		}
		
		try {
			_actionJson.put(key_en, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
