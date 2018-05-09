package cn.smbms.pojo;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ��Ӧ�̱�
 * @author Dow
 *
 */
public class Provider {
	//����id
	private Integer id;
	//��Ӧ�̱��
	@NotEmpty(message="供应商编码不能为空")
	private String proCode;
	//��Ӧ�����
	@NotEmpty(message="供应商名称不能为空")
	private String proName;
	//��ϸ����
	private String proDesc;
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProDesc() {
		return proDesc;
	}
	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}
	public String getProContact() {
		return proContact;
	}
	public void setProContact(String proContact) {
		this.proContact = proContact;
	}
	public String getProPhone() {
		return proPhone;
	}
	public void setProPhone(String proPhone) {
		this.proPhone = proPhone;
	}
	public String getProAddress() {
		return proAddress;
	}
	public void setProAddress(String proAddress) {
		this.proAddress = proAddress;
	}
	public String getProFax() {
		return proFax;
	}
	public void setProFax(String proFax) {
		this.proFax = proFax;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	//��ϵ��
	@NotEmpty(message="联系人不能为空")
	private String proContact;
	//�绰
	@Length(min=11,max=11,message="正确手机格式为11位")
	private String proPhone;
	//��ַ
	private String proAddress;
	//����
	private String proFax;
	//������
	private Integer createdBy;
	//����ʱ��
	private Date creationDate;
	//������
	private Date modifyDate;
	//����ʱ��
	private Integer modifyBy;
	//������Ϣ
	//企业营业执照
	private String companyLicPicPath;	
	//组织结构代码证照片
	private String orgCodePicPath;
	public String getCompanyLicPicPath() {
		return companyLicPicPath;
	}
	public void setCompanyLicPicPath(String companyLicPicPath) {
		this.companyLicPicPath = companyLicPicPath;
	}
	public String getOrgCodePicPath() {
		return orgCodePicPath;
	}
	public void setOrgCodePicPath(String orgCodePicPath) {
		this.orgCodePicPath = orgCodePicPath;
	}	
}
