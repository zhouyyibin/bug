package seed.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Bug {

    final static public int STATUS_CREATE = 0;//未确认，激活，新建

    final static public int STATUS_CONFIRM = 1;//确认

    final static public int STATUS_DOING = 2;//处理中

    final static public int STATUS_FINISH= 3;//已解决

    final static public int STATUS_PASS= 4;//通过

    final static public int STATUS_TIMEOUT= 5;//超时

    final static public int STATUS_EDIT = 6;//编辑

    private Long id;

    private int code;

    private int oldCode;

    private String project;

    private String model;

    private String edition;

    private String type;

    private String hardwarePlatform;

    private String operatingSystem;

    private String browser;

    private String title;

    private String describe;

    private String severity;

    private String priority;

    private Long leadingId;

    private String sendMails;

    private List<User> sendMailUsers;

    private User leading;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date startTime ;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date settlingTime ;

    private String allCycles;

    private Integer status;

    private String annex;

    private List<String> annexs = new ArrayList<>();

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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition == null ? null : edition.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getHardwarePlatform() {
        return hardwarePlatform;
    }

    public void setHardwarePlatform(String hardwarePlatform) {
        this.hardwarePlatform = hardwarePlatform == null ? null : hardwarePlatform.trim();
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem == null ? null : operatingSystem.trim();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity == null ? null : severity.trim();
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority == null ? null : priority.trim();
    }

    public Long getLeadingId() {
        return leadingId;
    }

    public void setLeadingId(Long leadingId) {
        this.leadingId = leadingId;
    }

    public String getSendMails() {
        return sendMails;
    }

    public void setSendMails(String sendMails) {
        this.sendMails = sendMails == null ? null : sendMails.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getSettlingTime() {
        return settlingTime;
    }

    public void setSettlingTime(Date settlingTime) {
        this.settlingTime = settlingTime;
    }

    public String getAllCycles() {
        return allCycles;
    }

    public void setAllCycles(String allCycles) {
        this.allCycles = allCycles == null ? null : allCycles.trim();
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

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public List<User> getSendMailUsers() {
        return sendMailUsers;
    }

    public void setSendMailUsers(List<User> sendMailUsers) {
        this.sendMailUsers = sendMailUsers;
    }

    public User getLeading() {
        return leading;
    }

    public void setLeading(User leading) {
        this.leading = leading;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getAnnexs() {
        if(annex==null){
            return annexs;
        }
        return Arrays.asList(annex.split(","));
    }

    public void setAnnexs(List<String> annexs) {
        this.annexs = annexs;
    }

    public int getOldCode() {
        return oldCode;
    }

    public void setOldCode(int oldCode) {
        this.oldCode = oldCode;
    }
}