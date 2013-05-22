package com.founder.fix.ocx.util;

import java.util.HashMap;
import java.util.Map;

import com.founder.fix.apputil.Const.QueryParamType;
import com.founder.fix.apputil.Const.SecurityLevel;
import com.founder.fix.apputil.Const.VerifyDefType;
import com.founder.fix.apputil.Const.VerifyMsgParamSource;
import com.founder.fix.apputil.Const.VerifyParameterSource;

public class BizObjContants {

	public static final String PARA_ID = "paraid";
	public static final String PARA_SOURCE = "parasource";
	public static final String PARA_VALUE = "paravalue";

	public static final String MSGPARA_SOURCE = "msgparasource";
	public static final String MSGPARA_VALUE = "msgparavalue";

	public static final String VALIDATE_TYPE = "validatetype";
	public static final String VALIDATE_RULE = "validaterule";
	public static final String VALIDATE_DESC = "validateruledesc";
	public static final String VALIDATE_BTN = "validaterulebtn";

	public static final String VERIFY_TYPE_DISNAME = "类型";
	public static final String VERIFY_ID_DISNAME = "Rules";
	public static final String VERIFY_DESC_DISNAME = "规则";
	public static final String VERIFY_CONF_DISNAME = "配置";

	public static final String MSGPARA_CHOOSE_DISNAME = "选择";

	public static final String[] VALIDATE_IDS = new String[] { VALIDATE_TYPE,
			VALIDATE_RULE, VALIDATE_DESC, VALIDATE_BTN };

	public static final String[] VALIDATE_PARAPROPERTIES = new String[] {
			PARA_ID, PARA_SOURCE, PARA_VALUE };

	public static final String[] VALIDATE_MSGPARAPROPERTIES = new String[] {
			MSGPARA_SOURCE, MSGPARA_VALUE };

	public static final String[] VERIYF_PROPERTY_DISNAMES = new String[] {
			VERIFY_TYPE_DISNAME, VERIFY_ID_DISNAME, VERIFY_DESC_DISNAME,
			VERIFY_CONF_DISNAME };

	public static final String COLUMN_ID_DISNAME = "编号";
	public static final String COLUMN_NAME_DISNAME = "名称";
	public static final String COLUMN_DATATYPE_DISNAME = "数据类型";
	public static final String COLUMN_LENGTH_DISNAME = "长度";
	public static final String COLUMN_ISPRIMARY_DISNAME = "主键";
	public static final String COLUMN_ISSENSITIVE_DISNAME = "敏感";
	public static final String COLUMN_CATEGORYVALUE_DISNAME = "分类";
	public static final String COLUMN_CODETABLE_DISNAME = "关联字典表";
	public static final String COLUMN_INSERT_DEFAULT_DISNAME = "新增默认值";
	public static final String COLUMN_UPDATE_DEFAULT_DISNAME = "修改默认值";

	public static final String COLUMN_ID_KEY = "id";
	public static final String COLUMN_NAME_KEY = "name";
	public static final String COLUMN_DATATYPE_KEY = "dataType";
	public static final String COLUMN_LENGTH_KEY = "length";
	public static final String COLUMN_ISPRIMARY_KEY = "isprimary";
	public static final String COLUMN_ISSENSITIVE_KEY = "issensitive";
	public static final String COLUMN_CATEGORYVALUE_KEY = "categoryvalue";
	public static final String COLUMN_CODETABLE_KEY = "codetable";
	public static final String COLUMN_INSERT_DEFAULT_KEY = "insertDefault";
	public static final String COLUMN_UPDATE_DEFAULT_KEY = "updateDefault";
	
	public static final String VERIFY_ID_KEY = "类型";
	public static final String VERIFY_RULES_KEY = "Rules";
	public static final String VERIFY_DESC_KEY = "规则";
	public static final String VERIFY_CONFIG_KEY = "配置";
	public static final String VERIFY_TARGET_KEY = "目标";
	
	public static final String[] VERIFY_KEY = { VERIFY_ID_KEY, VERIFY_RULES_KEY, VERIFY_DESC_KEY, VERIFY_CONFIG_KEY, VERIFY_TARGET_KEY};
	
	public static final String EVENT_ID_KEY = "事件编号";
	public static final String EVENT_NAME_KEY = "事件名称";
	public static final String EVENT_CONTEXT_KEY = "事件内容";
	
	public static final String[] EVENT_KEY = { EVENT_ID_KEY, EVENT_NAME_KEY, EVENT_CONTEXT_KEY};

	public static final String[] COLUMN_DATATYPE = { "string", "int", "date" };
	public static final String[] COLUMN_PROPERTIYE_IDS = new String[] {
			COLUMN_ID_KEY, COLUMN_NAME_KEY, COLUMN_DATATYPE_KEY,
			COLUMN_LENGTH_KEY,
			COLUMN_ISPRIMARY_KEY, COLUMN_ISSENSITIVE_KEY, COLUMN_CODETABLE_KEY,
			COLUMN_CATEGORYVALUE_KEY, COLUMN_INSERT_DEFAULT_KEY,
			COLUMN_UPDATE_DEFAULT_KEY };

	public static final String[] COLUMN_PROPERTIYE_NAMES = new String[] {
			COLUMN_ID_DISNAME, COLUMN_NAME_DISNAME, COLUMN_DATATYPE_DISNAME,
			COLUMN_LENGTH_DISNAME,
			COLUMN_ISPRIMARY_DISNAME, COLUMN_ISSENSITIVE_DISNAME,
			COLUMN_CODETABLE_DISNAME, COLUMN_CATEGORYVALUE_DISNAME,
			COLUMN_INSERT_DEFAULT_DISNAME, COLUMN_UPDATE_DEFAULT_DISNAME };

	public static final String DATAAUCTRL_ID_KEY = "id";
	public static final String DATAAUCTRL_NAME_KEY = "name";
	public static final String DATAAUCTRL_PARAMLIST_KEY = "paramlist";
	public static final String DATAAUCTRL_NEEDAUTHORIZE_KEY = "needauthorize";

	public static final String DATAAUCTRL_ID_DISP = "编号";
	public static final String DATAAUCTRL_NAME_DISP = "名称";
	public static final String DATAAUCTRL_PARAMLIST_DISP = "参数";
	public static final String DATAAUCTRL_NEEDAUTHORIZE_DISP = "是否启用";

	public static final String[] DATAAUCTRL_PROPERTIYE_IDS = new String[] {
			DATAAUCTRL_ID_KEY, DATAAUCTRL_NAME_KEY, DATAAUCTRL_PARAMLIST_KEY,
			DATAAUCTRL_NEEDAUTHORIZE_KEY };
	public static final String[] DATAAUCTRL_PROPERTIYE_NAMES = new String[] {
			DATAAUCTRL_ID_DISP, DATAAUCTRL_NAME_DISP,
			DATAAUCTRL_PARAMLIST_DISP, DATAAUCTRL_NEEDAUTHORIZE_DISP };

	public static final String DATARELATION_ID_KEY = "id";
	public static final String DATARELATION_NAME_KEY = "name";
	public static final String DATARELATION_ISJOINDELETE_KEY = "isjoindelete";
	public static final String DATARELATION_ISJOINUPDATE_KEY = "isjoinupdate";
	public static final String DATARELATION_RELATIONEXP_KEY = "relationexp";
	public static final String DATARELATION_RELTYPE_KEY = "relType";

	public static final String DATARELATION_ID_DISP = "编号";
	public static final String DATARELATION_NAME_DISP = "名称";
	public static final String DATARELATION_ISJOINDELETE_DISP = "关联查询";
	public static final String DATARELATION_ISJOINUPDATE_DISP = "关联更新";
	public static final String DATARELATION_RELATIONEXP_DISP = "表达式";
	public static final String DATARELATION_RELTYPE_DISP = "关联类型";

	public static final String[] DATARELATION_PROPERTIYE_IDS = new String[] {
			DATARELATION_ID_KEY, DATARELATION_NAME_KEY,
			DATARELATION_ISJOINDELETE_KEY, DATARELATION_ISJOINUPDATE_KEY,
			DATARELATION_RELATIONEXP_KEY, DATARELATION_RELTYPE_KEY };
	public static final String[] DATARELATION_PROPERTIYE_NAMES = new String[] {
			DATARELATION_ID_DISP, DATARELATION_NAME_DISP,
			DATARELATION_ISJOINDELETE_DISP, DATARELATION_ISJOINUPDATE_DISP,
			DATARELATION_RELATIONEXP_DISP, DATARELATION_RELTYPE_DISP };

	public static final String DATARELATION_RELATIONCOLUMN_MASTER_KEY = "master";
	public static final String DATARELATION_RELATIONCOLUMN_DETAIL_KEY = "detail";
	
	public static final String DATARELATION_RELATIONCOLUMN_MASTER_DISP = "主对象属性";
	public static final String DATARELATION_RELATIONCOLUMN_DETAIL_DISP = "明细对象属性";
	
	public static final String ARRAY_VALUE_KEY = "arrayValue";

	public static final String[] DATARELATION_RELATIONCOLUMN_IDS = new String[] {
			DATARELATION_RELATIONCOLUMN_MASTER_KEY,
			DATARELATION_RELATIONCOLUMN_DETAIL_KEY };
	
	public static final String[] DATARELATION_RELATIONCOLUMN_DISPS = new String[] {
			DATARELATION_RELATIONCOLUMN_MASTER_DISP,
			DATARELATION_RELATIONCOLUMN_DETAIL_DISP };
	
	public static final String[] ARRAY_VALUE_IDS = new String[] {
		ARRAY_VALUE_KEY };

	public static final String SERVICE_ID_KEY = "id";
	public static final String SERVICE_NAME_KEY = "name";
	public static final String SERVICE_NAME_SECURITYLEVEL = "securityLevel";
	public static final String SERVICE_NAME_IMPLEMENT = "implement";
	public static final String SERVICE_NAME_TRANSACTION = "transaction";
	public static final String SERVICE_NAME_INVOKETYPE = "invokeType";

	public static final String SERVICE_ID_DISP = "方法编号：";
	public static final String SERVICE_NAME_DISP = "方法名称：";
	public static final String SERVICE_NAME_SECURITYLEVEL_DISP = "安全级别：";
	public static final String SERVICE_NAME_IMPLEMENT_DISP = "方法实现：";
	public static final String SERVICE_NAME_TRANSACTION_DISP = " 方法事务：";
	public static final String SERVICE_NAME_INVOKETYPE_DISP = "方法类型：";

	public static final String[] SERVICE_PROPERTIYE_IDS = new String[] {
			SERVICE_ID_KEY, SERVICE_NAME_KEY, SERVICE_NAME_SECURITYLEVEL,
			SERVICE_NAME_IMPLEMENT, SERVICE_NAME_TRANSACTION,
			SERVICE_NAME_INVOKETYPE };
	public static final String[] SERVICE_PROPERTIYE_NAMES = new String[] {
			SERVICE_ID_DISP, SERVICE_NAME_DISP,
			SERVICE_NAME_SECURITYLEVEL_DISP, SERVICE_NAME_IMPLEMENT_DISP,
			SERVICE_NAME_TRANSACTION_DISP, SERVICE_NAME_INVOKETYPE_DISP };

	public static final String S_PARA_ID_KEY = "id";
	public static final String S_PARA_NAME_KEY = "name";
	public static final String S_PARA_TYPE_KEY = "type";
	public static final String S_PARA_DEFAULT_KEY = "default";

	public static final String S_PARA_ID_DISP = "编号";
	public static final String S_PARA_NAME_DISP = "名称";
	public static final String S_PARA_TYPE_DISP = "数据类型";
	public static final String S_PARA_DEFAULT_DISP = "缺省值";

	public static final String[] S_PARA_PROPERTIYE_IDS = new String[] {
			S_PARA_ID_KEY, S_PARA_NAME_KEY, S_PARA_TYPE_KEY, S_PARA_DEFAULT_KEY };
	public static final String[] S_PARA_PROPERTIYE_NAMES = new String[] {
			S_PARA_ID_DISP, S_PARA_NAME_DISP, S_PARA_TYPE_DISP,
			S_PARA_DEFAULT_DISP };

	public static final String S_OUT_ID_KEY = "id";
	public static final String S_OUT_NAME_KEY = "name";
	public static final String S_OUT_DATATYPE_KEY = "type";
	public static final String S_OUT_OUTTYPE_KEY = "outtype";

	public static final String S_OUT_ID_DISP = "编号";
	public static final String S_OUT_NAME_DISP = "名称";
	public static final String S_OUT_DATATYPE_DISP = "数据类型";
	public static final String S_OUT_OUTTYPE_DISP = "默认返回";

	public static final String[] S_OUT_PROPERTIYE_IDS = new String[] {
			S_OUT_ID_KEY, S_OUT_NAME_KEY, S_OUT_DATATYPE_KEY, S_OUT_OUTTYPE_KEY };
	public static final String[] S_OUT_PROPERTIYE_NAMES = new String[] {
			S_OUT_ID_DISP, S_OUT_NAME_DISP, S_OUT_DATATYPE_DISP,
			S_OUT_OUTTYPE_DISP };
	public static final Map<String, String> BIZOBJ_NAME_ID_MAP = new HashMap<String, String>();
	public static final Map<String, String> BIZOBJ_ID_NAME_MAP = new HashMap<String, String>();

	public static final String BIZ_TYPE_TABLE_DISP = "实体对象";
	public static final String BIZ_TYPE_VIEW_DISP = "视图";
	public static final String BIZ_TYPE_PROC_DISP = "存储过程";
	public static final String BIZ_TYPE_SERVICE_DISP = "非实体对象";

	public static final String BIZ_TYPE_TABLE_ID = "EntityTable";
	public static final String BIZ_TYPE_VIEW_ID = "BusinessView";
	public static final String BIZ_TYPE_PROC_ID = "BusinessProc";
	public static final String BIZ_TYPE_SERVICE_ID = "Invoke";

	public static final String[] BIZ_TYPE_DISPS = new String[] {
			BIZ_TYPE_TABLE_DISP, BIZ_TYPE_VIEW_DISP, BIZ_TYPE_PROC_DISP,
			BIZ_TYPE_SERVICE_DISP };
	public static final String[] BIZ_TYPE_IDS = new String[] {
			BIZ_TYPE_TABLE_ID, BIZ_TYPE_VIEW_ID, BIZ_TYPE_PROC_ID,
			BIZ_TYPE_SERVICE_ID };
	public static final String BIZ_FILE_SUFFIX = ".biz";
	public static final String BIZSERVICE_FILE_SUFFIX = ".sev";

	static {
		BIZOBJ_NAME_ID_MAP.put(BIZ_TYPE_TABLE_DISP, BIZ_TYPE_TABLE_ID);
		BIZOBJ_NAME_ID_MAP.put(BIZ_TYPE_VIEW_DISP, BIZ_TYPE_VIEW_ID);
		BIZOBJ_NAME_ID_MAP.put(BIZ_TYPE_PROC_DISP, BIZ_TYPE_PROC_ID);
		BIZOBJ_NAME_ID_MAP.put(BIZ_TYPE_SERVICE_DISP, BIZ_TYPE_SERVICE_ID);

	}
	static {
		BIZOBJ_ID_NAME_MAP.put(BIZ_TYPE_TABLE_ID, BIZ_TYPE_TABLE_DISP);
		BIZOBJ_ID_NAME_MAP.put(BIZ_TYPE_VIEW_ID, BIZ_TYPE_VIEW_DISP);
		BIZOBJ_ID_NAME_MAP.put(BIZ_TYPE_PROC_ID, BIZ_TYPE_PROC_DISP);
		BIZOBJ_ID_NAME_MAP.put(BIZ_TYPE_SERVICE_ID, BIZ_TYPE_SERVICE_DISP);

	}
	public static final String TABLE_ATTR_NAME = "属性";
	public static final String TABLE_ATTR_TYPE = "类型";
	
	public static final String TABLE_COM_ATTR_NAME = "名称";
	public static final String TABLE_COM_ATTR_TYPE = "数据源";

	public static final String[] TABLE_COLUMNS = new String[] {
			TABLE_ATTR_NAME, TABLE_ATTR_TYPE };
	
	
	public static final String DETAIL_TABLE_NAME = "表名";
    public static final String DETAIL_TABLE_TYPE = "样式";
    
    
    public static final String DETAIL_COM_TABLE_NAME = "表名";
    public static final String DETAIL_COM_TABLE_TYPE = "样式";
	
	public static final String[] TABLE_TYPES = new String[] {
		DETAIL_TABLE_NAME,DETAIL_TABLE_TYPE };
	
	public static final String[] TABLE_COM_COLUMNS = new String[] {
		TABLE_COM_ATTR_NAME, TABLE_COM_ATTR_TYPE };
	
	public static final String[] TABLE_TYPES_COLUMNS = new String[] {
		DETAIL_COM_TABLE_NAME,DETAIL_COM_TABLE_TYPE };

	public static final String[] SERVICE_INVOKE_TYPES = new String[] { "default",
			"insert", "delete", "update", "selectMore", "selectOne",
			"selectCount", "selectMath", "selectAll" };
	public static Map<String, Integer> SERVICE_INVOKE_INDEX_MAP = new HashMap<String, Integer>();
	static {
		SERVICE_INVOKE_INDEX_MAP.put("default", 0);
		SERVICE_INVOKE_INDEX_MAP.put("insert", 1);
		SERVICE_INVOKE_INDEX_MAP.put("delete", 2);
		SERVICE_INVOKE_INDEX_MAP.put("insert", 3);
		SERVICE_INVOKE_INDEX_MAP.put("update", 4);
		SERVICE_INVOKE_INDEX_MAP.put("selectMore", 5);
		SERVICE_INVOKE_INDEX_MAP.put("selectOne", 6);
		SERVICE_INVOKE_INDEX_MAP.put("selectCount", 7);
		SERVICE_INVOKE_INDEX_MAP.put("selectMath", 8);
		SERVICE_INVOKE_INDEX_MAP.put("selectAll", 9);
	}

	public static String[] DATA_TYPES = new String[] { "String",
			"boolean", "byte", "short", "BigDecimal", "int", "long", "float",
			"double", "Date", "Time", "TimeStamp", "byte[]", "Blob", "Clob", "AttachmentDB" };
	public static Map<String, Integer> DATA_TYPE_INDEX_MAP = new HashMap<String, Integer>();
	static {
		DATA_TYPE_INDEX_MAP.put("String", 0);
		DATA_TYPE_INDEX_MAP.put("boolean", 1);
		DATA_TYPE_INDEX_MAP.put("byte", 2);
		DATA_TYPE_INDEX_MAP.put("short", 3);
		DATA_TYPE_INDEX_MAP.put("BigDecimal", 4);
		DATA_TYPE_INDEX_MAP.put("int", 5);
		DATA_TYPE_INDEX_MAP.put("long", 6);
		DATA_TYPE_INDEX_MAP.put("float", 7);
		DATA_TYPE_INDEX_MAP.put("double", 8);
		DATA_TYPE_INDEX_MAP.put("Date", 9);
		DATA_TYPE_INDEX_MAP.put("Time", 10);
		DATA_TYPE_INDEX_MAP.put("TimeStamp", 11);
		DATA_TYPE_INDEX_MAP.put("byte[]", 12);
		DATA_TYPE_INDEX_MAP.put("Blob", 13);
		DATA_TYPE_INDEX_MAP.put("Clob", 14);
		DATA_TYPE_INDEX_MAP.put("AttachmentDB", 15);
	}

	public static Map<VerifyDefType, Integer> VERIFY_TYPE = new HashMap<VerifyDefType, Integer>();
	static {
		VERIFY_TYPE.put(VerifyDefType.js, 0);
		VERIFY_TYPE.put(VerifyDefType.service, 1);
		VERIFY_TYPE.put(VerifyDefType.common, 2);
	}

	public static Map<VerifyParameterSource, Integer> VERIFY_PARA_SOURCE = new HashMap<VerifyParameterSource, Integer>();
	static {
		VERIFY_PARA_SOURCE.put(VerifyParameterSource.controlId, 0);
		VERIFY_PARA_SOURCE.put(VerifyParameterSource.alias, 1);
		VERIFY_PARA_SOURCE.put(VerifyParameterSource.tableField, 2);
		VERIFY_PARA_SOURCE.put(VerifyParameterSource.context, 3);
		VERIFY_PARA_SOURCE.put(VerifyParameterSource.string, 4);
	}

	public static Map<VerifyMsgParamSource, Integer> VERIFY_MSG_PARA_SOURCE = new HashMap<VerifyMsgParamSource, Integer>();
	static {
		VERIFY_MSG_PARA_SOURCE.put(VerifyMsgParamSource.thisValue, 0);
		VERIFY_MSG_PARA_SOURCE.put(VerifyMsgParamSource.tableFieldDeclare, 1);
		VERIFY_MSG_PARA_SOURCE.put(VerifyMsgParamSource.string, 2);
	}

	public static String[] SecurityLevels = new String[] {
			SecurityLevel.none.name(), SecurityLevel.online.name(),
			SecurityLevel.permission.name() };
	public static final String[] SERVICE_TAB_NAMES = new String[] { "参数",
			"返回值", "校验" };

	public static final String[] RELATION_TYPE = new String[] {
		/**QueryParamType.parameter.name(),**/ QueryParamType.combination.name() };

	public static final String[] TYPE_COMBO = new String[] { "_FixAddBtn", 
			"_FixModifyBtn",  "_FixViewBtn", "_FixDeleteBtn", "_FixRefreshBtn",
			"_FixPopUpSearchBtn", "_FixExportBtn", "_FixEmptyBtn", "_FixPopUpBtn",
			"_FixSaveBtn", "_FixImportBtn","_FixAddDailogBtn", 
			"_FixModifyDailogBtn", "_FixViewDailogBtn", "_FixCustomDailogBtn",
			"_FixFlowStartNewFlowWithFormBtn", "_FixLogicDeleteBtn",
			"_FixCopyBtn", "_FixCopyDailogBtn"};

	public static final String BASEINTERFACE_PACKAGE = "com.founder.fix.webcore.BaseInterface";
	public static final String BASEINTERFACE_CLASS = "BaseInterface";

	public static final String BIZ_REL_FLOW_DEFINE = "definition";
	public static final String BIZ_REL_FLOW_KEY = "key";
	public static final String BIZ_REL_FLOW_NAME = "name";
	public static final String BIZ_REL_FLOW_ISENABLE = "enable";

	public static final String BIZ_REL_FLOW_DEFINE_DISP = "流程定义";
	public static final String BIZ_REL_FLOW_KEY_DISP = "关联键";
	public static final String BIZ_REL_FLOW_NAME_DISP = "显示名称";
	public static final String BIZ_REL_FLOW_ISENABLE_DISP = "是否启用";

	public static final String[] BIZ_REL_FLOW_IDS = new String[] {
			BIZ_REL_FLOW_DEFINE, BIZ_REL_FLOW_KEY, BIZ_REL_FLOW_NAME,
			BIZ_REL_FLOW_ISENABLE };
	public static final String[] BIZ_REL_FLOW_NAMES = new String[] {
			BIZ_REL_FLOW_DEFINE_DISP, BIZ_REL_FLOW_KEY_DISP,
			BIZ_REL_FLOW_NAME_DISP, BIZ_REL_FLOW_ISENABLE_DISP };

}
