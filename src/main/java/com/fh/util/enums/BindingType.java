package com.fh.util.enums;

//枚举  AddCostFactSheetItem 绑定类型
public enum BindingType {
	Total0("0",""),//
	Total44("44",""),//
	DetailSCHHTH("1",""),//
	DetailXTNLW("2",""),//
	DetailLWPQ("3","");
	
	private String nameKey;

    private String nameValue;
    
    
    private BindingType(String nameKey, String nameValue) {
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
    	BindingType[] enums = BindingType.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
	
}
