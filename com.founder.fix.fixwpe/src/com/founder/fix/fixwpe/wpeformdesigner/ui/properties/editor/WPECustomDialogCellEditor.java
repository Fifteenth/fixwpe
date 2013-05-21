package com.founder.fix.fixwpe.wpeformdesigner.ui.properties.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.jsdt.core.IJavaScriptElement;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.ui.JavaScriptUI;
import org.json.JSONException;
import org.json.JSONObject;

import com.founder.fix.ocx.BizobjEntity;
import com.founder.fix.ocx.ChildrenPropertySource;
import com.founder.fix.ocx.FormConst;
import com.founder.fix.ocx.FormConst.SelfBizObjInfo;
import com.founder.fix.ocx.OverrideWizard;
import com.founder.fix.ocx.SelBizServicejInfoDialog;
import com.founder.fix.ocx.SelectBizServiceDialog;
import com.founder.fix.ocx.SelectBizobjAndMethodWizard;
import com.founder.fix.ocx.SelectBizobjDialog;


public class WPECustomDialogCellEditor extends DialogCellEditor {

	private Composite parent = null;

	private HashMap<String, Object> _propertyMap = null;
	private JSONObject _modelJson = null;
	private HashMap<String, String> _translateMap;
	
	private HashMap<String,ArrayList<HashMap<String,Object>>> _parentPropertyMap;
	private String _parentNodeName;
	
	

	public String bizobjId;

	private Text text;

	private String showstate;
	
	private Text displayText;

	//WPE版
	public WPECustomDialogCellEditor(Composite parent, 
			HashMap<String, Object> propertyMap,JSONObject node_json,
			HashMap<String, String> translateMap,
			HashMap<String,ArrayList<HashMap<String,Object>>> parentPropertyMap,String parentNodeName
			) {
		super(parent);
		this.parent = parent;
		this._propertyMap = propertyMap;
		this._modelJson = node_json;
		this._translateMap = translateMap;
		_parentPropertyMap = parentPropertyMap;
		_parentNodeName = parentNodeName;
		

		// 根据配置设置‘只读’属性
		this.showstate = (String) _propertyMap.get("showstate");
		if (this.showstate != null && this.showstate.equals("readonly")) {
			text.setEditable(false);
		}
	}
	
	public WPECustomDialogCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	protected Object openDialogBox(Control cellEditorWindow) {
		Object value = this.getValue();
		Object showstate = this._propertyMap.get("showstate");
		if (showstate == null) {
			showstate = "normal";
		}

		Object jsontype = this._propertyMap.get("jsontype");

		Object category = this._propertyMap.get("category");
		String name = this._propertyMap.get("name").toString();
		// 事件处理
		if ("event".equals(category)) {
			String webRootPath = "WebRoot//js//custom.js";
			IPath path = new Path(webRootPath);
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IProject[] projects = workspace.getRoot().getProjects();
			for (IProject project : projects) {
				try {
					IJavaScriptProject jsProject = JavaScriptCore.create(project);
					if(project.getName().equals("test1")){//ywpx
						IJavaScriptElement element = jsProject.findElement(path);
						try {
							//打开不定位
//							JavaScriptUI.openInEditor(element);
							
							IEditorPart methodEditor = JavaScriptUI.openInEditor(element, false, false);
				            if (methodEditor instanceof ITextEditor) {
				                ITextEditor editor = (ITextEditor) methodEditor;
				                
				                IEditorInput input = new FileEditorInput(project.getFile(webRootPath));
				                
				                IDocument document = editor.getDocumentProvider().getDocument(input);
				                
				                //得到文本内容
				                String doucumentText = document.get();
				                
				                //reportedManager
				                String functionName = "reportedManager";
				                
				                //editor.selectAndReveal(134,15);
				                // 定位
				                editor.selectAndReveal(
				                		doucumentText.indexOf(functionName),functionName.length());
				                
				                
				                // 换行在最后追加方法
//				                doucumentText += "\r"  + "function aaaa(){}"; 
//				                document.set(doucumentText);
//				                
//				                editor.selectAndReveal(
//				                		doucumentText.indexOf("aaaa"),"aaaa".length());
				                
				                // 设置焦点
				                editor.setFocus();
				                
//				                //保存
//				                editor.doSave(null);
				            }

						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				} catch (JavaScriptModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
			
//			JavaScriptUI.openInEditor(element);
//			String path = "D://mySelfWorkSpaces//runtime-exe//ywpx//WebRoot//js//custom.js";

		}
		// 对弹出窗口有设置
		else if (this._propertyMap.containsKey("dialogfunc")) {
			Map<String, Object> propertyInfo;
			String bizobjValue = "";
			String fieldsValue = "";
			
			
			ArrayList<HashMap<String, Object>> arraylist = null;
			try{
				
				try {
					
					if(_translateMap.containsKey("BizObj")){
						bizobjValue = (String) _modelJson.get(_translateMap.get("BizObj"));
					}
					if(_translateMap.containsKey("Fields")){
						fieldsValue = (String) _modelJson.get(_translateMap.get("Fields"));
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				
				arraylist = _parentPropertyMap.get(_parentNodeName);
				
			}
			catch(java.lang.ClassCastException e){
				ChildrenPropertySource _ChildrenPropertySource = (ChildrenPropertySource) 
						this._propertyMap
						.get(FormConst.COMPONENTDIALOGPROPERTYKEY);
				propertyInfo = _ChildrenPropertySource.getPropertyInfo();
				bizobjValue = (String) propertyInfo.get("BizObj");
				fieldsValue = (String) propertyInfo.get("Fields");
				arraylist = _ChildrenPropertySource.getList();
			}
//			ComponentDialogProperty _ComponentDialogProperty = (ComponentDialogProperty) this.Map
//					.get(FormConst.COMPONENTDIALOGPROPERTYKEY);
			
//			String bizobjValue = (String) _ComponentDialogProperty
//					.getPropertyInfo().get("BizObj");
//			String fieldsValue = (String) _ComponentDialogProperty
//					.getPropertyInfo().get("Fields");
			
			if(fieldsValue==null
					&&(_propertyMap.get("name").equals("Parent")
					||_propertyMap.get("name").equals("Child")
					||_propertyMap.get("name").equals("ShowField")
					||_propertyMap.get("name").equals("AppendFields"))){
				fieldsValue = value.toString();
			}
			
			String serviceValue = "";
			try {
				if(_translateMap.containsKey("Service")){
					serviceValue = (String) _modelJson.get(_translateMap.get("Service"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String dialogfunc = this._propertyMap.get("dialogfunc").toString();

			bizobjId = bizobjValue;

			if (dialogfunc.startsWith("bizobj:")) {
				if(dialogfunc.equals("bizobj:method")){
					OverrideWizard dialog = new OverrideWizard(null, 
							new SelectBizobjAndMethodWizard());
					dialog.setPageSize(-1, 350); //-1代表宽度自适应, 300为高度
					if (dialog.open() == Dialog.OK) {
//						viewText.setText(dialog.getValue());
						return dialog.getBizobjAndMethodValue();
					}
				}
				// 需要打开业务对象选择窗口
				String types = dialogfunc.substring("bizobj:".length());
				if (types != null && types.equals("bizobj")) {
					SelectBizobjDialog dialog = new SelectBizobjDialog(
							this.parent.getShell(), bizobjValue, true);
					int rvl = dialog.open();
					if (rvl == InputDialog.OK) {
						BizobjEntity sboi = dialog.getValue();
						if (sboi != null) {
							bizobjId = sboi.getId();

							List<String> list = getBizObjPropertyList(arraylist);
							for (int i = 0; i < list.size(); i++) {
								String key = list.get(i);
								if (!key.equals(name))
									try {
										String key_cn = _translateMap.get(key);
										_modelJson.put(key_cn, "");
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
							return sboi.getId();
						}
					}
				} else {
					if (bizobjId != null && !bizobjId.equals("")) {
						// 选择方法的对话框先行
						if (types.equals("service")) {
							SelectBizServiceDialog dialog = new SelectBizServiceDialog(
									this.parent.getShell(), bizobjValue, serviceValue);
							if (Dialog.OK == dialog.open()) {
								serviceValue = dialog.getValue().getId();
							}
							return serviceValue;
						}

						// 验证‘方法’字段是否为空，如果不为空需要执行方法字段选择对话框
						if (serviceValue != null && !serviceValue.equals("")) {
							// 选择方法字段的对话框
							String[] selectedValue = new String[] {String.valueOf(this.getValue())};
							SelBizServicejInfoDialog sbid = new SelBizServicejInfoDialog(
									this.parent.getShell(), bizobjValue,
									serviceValue, selectedValue);
							int rvl = sbid.open();
							if (rvl == InputDialog.OK) {
								SelfBizObjInfo sboi = sbid.getValue();
								if (sboi != null) {
									return sboi.ColumnNames;
								}
							}
						} else {
							// 选择业务对象字段的对话框
							SelBizObjInfoDialog sbid = new SelBizObjInfoDialog(
									this.parent.getShell(), "", fieldsValue);
							int rvl = sbid.open();
							if (rvl == InputDialog.OK) {
								SelfBizObjInfo sboi = sbid.getValue();
								if (sboi != null) {
									if (types.equals("source"))
										return sboi.DataSource;
									else if (types.equals("bizobj"))
										return sboi.BizObjName;
									else if (types.equals("field"))
										return sboi.ColumnNames;
									else if (types.equals("service")) {
										return sboi.ColumnNames;
									}
								}
							}
						}
					}
					//JQAutoComplete
//					else if ((Map.get("fixcomponents_key").equals("jqautocomplete"))
//							&& ((Map.get("name").equals("value")) 
//									|| (Map.get("name").equals("label"))))
//					{
//						Object dataStoreObj = DHtmlPropertyMap.get("DataStore");
//						if(dataStoreObj!=null&&!dataStoreObj.equals(""))
//						{
//							String dataStore = dataStoreObj.toString();
//							dataStore = dataStore.replaceAll("\r","");
//							dataStore = dataStore.replaceAll("\n","");
//							Map <String,Object>dataStoreMap = JSONUtil.parseJSON2Map(dataStore);
//							String bizObj = dataStoreMap.get("BizObj").toString();
//							String fieldsSource = dataStoreMap.get("Fields").toString();
//							
//							String fieldsChoose = value.toString();
//							// 选择业务对象字段的对话框
//							SelBizChooseInfoDialog sbid = new SelBizChooseInfoDialog(
//									this.parent.getShell(), bizObj, fieldsSource,fieldsChoose);
//							int rvl = sbid.open();
//							if (rvl == InputDialog.OK) {
//								SelfBizObjInfo sboi = sbid.getValue();
//								if (sboi != null) {
//									if (types.equals("source"))
//										return sboi.DataSource;
//									else if (types.equals("bizobj"))
//										return sboi.BizObjName;
//									else if (types.equals("field"))
//										return sboi.ColumnNames;
//									else if (types.equals("service")) {
//										return sboi.ColumnNames;
//									}
//								}
//							}
//						}
//						else
//						{
//							MessageDialog.openWarning(Activator.getDefault()
//									.getWorkbench().getDisplay().getActiveShell(),
//									"提示", "请先配置数据");
//						}
//					}
				}
			}
		}
		// 组件引用
		else if (this._propertyMap.containsKey("element"))// (
		// refc.equals(FormConst.FIXBUTTONCOMPONENTREFC))
		{
			String element = this._propertyMap.get("element").toString();
			
			// #{func}:advance
			ElementInfo eleinf = WPEFormPropertyUtils.getElConfExp(element,
					_modelJson);

			if (eleinf.name == null || eleinf.name.equals("")) {
				if (element.startsWith("#{func}"))
					FixLoger.info("请先选择组件类型");
				else
					FixLoger.info("关键属性没有设定，无法继续");
				return null;
			}

			// category=normal
			// 是静态数据,需要进行分解，因为style出来的数据不是正规的JSON
			if ("normal".equals(category)) {
				// value = FormPropertyUtils.getNormalJsonString(value);
				// if ( "style".equals(eleinf.name) )
				// value = "{"+value+"}";
			}

			Object arrayobject_dialog_state = this._propertyMap
					.get("arrayobject_dialog_state");

			// [{key1:"",key2:""},{}]
			if (jsontype != null
					&& "arrayobject".equals(jsontype)
					&& (arrayobject_dialog_state == null || "ArrayDialog"
							.equals(arrayobject_dialog_state))) {
				// this.Map.remove("jsontype");
				// 设置标示，以确定是在列表还是在单条属性
				// ArrayDialog
				// PropertySheetDialog

				// 打开数组对象对话框
				ArrayDialog dialog = new ArrayDialog(parent.getShell(),
						value.toString(), this._propertyMap);
				//
				int rvl = dialog.open();

				// 重新赋值用以下次打开ArrayDialog
				this._propertyMap.put("arrayobject_dialog_state", "ArrayDialog");

				if (rvl == InputDialog.OK)
					return dialog.getValue();

			} else {
				// 下面是根据组件类型，取得对应的属性配置，只取advance部分
				WPEPropertySheetDialog id = new WPEPropertySheetDialog(
						this.parent.getShell(), eleinf, value.toString(),
						this._propertyMap,this._modelJson
//						,this._translateMap
						);
				int rvl = id.open();
				if (rvl == InputDialog.OK) {
					text.setText(id.getValue());
					return id.getValue();
				}
			}
		}
		// 选择颜色
		else if (showstate.toString().equals(DHtmlConst.FORM_Color)) {
			return FormPropertyUtils.showColorDialog(this.parent);
		}
		// 选择表达式
		else if (showstate.toString().equals(DHtmlConst.FORM_Expression)) {
//			String formAlias = mainEditor.getDhtml().Execute(
//					DHtmlConst.EXECUTE_GetControlAliasOrId,
//					DHtmlConst.FORM_Alias, "");
//			String formIds = mainEditor.getDhtml().Execute(
//					DHtmlConst.EXECUTE_GetControlAliasOrId,
//					DHtmlConst.FORM_ControlID, "");

			// ExpressionComponentNew expressionComponent = new
			// ExpressionComponentNew(value.toString(),0,formIds,formAlias,DHtmlConst.FORM_Formula);
			// //shell.setOpenedShell(expressionComponent.getShell());
			// //expressionComponent.setDomain(domain);
			// if ( expressionComponent.show() == SWT.OK )
			// return expressionComponent.getValue();
		}
		// 选择验证
//		else if (showstate.toString().equals(DHtmlConst.FORM_Verify)) {
//			// id,bizobj,
//			// formfilepath
//			String id = DHtmlPropertyMap.get("id").toString();
//			String htmlfile = mainEditor.getHtmlFileName();
//
//			FormVerifyCellDialog formVerifyCellDialog = new FormVerifyCellDialog(
//					this.parent.getShell(), id, htmlfile);
//			formVerifyCellDialog.setBlockOnOpen(true);
//
//			// 读
//			formVerifyCellDialog.ReadViw();
//			if (formVerifyCellDialog.getFormViewTo() == null) {
//				MessageDialog.openWarning(null, "警告", "该表单尚未被配置！");
//				return null;
//			}
//
//			if (InputDialog.OK == formVerifyCellDialog.open()) {
//				// 重新打开业务对象
////				FixUtil.closeAndOpenBizEditorFromBizobjId(formVerifyCellDialog
////						.getBizObjId());
//				return formVerifyCellDialog.ruleString;
//			}
//
//			return null;
//		}
		
		// 保存配置
//		else if (showstate.toString().equals(DHtmlConst.FORM_Action)) {
//			// id,bizobj,
//			// formfilepath
//			String fixcomponents_key = _tag_json.get("fixcomponents_key").toString();
//			String htmlfile = mainEditor.getHtmlFileName();
//
//			
//			ElementInfo ei = new ElementInfo();
//			ei.name = fixcomponents_key;// 选中控件类型
//			
////			//OCX版
////			ArrayList<HashMap<String, Object>> list = mainEditor
////					.getXmlPropBuffer().getPropertyList(ei);
//			for (int i = 0; i < list.size(); i++) {
//				if (list.get(i).get("category") == null) {
//					list.remove(i);
//					i--;
//				} else {
//					if (!list.get(i).get("category").toString()
//							.equals("advance")) {
//						list.remove(i);
//						i--;
//					}
//				}
//			}
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			for (int i = 0; i < list.size(); i++) {
//				map.put(list.get(i).get("name").toString(),
//						DHtmlPropertyMap.get((list.get(i).get("name"))));
//			}
//		
//			
//			FormSaveConfigCellDialog formSaveConfigCellDialog = new FormSaveConfigCellDialog(
//					this.parent.getShell(),fixcomponents_key,htmlfile,map);
//			formSaveConfigCellDialog.setBlockOnOpen(true);
//			
//			if (InputDialog.OK == formSaveConfigCellDialog.open()) {
//				// 重新打开业务对象
//				return "已保存";
//			}
//
//			return null;
//		}
		// 选择别名
//		else if (showstate.toString().equals(DHtmlConst.FORM_Alias)) {
//			String value2 = value.toString();
//			value2 = value2.replace("\"", "");
//			value2 = value2.replace("[", "");
//			value2 = value2.replace("]", "");
//			String selValue = mainEditor.getDhtml().Execute(
//					DHtmlConst.EXECUTE_ShowComponents, DHtmlConst.FORM_Alias,
//					value2);
//			if (!"null".equals(selValue)){
//				if(selValue==null){
//					return "";
//				}
//				return selValue;
//			}
//		}
		// 选择控件ID
//		else if (showstate.toString().equals(DHtmlConst.FORM_ControlID)) {
//			String value2 = value.toString();
//			value2 = value2.replace("\"", "");
//			value2 = value2.replace("[", "");
//			value2 = value2.replace("]", "");
////			//OCX版
////			String selValue = mainEditor.getDhtml().Execute(
////					DHtmlConst.EXECUTE_ShowComponents,
////					DHtmlConst.FORM_ControlID, value2);
//			if (!"null".equals(selValue))
//				return selValue;
//		} 
		else {

			if(name.equals("defaultValue")){
				String dialogToString = value.toString();
				DefaultValueDialog id = new DefaultValueDialog(this.parent.getShell(),
						dialogToString, _propertyMap);
				int rvl = id.open();
				if (rvl == InputDialog.OK){
					String dialogReturnString = id.getValue().trim();
					return dialogReturnString;
				}
			}
			else{
				String dialogToString = value.toString();
				if(name.equals("Filter")){
					dialogToString = dialogToString.replaceAll("  ","\r\n");
				}
				PropertyDialog id = new PropertyDialog(this.parent.getShell(),
						dialogToString, _propertyMap);
				int rvl = id.open();
				if (rvl == InputDialog.OK){
					String dialogReturnString = id.getValue().trim();
					if(name.equals("Filter")){
						dialogReturnString = dialogReturnString.replaceAll("\r\n", "  ");
					}
					return dialogReturnString;
				}
			}
		}
		return null;
	}

	protected Button createButton(Composite parent) {
		Button result = new Button(parent, SWT.PUSH);
		result.setSize(15, 15);
		result.setText(".."); //$NON-NLS-1$
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.DialogCellEditor#doSetFocus()
	 */
	@Override
	protected void doSetFocus() {
		if (text != null) {
			if(getValue()!=null){
				text.setText(getValue().toString());
			}
			text.selectAll();
			text.setFocus();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.DialogCellEditor#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite cell) {
		text = new Text(cell, SWT.NONE);

		text.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				//获取焦点用以提示信息
				String description = String.valueOf(_propertyMap.get("description"));
				if(displayText != null) {
					displayText.setText(description == null ? "" : description);
				}
			}

			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				doSetValue(text.getText());
				if (showstate != null && !showstate.equals("readonly")) {
					if(!_propertyMap.get("caption").toString().equals("常量")) {
						WPECustomDialogCellEditor.this.focusLost();
					}
				}
			}
		});

		text.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.keyCode == 13) {
					doSetValue(text.getText());
				}
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		return text;
	}

	@Override
	protected void focusLost() {
		// TODO Auto-generated method stub
		super.focusLost();
	}
	
	protected static String getURLPath(Class rsrcClass, String name) {
		return rsrcClass.getResource(name).getPath();
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	
	
	
	
	public ArrayList<String> getBizObjPropertyList(ArrayList<HashMap<String, Object>> arraylist) {
		ArrayList<String> list1 = new ArrayList<String>();
		for (int i = 0; i < arraylist.size(); i++) {
			HashMap<String, Object> map = arraylist.get(i);
			String name = map.get("name").toString();
			if (map.containsKey("dialogfunc")) {
				String dialogfunc = map.get("dialogfunc").toString();
				if (dialogfunc.startsWith("bizobj:"))
					list1.add(name);
			}
		}
		return list1;
	}

}