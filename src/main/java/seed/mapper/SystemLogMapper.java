package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.SystemLog;

import java.util.List;

@Mapper
public interface SystemLogMapper {
    SystemLog get(Long id);

    Long  save(SystemLog data);

    List<SystemLog> find(@Param("page") int page, @Param("size") int size);

    int getCount();
}