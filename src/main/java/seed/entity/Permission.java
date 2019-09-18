package seed.entity;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Permission {
    private Long id;

    private Long parentId=0L;

    private String name;

    private String action;

    private String actionEntity;

    private JSONArray actionEntitySet = new JSONArray();

    private Long creatorId;

    private String creatorName;

    private Date creatorTime = new Date();

    private Long modifierId;

    private String modifierName;

    private boolean defaultCheck;

    private Date modifierTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(String actionEntity) {
        this.actionEntity = actionEntity;
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

    public JSONArray getActionEntitySet() {
        if(actionEntity==null){
            return actionEntitySet;
        }
        return JSONArray.parseArray(actionEntity);
    }

    public void setActionEntitySet(JSONArray actionEntitySet) {
        this.actionEntitySet = actionEntitySet;
    }

    public boolean isDefaultCheck() {
        return defaultCheck;
    }

    public void setDefaultCheck(boolean defaultCheck) {
        this.defaultCheck = defaultCheck;
    }
}