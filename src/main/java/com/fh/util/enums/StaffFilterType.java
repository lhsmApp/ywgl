package com.fh.util.enums;

public enum StaffFilterType {
	StaffFilterType_CONTRACT("1","合同化"),
	StaffFilterType_MARKET("2","市场化"),
	StaffFilterType_SYS_LABOR("3","系统内劳务"),
	StaffFilterType_OPER_LABOR("4","运行人员"),
	StaffFilterType_LABOR("5","劳务派遣");
	
	private String nameKey;

    private String nameValue;
    
    
    private StaffFilterType(String nameKey, String nameValue) {
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
    	StaffFilterType[] enums = StaffFilterType.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
	
}
