package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.BugLog;

import java.util.List;

@Mapper
public interface BugLogMapper {
    BugLog get(Long id);

    Long  save(BugLog data);

    List<BugLog> find(@Param("bugId") Long bugId);

    int getCount(@Param("bugId") Long bugId);

}