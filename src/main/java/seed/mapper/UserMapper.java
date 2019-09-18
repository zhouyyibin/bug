package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.User;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    User get(Long id);

    User getByAccount(@Param("account")String account);

    User getByAccountAndPassword(@Param("account") String account, @Param("password") String password);

    Long  save(User data);

    List<User> find(@Param("page") int page, @Param("size") int size, @Param("departmentId") Long departmentId,@Param("keyword") String keyword,@Param("status")int status);

    int getCount(@Param("departmentId") Long departmentId,@Param("keyword") String keyword,@Param("status")int status);

    void delete(@Param(value = "id") Long id);

    void update(User data);

    void updateLogin(@Param(value = "id") Long id, @Param("lastLoginTime") Date lastLoginTime);

    void updateRoleId(@Param(value = "id") Long id, @Param("roleId") String roleId);

    void updatePassword(@Param(value = "id") Long id, @Param("password") String password);

    void updateStatus(@Param(value = "id") Long id, @Param("status") int status);


}