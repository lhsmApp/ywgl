package com.fh.util.enums;

public enum TmplTypeTransferss {

	TB_STAFF_TRANSFER_CONTRACT("16","合同化工资传输表"),
	TB_STAFF_TRANSFER_MARKET("17","市场化工资传输表"),
	TB_STAFF_TRANSFER_SYS_LABOR("18","系统内劳务工资传输表"),
	TB_STAFF_TRANSFER_OPER_LABOR("19","运行人员工资传输表"),
	TB_STAFF_TRANSFER_LABOR("20","劳务派遣工资传输表"),
	
	TB_SOCIAL_INC_TRANSFER("24","社保传输表"),
	TB_HOUSE_FUND_TRANSFER("28","公积金传输表"),
	
	
	TB_GHJYJF_TRANSFER("PZ01","工会教育经费传输表"),
	TB_DF_TRANSFER("PZ02","党费传输表"),
	TB_SB_TRANSFER("PZ03","特殊社保传输表"),
	TB_GJJ_TRANSFER("PZ04","特殊公积金传输表"),
	TB_GJ_TRANSFER("PZ05","个缴传输表"),
	TB_YFLWF_TRANSFER("PZ06","应付劳务费传输表"),
	TB_QYNJTQ_TRANSFER("PZ07","企业年金提取传输表"),
	TB_BCYLTQ_TRANSFER("PZ08","补充医疗提取传输表"),
	TB_QYNJFF_TRANSFER("PZ09","企业年金发放传输表"),
	TB_PGTZ_TRANSFER("PZ10","评估调整传输表"),
	TB_SGRQ_TRANSFER("PZ11","深港天然气传输表"),
	TB_SGFY_TRANSFER("PZ12","深港社保劳务及管理费传输表"),
	TB_SGDX_TRANSFER("PZ13","深港社保费用及抵消往来传输表");
	
	private String nameKey;

    private String nameValue;
    
    
    private TmplTypeTransferss(String nameKey, String nameValue) {
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
    	TmplTypeTransferss[] enums = TmplTypeTransferss.values();  
        for (int i = 0; i < enums.length; i++) {  
            if (enums[i].getNameKey().equals(key)) {  
                return enums[i].getNameValue();  
            }  
        }  
        return "";  
    }  
	
}
