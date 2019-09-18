package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.Company;
import seed.entity.Department;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    Department get(Long id);

    Department getByName(String name);

     Department getByNameAndParentId(@Param("name") String name, @Param("parentId") Long parentId);

    Long  save(Department data);

    List<Department> find(@Param("page") int page, @Param("size") int size);

    int getCount();

    void delete(@Param(value = "id") Long id);

    void update(@Param(value = "id") Long id, @Param(value = "name") String name,@Param(value = "userId") Long userId, @Param(value = "parentId") Long parentId,@Param(value = "code") String code);


    void updateCode(@Param(value = "id") Long id, @Param(value = "code") String code);

    List<Department> findByParentId(@Param(value = "parentId") Long parentId);
}