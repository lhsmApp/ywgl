package com.fh.util.enums;


/**
 * 附件类型
* @ClassName: AttachmentType
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年10月14日
*
 */
public enum AttachmentType {

	/*public static final String RPOBLEM_INFO = "RPOBLEM_INFO";//问题提报
	public static final String RPOBLEM_ANSWER = "RPOBLEM_INFO";//问题回复
	public static final String RPOBLEM_CLOSE = "RPOBLEM_INFO";//问题关闭
	
	public static final String GRC_ZHXZ = "CHANGE_GRC_ZHXZ";//GRC账号新增 
	public static final String GRC_QXBG = "CHANGE_GRC_QXBG";//GRC权限变更
	public static final String ERP_ZHXZ = "CHANGE_ERP_ZHXZ";//ERP账号新增 
	public static final String ERP_ZHZX = "CHANGE_ERP_ZHZX";//ERP账号注销
	public static final String ERP_XTBG = "CHANGE_ERP_XTBG";//ERP系统变更
*/	
	PROBLEM_INFO("PROBLEM_INFO","问题提报"),
	PROBLEM_ANSWER("PROBLEM_ANSWER","问题回复"),
	PROBLEM_CLOSE("PROBLEM_CLOSE","问题关闭"),
	KNOWLEDGE("KNOWLEDGE","知识库"),
	
	GRC_ZHXZ("CHANGE_GRC_ZHXZ","GRC账号新增 "),
	GRC_QXBG("CHANGE_GRC_QXBG","GRC权限变更"),
	ERP_ZHXZ("CHANGE_ERP_ZHXZ","ERP账号新增 "),
	ERP_ZHZX("CHANGE_ERP_ZHZX","ERP账号注销"),
	ERP_XTBG("CHANGE_ERP_XTBG","ERP系统变更");
	
	private String nameKey;

    private String nameValue;
    
    
    private AttachmentType(String nameKey, String nameValue) {
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
    	AttachmentType[] enums = AttachmentType.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
}
