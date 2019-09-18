package seed.entity;

import java.util.Date;

public class Company {
    private Long id;

    private String name;

    private String taxIdentificationNo;

    private String registeredAddress;

    private String officeAddress;

    private String tel;

    private String fax;

    private String zipCode;

    private String officialWebsite;

    private String intranet;

    private Long creatorId;

    private String creatorName;

    private Date creatorTime = new Date();

    private Long modifierId;

    private String modifierName;

    private Date modifierTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTaxIdentificationNo() {
        return taxIdentificationNo;
    }

    public void setTaxIdentificationNo(String taxIdentificationNo) {
        this.taxIdentificationNo = taxIdentificationNo == null ? null : taxIdentificationNo.trim();
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress == null ? null : registeredAddress.trim();
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress == null ? null : officeAddress.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite == null ? null : officialWebsite.trim();
    }

    public String getIntranet() {
        return intranet;
    }

    public void setIntranet(String intranet) {
        this.intranet = intranet == null ? null : intranet.trim();
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName == null ? null : modifierName.trim();
    }

    public Date getModifierTime() {
        return modifierTime;
    }

    public void setModifierTime(Date modifierTime) {
        this.modifierTime = modifierTime;
    }
}