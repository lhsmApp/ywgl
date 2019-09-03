package com.fh.entity.system;

import java.util.List;

/**
 * 组织机构
* @ClassName: Department
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
public class Department {

	private String NAME;			//名称
	private String NAME_EN;			//英文名称
	private String DEPARTMENT_CODE;			//编码
	private String PARENT_CODE;		//上级ID
	private String HEADMAN;			//负责人
	private String TEL;				//电话
	private String FUNCTIONS;		//部门职能
	private String BZ;				//备注
	private	String ADDRESS;			//地址
	private String DEPARTMENT_ID;	//主键
	private String target;
	private Department department;
	private List<Department> subDepartment;
	private boolean hasDepartment = false;
	private String treeurl;
	private String icon;
	private boolean checked;//是否选中
	private boolean open;//是否含有子节点
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	
	public String getDEPARTMENT_CODE() {
		return DEPARTMENT_CODE;
	}
	public void setDEPARTMENT_CODE(String dEPARTMENT_CODE) {
		DEPARTMENT_CODE = dEPARTMENT_CODE;
	}
	public String getPARENT_CODE() {
		return PARENT_CODE;
	}
	public void setPARENT_CODE(String pARENT_CODE) {
		PARENT_CODE = pARENT_CODE;
	}
	public String getHEADMAN() {
		return HEADMAN;
	}
	public void setHEADMAN(String hEADMAN) {
		HEADMAN = hEADMAN;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getFUNCTIONS() {
		return FUNCTIONS;
	}
	public void setFUNCTIONS(String fUNCTIONS) {
		FUNCTIONS = fUNCTIONS;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}
	public void setDEPARTMENT_ID(String dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public List<Department> getSubDepartment() {
		return subDepartment;
	}
	public void setSubDepartment(List<Department> subDepartment) {
		this.subDepartment = subDepartment;
	}
	public boolean isHasDepartment() {
		return hasDepartment;
	}
	public void setHasDepartment(boolean hasDepartment) {
		this.hasDepartment = hasDepartment;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	
}