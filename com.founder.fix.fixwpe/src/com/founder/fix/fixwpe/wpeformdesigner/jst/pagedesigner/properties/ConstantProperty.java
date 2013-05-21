package com.founder.fix.fixwpe.wpeformdesigner.jst.pagedesigner.properties;

import org.json.JSONObject;

/**
 * @author Fifteenth
 */

public class ConstantProperty {
	
	
	
	public final static String fixAdvance_cn = "   高级   ";
	public final static String fixNormal_cn  = "   常规   ";
	public final static String fixServer_cn  = "   后台   ";
	public final static String fixEvent_cn   = "   事件   ";
	public final static String fixVerify_cn  = "   验证   ";
	public final static String fixAction_cn  = "   操作   ";
	
	
	public final static String fixAdvance_en = "advance";
	public final static String fixNormal_en = "normal";
	public final static String fixServer_en = "server";
	public final static String fixEvent_en = "event";
	public final static String fixVerify_en = "verify";
	public final static String fixAction_en = "action";
	
//	public final static String propetyTypeAttribute = "attribute";
//	public final static String propetyTypeEven = "even";
	
	
	public static String attributeJsonTag = "attributeJsonTag";
	public static String attributeJsonModel = "attributeJsonModel";
	final public static String[] attributeJson = {attributeJsonTag,attributeJsonModel};
	
	public static int attributeJsonTagIndex = 0;
	public static int attributeJsonModelIndex = 1;
	
	public static JSONObject evenJsonModel;
	final public static JSONObject[] evenJson = {evenJsonModel};
	
	
	public static String attributeCategroyTag = "Tag";
	public static String attributeCategroyModel = "Model";
	public static String[] attributeCategroy = {attributeCategroyTag,attributeCategroyModel};
	
	
	public static String propertyComment = "#comment";
	
	
	public static String fixAttributeNameSpace = "@FixAttributies:";
	
//	public static String attributeCategroyModelName[] = {
//		attributeCategroyModelNameAttribute};
	
	
	
//	public static String evenCategroyModel = "Model";
//	public static String[] evenCategroy = {evenCategroyModel};
	
	
//	public static String evenCategroyModelName[] = {"@FixEvens:"};
//	
//	public static String evenCategroyModelNameEven = "@FixEvens:";
	
	final public static String childJsonAttributeTag = "attributeTag";
	final public static String childJsonAttributeModel = "attributeModel";
	final public static String []childJsonAttribute = {childJsonAttributeTag,childJsonAttributeModel};
	
	final public static String childJsonEvenModel = "evenModel";
	final public static String [] childJsonEven = {childJsonEvenModel};
	
}
