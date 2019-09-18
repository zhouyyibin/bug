package seed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import seed.entity.Bug;

import java.util.List;

@Mapper
public interface BugMapper {
    Bug get(Long id);

    Long  save(Bug data);

    Bug getByTitle(@Param("title") String title);

    List<Bug> findTop(@Param("page") int page, @Param("size") int size,@Param("leadingId")Long leadingId,@Param("creatorId")Long creatorId);

    List<Bug> find(@Param("page") int page,
                   @Param("size") int size,
                   @Param("leadingId")Long leadingId,
                   @Param("creatorId")Long creatorId,
                   @Param("keyword")String keyword,
                   @Param("departmentCode") String departmentCode,
                   @Param("project") String project,
                    @Param("sender")Long sender);

    List<Bug> findByDepartment(@Param("departmentCode") String departmentCode);

    int getCountByDepartment(@Param("departmentCode") String departmentCode);

    int getCount(@Param("leadingId")Long leadingId,
                 @Param("creatorId")Long creatorId,
                 @Param("keyword")String keyword,
                 @Param("departmentCode") String departmentCode,
                 @Param("project") String project,
                 @Param("sender")Long sender);

    void delete(@Param(value = "id") Long id);

    void update(Bug bug);

    void updateBase(Bug bug);

    int getCountAllFinish(@Param("leadingId")Long leadingId,@Param("creatorId")Long creatorId, @Param("status")int status);

    int getCountAllTimeOut(@Param("leadingId")Long leadingId,@Param("creatorId")Long creatorId, @Param("status")int status);

    int getCountAllDoing(@Param("leadingId")Long leadingId,@Param("creatorId")Long creatorId, @Param("status")int status);

    int getCountByProjectAndStatus(@Param("project")String project,@Param("status")int status);

    int getCountByProjectAndEtStatus(@Param("project")String project,@Param("status")int status);

    int getCountByDepartmentAndStatus(@Param("departmentCode")String departmentCode,@Param("status")int status);

    int getCountByDepartmentAndEtStatus(@Param("departmentCode")String departmentCode,@Param("status")int status);

    int getCountByStatus(@Param("status")int status);

    int getCountBySeverity(@Param("severity")String severity);

    int getCountByPriority(@Param("priority")String priority);

    int getCountByProject(@Param("project")String project);

    int getCountByModel(@Param("model")String model);

    int getCountByTime();

}