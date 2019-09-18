package seed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台应用启动类.
 * Created by yibin on 2018/05/23.
 */
@SpringBootApplication(scanBasePackages = {"seed"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    final static public  int LOGIN_ERROR = 50101;//账号或者密码不正确

    final static public  int ACCOUNT_ALREADY_ERROR = 50102;//账号已经存在

    final static public int COMPANY_NAME_ALREADY_ERROR = 50103;//公司名字已存在

    final static public int DEPARTMENT_NAME_ALREADY_ERROR = 50104;//部门名字已存在

    final static public int ROLE_NAME_ALREADY_ERROR = 50105;//角色名字已存在

    final static public int SYSTEMCLASSIFICATION_NAME_ALREADY_ERROR = 50106;//系统类别名字已存在

    final static public int FILE_TYPE_ERROR = 50107;//文件类型不正确

    final static public int BUG_NAME_ERROR = 50108;//bug已存在

    final static public int ROLE_NAME_PROJECT_ERROR = 50109;//项目名字已存在

    final static public int PASSWORD_ERROR = 50110;//原密码不正确

    final static public int USER_STOP_ERROR = 50111;//用户已经被禁用

}
