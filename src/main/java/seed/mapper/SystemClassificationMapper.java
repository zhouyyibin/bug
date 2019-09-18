package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.SystemClassification;

import java.util.List;

@Mapper
public interface SystemClassificationMapper {
    SystemClassification get(Long id);

    SystemClassification getByNameAndType(@Param("name")String name,@Param("type")String type);

    Long  save(SystemClassification data);

    void  updateContent(SystemClassification data);

    List<SystemClassification> findByType(@Param("type") String type);

    int getCountByType(@Param("type") String type);

    void delete(@Param(value = "id") Long id);

}