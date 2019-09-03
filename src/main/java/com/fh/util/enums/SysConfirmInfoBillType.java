package com.fh.util.enums;

public enum SysConfirmInfoBillType {
	//确认表单据类型（1合同化工资传输2市场化工资传输3运行人员工资传输4劳务用工传输5劳务人员在建传输6社保传输7公积金传输）
	STAFF_CONTRACT("1","合同化工资传输"),//
	STAFF_MARKET("2","市场化工资传输"),
	STAFF_SYS_LABOR("3","运行人员工资传输"),
	STAFF_OPER_LABOR("4","劳务用工传输"),
	STAFF_LABOR("5","劳务人员在建传输"),
	SOCIAL_INC("6","社保传输"),
	HOUSE_FUND("7","公积金传输");

	private String nameKey;

    private String nameValue;
    
    
    private SysConfirmInfoBillType(String nameKey, String nameValue) {
    	this.nameKey = nameKey;
        this.setNameValue(nameValue);
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
    	SysConfirmInfoBillType[] enums = SysConfirmInfoBillType.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
	
}
