package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.Project;

import java.util.List;

@Mapper
public interface ProjectMapper {
    Project get(Long id);

    Project getByName(String name);

    Long  save(Project data);

    List<Project> find(@Param("page") int page, @Param("size") int size);

    int getCount();

    void delete(@Param(value = "id") Long id);

    void update(Project project);

    void updateModels(@Param(value = "id") Long id,@Param(value = "models")String models);

    void updateSenders(@Param(value = "id") Long id,@Param(value = "senders")String senders);

}