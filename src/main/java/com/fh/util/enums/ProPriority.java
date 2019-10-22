package com.fh.util.enums;

//枚举问题优先级
public enum ProPriority {
	General("1","一般"),//
	Emergency("2","紧急");
	
	private String nameKey;

    private String nameValue;
    
    
    private ProPriority(String nameKey, String nameValue) {
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
    	ProPriority[] enums = ProPriority.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
	
}
