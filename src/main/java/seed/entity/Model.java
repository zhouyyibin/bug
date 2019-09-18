package seed.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Model {
    private Long id;

    private Long projectId;

    private String name;

    private String leadinger;

    private JSONObject leadingBean;

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLeadinger() {
        return leadinger;
    }

    public void setLeadinger(String leadinger) {
        this.leadinger = leadinger == null ? null : leadinger.trim();
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

    public JSONObject getLeadingBean() {
        if(leadinger==null){
            return leadingBean;
        }
        return JSONObject.parseObject(leadinger);
    }

    public void setLeadingBean(JSONObject leadingBean) {
        this.leadingBean = leadingBean;
    }
}