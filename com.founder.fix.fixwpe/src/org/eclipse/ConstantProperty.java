package org.eclipse;

import org.json.JSONObject;

/**
 * @author Fifteenth
 */

public class ConstantProperty {
	
	public final static String propetyTypeAttribute = "attribute";
	public final static String propetyTypeEven = "even";
	
	
	public static String attributeJsonTag = "attributeJsonTag";
	public static String attributeJsonModel = "attributeJsonModel";
	final public static String[] attributeJson = {attributeJsonTag,attributeJsonModel};
	
	public static JSONObject evenJsonModel;
	final public static JSONObject[] evenJson = {evenJsonModel};
	
	
	public static String attributeCategroyTag = "Tag";
	public static String attributeCategroyModel = "Model";
	public static String[] attributeCategroy = {attributeCategroyTag,attributeCategroyModel};
	
	
	public static String propertyComment = "#comment";
	
	
	public static String attributeCategroyModelNameAttribute = "@FixAttributies:";
	
	public static String attributeCategroyModelName[] = {
		attributeCategroyModelNameAttribute};
	
	
	
	public static String evenCategroyModel = "Model";
	public static String[] evenCategroy = {evenCategroyModel};
	
	
	public static String evenCategroyModelName[] = {"@FixEvens:"};
	
	public static String evenCategroyModelNameEven = "@FixEvens:";
	
	final public static String childJsonAttributeTag = "attributeTag";
	final public static String childJsonAttributeModel = "attributeModel";
	final public static String []childJsonAttribute = {childJsonAttributeTag,childJsonAttributeModel};
	
	final public static String childJsonEvenModel = "evenModel";
	final public static String [] childJsonEven = {childJsonEvenModel};
	
}
