package com.fh.util.enums;

public enum FundsConfirmInfoState {
	NotOper("0","未操作"),//
	Confirm("1","已确认"),//
	Summy("2","已汇总"),
	Transfer("3","已传输");

	private String nameKey;

    private String nameValue;
    
    
    private FundsConfirmInfoState(String nameKey, String nameValue) {
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
    	FundsConfirmInfoState[] enums = FundsConfirmInfoState.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
	
}
