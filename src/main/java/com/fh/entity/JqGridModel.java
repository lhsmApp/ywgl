package com.fh.entity;

import java.io.Serializable;

public  class JqGridModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Integer ID;
    private String CATEGORYNAME;
    private String PRODUCTNAME;
    private String COUNTRY;
    private Double PRICE;
    private Integer QUANTITY;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getCATEGORYNAME() {
		return CATEGORYNAME;
	}
	public void setCATEGORYNAME(String cATEGORYNAME) {
		CATEGORYNAME = cATEGORYNAME;
	}
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public String getCOUNTRY() {
		return COUNTRY;
	}
	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}
	public Double getPRICE() {
		return PRICE;
	}
	public void setPRICE(Double pRICE) {
		PRICE = pRICE;
	}
	public Integer getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(Integer qUANTITY) {
		QUANTITY = qUANTITY;
	}
    
}