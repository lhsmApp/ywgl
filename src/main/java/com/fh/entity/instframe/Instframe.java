package com.fh.entity.instframe;

import java.util.List;

/**
 * 组织机构 实体类
* @ClassName: Instframe
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
public class Instframe{ 
	
	private String INSTFRAME_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private Instframe instframe;
	private List<Instframe> subInstframe;
	private boolean hasInstframe = false;
	private String treeurl;
	
	private String INST_CODE;			//组织机构编码
	public String getFINST_CODE() {
		return INST_CODE;
	}
	public void setFINST_CODE(String INST_CODE) {
		this.INST_CODE = INST_CODE;
	}
	private String INST_NAME;			//组织机构名称
	public String getFINST_NAME() {
		return INST_NAME;
	}
	public void setFINST_NAME(String INST_NAME) {
		this.INST_NAME = INST_NAME;
	}
	private String INST_FATHER_CODE;			//组织机构父编码
	public String getFINST_FATHER_CODE() {
		return INST_FATHER_CODE;
	}
	public void setFINST_FATHER_CODE(String INST_FATHER_CODE) {
		this.INST_FATHER_CODE = INST_FATHER_CODE;
	}
	private String LEADER_NAME;			//姓名
	public String getFLEADER_NAME() {
		return LEADER_NAME;
	}
	public void setFLEADER_NAME(String LEADER_NAME) {
		this.LEADER_NAME = LEADER_NAME;
	}
	private String STAFF_JOB;			//职务
	public String getFSTAFF_JOB() {
		return STAFF_JOB;
	}
	public void setFSTAFF_JOB(String STAFF_JOB) {
		this.STAFF_JOB = STAFF_JOB;
	}
	private String MOBILE_TEL;			//电话
	public String getFMOBILE_TEL() {
		return MOBILE_TEL;
	}
	public void setFMOBILE_TEL(String MOBILE_TEL) {
		this.MOBILE_TEL = MOBILE_TEL;
	}
	private String FUNCTION;			//职能
	public String getFFUNCTION() {
		return FUNCTION;
	}
	public void setFFUNCTION(String FUNCTION) {
		this.FUNCTION = FUNCTION;
	}
	private String ADDRESS;			//地址
	public String getFADDRESS() {
		return ADDRESS;
	}
	public void setFADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}
	private String REMARK;			//备注
	public String getFREMARK() {
		return REMARK;
	}
	public void setFREMARK(String REMARK) {
		this.REMARK = REMARK;
	}

	public String getINSTFRAME_ID() {
		return INSTFRAME_ID;
	}
	public void setINSTFRAME_ID(String INSTFRAME_ID) {
		this.INSTFRAME_ID = INSTFRAME_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String PARENT_ID) {
		this.PARENT_ID = PARENT_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Instframe getInstframe() {
		return instframe;
	}
	public void setInstframe(Instframe instframe) {
		this.instframe = instframe;
	}
	public List<Instframe> getSubInstframe() {
		return subInstframe;
	}
	public void setSubInstframe(List<Instframe> subInstframe) {
		this.subInstframe = subInstframe;
	}
	public boolean isHasInstframe() {
		return hasInstframe;
	}
	public void setHasInstframe(boolean hasInstframe) {
		this.hasInstframe = hasInstframe;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
