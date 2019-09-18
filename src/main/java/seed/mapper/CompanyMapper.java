package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.Company;
import seed.entity.Department;

import java.util.List;

@Mapper
public interface CompanyMapper {
    Company get(Long id);

    Company getByName(String name);

    Long  save(Company data);

    List<Company> find(@Param("page") int page, @Param("size") int size);

    int getCount();

    void delete(@Param(value = "id") Long id);

    void update(Company data);
}