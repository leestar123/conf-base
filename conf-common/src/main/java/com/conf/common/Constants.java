package com.conf.common;

public class Constants {
	
    public static String TELLER = "teller";
    
    public static String ORG = "org";
    
	public final static String SCAN_PACKAGE_NAME = "com.conf.template.rule";
	
	public final static String SYSTEM_ERROR_CODE = "9999";
	
	public final static long SCANNING_TIME_INTERVAL = 20L;
	
	/**
	 * 状态生效状态 0- 生效 1-失效
	 */
	public final static String EFFECT_STATUS_VALID = "0";
	
	/**
	 * 状态生效状态 0- 生效 1-失效
	 */
	public final static String EFFECT_STATUS_INVALID = "1";
	
	/**
	 * 最大序列号
	 */
	public final static Integer MAX_SEQUENCE = 9999;
	
	/**
	 * RULE基础地址
	 */
    public final static String RULE_URL_BASE = "http://" + ToolsUtil.localIP() + ":8081/urule/";
    
    /**
     * 规则类型  0-动作
     */
    public final static String RULE_TYPE_ACTION = "0";
    
    /**
     * 操作类型  1-增加 2-删除 3-修改 
     */
    public final static Integer OPERATE_TYPE_ADD = 1;
    
    public final static Integer OPERATE_TYPE_DEl = 2;
    
    public final static Integer OPERATE_TYPE_MOD = 3;
    
    /**
     * 操作模块  1-组件（conf_rule_info）
     *       2-节点(conf_node_info)
     *       3-阶段(conf_step_info)
     *       4-流程(conf_flow_info) 
     *       5-节点绑定规则(conf_node_template) 
     *       6-产品关联(conf_productstep)
     */
    public final static Integer OPERATE_MODULE_RULE = 1;
    
    public final static Integer OPERATE_MODULE_NODE = 2;
    
    public final static Integer OPERATE_MODULE_STEP = 3;
    
    public final static Integer OPERATE_MODULE_FLOW = 4;
    
    public final static Integer OPERATE_MODULE_BUND_NODERULE= 5;
    
    public final static Integer OPERATE_MODULE_BUND_PRODUCTSTEP = 6;
    
    /**
     * 执行状态 0-成功 1-失败
     */
    public final static Integer EXCUTE_STATUS_SUCCESS = 0;
    
    public final static Integer EXCUTE_STATUS_FAIL = 1;
    
    /**
     * 删除状态 0-未 1-删除
     */
    public final static Integer DELETE_STATUS_NO = 0;
    
    public final static Integer DELETE_STATUS_YES = 1;
    
    //阶段类型编码	 01：准入筛选 02：预筛选 03：信审评分
    public final static String STAGE_TYPE_01 = "01";
    
    public final static String STAGE_TYPE_02 = "02";
    
    public final static String STAGE_TYPE_03 = "03";
    
    //01-借款人 02-共同借款人 03-担保人
    public final static String CUST_TYPE_LOAN = "01";
    
    public final static String QUALITY_INFO_STR = "UserInfo";
    
    public final static String ARRAY_INFO_STR = "UserArrayInfo";
    
    //资质审查结果 0-成功 1-失败
    public final static String QUALITY_RESULT_SUCCESS = "0";
    
    public final static String QUALITY_RESULT_FAIL = "1";
    
    /**
     * 
	    01- 直销业务
	    02- 到期待处理业务
	    03- 提额业务
	    04- 营销启用业务
	    05- 预授信转授信业务
	    06- 贷后检查业务
	    07- 年检业务
	    08- 逾期分析业务
	    09- 流失回归业务
	    10- 信用卡申请业务
	    11- 贷款申请业务
	    12-交叉销售
	    13-客户营销
    **/
    public final static String BUSINESS_TYEP_DSELL = "01";
    
    public final static String BUSINESS_TYEP_LOAN = "14";
    
    public final static String BUSINESS_TYEP_CREDIT = "10";
    
    /**
     * 1 通用预筛选评分评级
     * 2 通用信审选评分评级
     * 3 消费贷信审评分评级
     * 4 信用卡预筛选评分评级
     * 5 信用卡信审评分评级
     */
    public final static String SCORE_LEVEL_COMMON_PRE = "1";
    
    public final static String SCORE_LEVEL_COMMON_TRIAL = "2";
    
    public final static String SCORE_LEVEL_LOAN_TRIAL = "3";
    
    public final static String SCORE_LEVEL_CREDIT_PRE = "4";
    
    public final static String SCORE_LEVEL_CREDIT_TRIAL = "5";
}
