package com.fh.util.enums;

//枚举 单据状态 0作废 1正常
public enum StruMappingBillState {
	StruNotInTable("0","表结构不存在此列"),//
	Normal("1","正常"),//
	TableNotInStru("2","未保存到配置表");
	
	private String nameKey;

    private String nameValue;
    
    
    private StruMappingBillState(String nameKey, String nameValue) {
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
    	StruMappingBillState[] enums = StruMappingBillState.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
	
}
