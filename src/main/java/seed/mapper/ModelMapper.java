package seed.mapper;

import seed.entity.Model;

public interface ModelMapper {
    Model selectByPrimaryKey(Long id);
}