package com.founder.fix.ocx.util;

/**
 * DHTML常量
 * @author BB
 *
 */
public class DHtmlConst 
{
	/*常量*/
	public final static String META_FormVersion = "FixFormVersion";
	public final static String META_FormCreateTime = "FixFormCreateTime";
	public final static String META_BizObjName = "FixBizObjName";
	
	/*表单属性关键常量*/
	public final static String FORM_BizObjId = "bizObj";
	public final static String FORM_FieldId = "field";
	public final static String FORM_BizObjName = "bizObjname";
	public final static String FORM_FieldName = "fieldname";
	public final static String FORM_Alias = "alias";
	public final static String FORM_Color = "color";
	public final static String FORM_Expression = "expression";
	public final static String FORM_ControlID = "controlid";
	public final static String FORM_Verify = "verifydialog";
	
	public final static String FORM_Action = "saveConfigdialog";
	
	/*表单应用长量*/
	public final static String FORM_Formula = "sum,count,Math.avg,Math.max,Math.min,Math.abs,Math.acos,Math.asin,Math.atan,Math.ceil,Math.cos,Math.exp,Math.floor,Math.log,Math.sin,Math.sqrt,Math.tan";
	
	/*事件类*/
	public final static int EVENT_OnActivate = 1;
	public final static int EVENT_OnClick = 2;
	public final static int EVENT_OnCreate = 3;
	public final static int EVENT_OnDblClick = 5;
	public final static int EVENT_OnDestroy = 6;
	public final static int EVENT_OnDeactivate = 7;
	public final static int EVENT_OnKeyPress = 11;
	public final static int EVENT_OnPaint = 16;
	public final static int EVENT_OnDHTMLDocumentComplete = 4;
	public final static int EVENT_OnGetComponentConfig = 8;
	public final static int EVENT_OnDcoumentChange = 9;
	public final static int EVENT_OnMessages = 10;
	
	/*函数类*/
	public final static String METHOD_GetElementProperty = "GetElementProperty";
	public final static String METHOD_LoadHtml = "LoadHtml";
	public final static String METHOD_GetProperty = "GetProperty";
	public final static String METHOD_SetProperty = "SetProperty";
	public final static String METHOD_SetPropertyConfig = "SetPropertyConfig";
	public final static String METHOD_ChangOneProperty = "ChangOneProperty";
	public final static String METHOD_Execute = "Execute";
	
	/*属性类*/
	public final static String PROPERTY_DocumentHTML="DocumentHTML";
	public final static String PROPERTY_ISDIRTY="IsDirty";
	public final static String PROPERTY_OldHtml="OldHtml";
	public final static String COMPONENT_TYPE = "FixCOM";
	public final static String PROPERTY_FormTemp = "FormTemp";
	public final static String PROPERTY_WebComponentsPath = "WebComponentsPath";
	

	/*Execute类的函数名*/
	public final static String EXECUTE_SetMeta="SetMeta";
	public final static String EXECUTE_GetMeta="GetMeta";
	public final static String EXECUTE_GetMetaList="GetMetaList";
	public final static String EXECUTE_SetMetaList="SetMetaList";
	public final static String EXECUTE_GetReferenceScripts="GetReferenceScripts";
	public final static String EXECUTE_SetReferenceScripts="SetReferenceScripts";
	public final static String EXECUTE_GetReferenceCsss="GetReferenceCsss";
	public final static String EXECUTE_SetReferenceCsss="SetReferenceCsss";
	public static final String EXECUTE_GetCssBlockRuntime = "GetCssBlockRuntime";
	public static final String EXECUTE_GetCssBlockDesign = "GetCssBlockDesign";
	public static final String EXECUTE_GetPageCustomScriptBlock = "GetPageCustomScriptBlock";
	public static final String EXECUTE_GetPageConfigScriptBlock4DataTargets = "GetPageConfigScriptBlock4DataTargets";
	public static final String EXECUTE_GetComponentScriptBlocks = "GetComponentScriptBlocks";
	public static final String EXECUTE_GetComponentScriptBlockContent = "GetComponentScriptBlockContent";
	public static final String EXECUTE_SetComponentProperty4dblclick = "SetComponentProperty4dblclick";
	public static final String EXECUTE_SetBizObjInfo = "SetBizObjInfo";
	public static final String EXECUTE_SetComponentsInfo = "SetComponentsInfo";
	public static final String EXECUTE_toDestroy = "toDestroy";
	public static final String EXECUTE_GetMessages = "GetMessages";
	public static final String EXECUTE_GetEvent = "GetEvent";
	public static final String EXECUTE_GetCssNames = "GetCssNames";
	public static final String EXECUTE_ShowComponents = "ShowComponents";
	public static final String EXECUTE_GetControlAliasOrId = "GetControlAliasOrId";
	public static final String EXECUTE_GetComponentEventList = "GetComponentEventList";
	public static final String EXECUTE_CheckBorders = "CheckBorders";
	public static final String EXECUTE_GetComponentIdsInfo = "GetComponentIdsInfo";
	public static final String EXECUTE_ClearComponentIdHistory = "ClearComponentIdHistory";
	public static final String EXECUTE_DontCan2KeyDown = "DontCan2KeyDown";

	
}
