package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {
    Role get(Long id);

    Long  save(Role data);

    List<Role> find(@Param("page") int page, @Param("size") int size);

    int getCount();

    void delete(@Param(value = "id") Long id);

    void update(@Param(value = "id") Long id, @Param(value = "name") String name, @Param(value = "describe") String describe);

    void updatePermission(@Param(value = "id") Long id,@Param(value = "permission") String permission);

    Role getByName(@Param(value = "name") String name);

}