package com.fh.util.enums;

public enum QuestionDifficulty {
	Simple("1","简单"),
	medium("2","中等"), 
	difficult("3","困难");
	
	private String nameKey;

    private String nameValue;
	
	private QuestionDifficulty(String nameKey, String nameValue) {
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
    	QuestionDifficulty[] enums = QuestionDifficulty.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
}
