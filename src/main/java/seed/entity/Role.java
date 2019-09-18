package seed.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.Date;

public class Role {

    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "describe")
    private String describe;

    @JSONField(name = "deleted") 
    private Integer deleted=1;

    private Long creatorId;

    private String creatorName;

    private Date creatorTime=new Date();

    private Long modifierId;

    private String modifierName;

    private Date modifierTime = new Date();

    private String permission;

    @JSONField(name = "permissions")
    private JSONArray permissions = new JSONArray();

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public JSONArray getPermissions() {
        if(permission==null){
            return permissions;
        }
        return JSONArray.parseArray(permission);

    }

    public void setPermissions(JSONArray permissions) {
        this.permissions = permissions;
    }
}