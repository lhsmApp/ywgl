package com.fh.util.enums;

/**
 * 录入日期控制业务类型枚举
 * @author xinyuLo
 * @version 1.0
 */
public enum SysDeptTime {
	GRC_PERSON("001","GRC人员信息"),
	GRC_APPROVAL_MATRIX("002","GRC审批矩阵"),			
	ERP_OAA("003","ERP正式账号申请"),						
	ERP_TAA("004","ERP临时账号申请"), 						
	ERP_DAA("005","ERP删除账号申请"),						
	ERP_USER_LIST("006","ERP用户清单"),				
	OPERATION_STATISTICS("007","运维工作统计"), 			
	PERMISSION_CHANGE_STATISTICS("008","权限变更统计"); 	
	
	private String nameKey;

    private String nameValue;
	
	private SysDeptTime(String nameKey, String nameValue) {
		this.nameKey = nameKey;
		this.nameValue = nameValue;
	}

	public String getNameKey() {
		return nameKey;
	}

	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}
	
	/** 
         * 根据key获取value 
     *  
     * @param key 
     *            : 键值key 
     * @return String 
     */  
    public static String getValueByKey(String key) {  
    	SysDeptTime[] enums = SysDeptTime.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
}	
