package com.fh.entity.system;

import java.util.List;

/**
 * 数据字典
* @ClassName: Dictionaries
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
public class Dictionaries {

	private String DICT_CODE;//编码
	private String PARENT_CODE;//父编码
	private String NAME;			//名称
	private String NAME_EN;			//英文名称
	private String ORDER_BY;		//排序	
	private String BZ;				//备注
	private String TBSNAME;			//关联表
	private String DICTIONARIES_ID;	//主键
	private String target;
	private Dictionaries dict;
	private List<Dictionaries> subDict;
	private boolean hasDict = false;
	private String treeurl;

	public Dictionaries(){

	}
	public Dictionaries(String dICT_CODE, String nAME){
		DICT_CODE = dICT_CODE;
		NAME = nAME;
	}
	
	public String getDICT_CODE() {
		return DICT_CODE;
	}
	public void setDICT_CODE(String dICT_CODE) {
		DICT_CODE = dICT_CODE;
	}
	public String getPARENT_CODE() {
		return PARENT_CODE;
	}
	public void setPARENT_CODE(String pARENT_CODE) {
		PARENT_CODE = pARENT_CODE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getNAME_EN() {
		return NAME_EN;
	}
	public void setNAME_EN(String nAME_EN) {
		NAME_EN = nAME_EN;
	}
	
	public String getORDER_BY() {
		return ORDER_BY;
	}
	public void setORDER_BY(String oRDER_BY) {
		ORDER_BY = oRDER_BY;
	}
	
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public String getTBSNAME() {
		return TBSNAME;
	}
	public void setTBSNAME(String tBSNAME) {
		TBSNAME = tBSNAME;
	}
	public String getDICTIONARIES_ID() {
		return DICTIONARIES_ID;
	}
	public void setDICTIONARIES_ID(String dICTIONARIES_ID) {
		DICTIONARIES_ID = dICTIONARIES_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Dictionaries getDict() {
		return dict;
	}
	public void setDict(Dictionaries dict) {
		this.dict = dict;
	}
	public List<Dictionaries> getSubDict() {
		return subDict;
	}
	public void setSubDict(List<Dictionaries> subDict) {
		this.subDict = subDict;
	}
	public boolean isHasDict() {
		return hasDict;
	}
	public void setHasDict(boolean hasDict) {
		this.hasDict = hasDict;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
