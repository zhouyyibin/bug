package seed.entity;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Project {
    private Long id;

    private String name;

    private String senders;

    private JSONArray senderBeans = new JSONArray();

    private String models;

    private JSONArray modelBeans = new JSONArray();

    private String versions;

    private List<String> versionBeans = new ArrayList<>();


    private Integer status;

    private Long creatorId;

    private String creatorName;

    private Date creatorTime;

    private Long modifierId;

    private String modifierName;

    private Date modifierTime;

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

    public String getSenders() {
        return senders;
    }

    public void setSenders(String senders) {
        this.senders = senders == null ? null : senders.trim();
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions == null ? null : versions.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<String> getVersionBeans() {
        if(versions==null){
            return versionBeans;
        }
        return Arrays.asList(versions.split(","));
    }

    public void setVersionBeans(List<String> versionBeans) {
        this.versionBeans = versionBeans;
    }

    public JSONArray getSenderBeans() {
        if(senders==null){
            return senderBeans;
        }
        return JSONArray.parseArray(senders);
    }

    public void setSenderBeans(JSONArray senderBeans) {
        this.senderBeans = senderBeans;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public JSONArray getModelBeans() {
        if(models==null){
            return modelBeans;
        }
        return JSONArray.parseArray(models);
    }

    public void setModelBeans(JSONArray modelBeans) {
        this.modelBeans = modelBeans;
    }
}