package com.fh.util.enums;

public enum BillType {
	SALLARY_DETAIL("1","工资明细"),//
	SALLARY_SUMMARY("2","工资汇总"),
	GOLD_DETAIL("3","公积金明细"),
	GOLD_SUMMARY("4","公积金汇总"),
	SECURITY_DETAIL("5","社保明细"),
	SECURITY_SUMMARY("6","社保汇总"),
	SALLARY_LISTEN("7","工资接口"),
	GOLD_LISTEN("8","公积金接口"),
	SECURITY_LISTEN("9","社保接口");
	
	private String nameKey;

    private String nameValue;
    
    
    private BillType(String nameKey, String nameValue) {
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
    	BillType[] enums = BillType.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
	
}
