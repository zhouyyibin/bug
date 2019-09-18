package seed.tools.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import seed.entity.SystemClassification;
import seed.service.SystemClassificationService;

public class EmailConfig {
    String host;//smtp服务器
    String username;//smtp账号
    String password;//stmp密码
    String port;//smtp端口号
    String name = "艾鑫Bug系统";//账号名称
    int isOpen =1;//是否打开，0=关闭，1=打开

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
       this.name =name;
    }

    public int getIsOpen() {
       return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

}
