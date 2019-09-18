package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.Permission;

import java.util.List;

@Mapper
public interface PermissionMapper {
    Permission get(Long id);

    Permission getByName(String name);

    Long  save(Permission data);

    List<Permission> find();

    int getCount();

    void delete(@Param(value = "id") Long id);

    void update(Permission permission);
}