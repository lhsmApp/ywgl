package com.fh.entity;

public enum Nature {
	
	NATURE_ALLPEOPLE("allpeople","全民"),//全民
	NATURE_GROUP("group","集体"),//集体
	NATURE_PRIVATE("private","民营"),//民营
	NATURE_STOCK("stock","股份制"),//股份制
	NATURE_FOREIGN("foreign","外资"),//外资
	NATURE_JOINT("joint","合资"),//合资
	NATURE_SOLE("sole","独资"),//独资
	NATURE_CAUSE("cause","事业单位"),
	NATURE_OTHER("other","其它");//其它
	
	private String nameKey;

    private String nameValue;
    
    
    private Nature(String nameKey, String nameValue) {
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

}
