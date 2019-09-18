package seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.entity.Permission;
import seed.mapper.PermissionMapper;
import seed.mapper.RoleMapper;

import java.util.List;

@Service
@Transactional
public class PermissionService {

    @Autowired
    PermissionMapper mapper;


    public Permission get(Long id){
        return mapper.get(id);
    }

    public Permission getByName(String name){
        return mapper.getByName(name);
    }

    public Long  save(Permission data){
       return mapper.save(data);
    }

    public List<Permission> find(){
        return mapper.find();
    }

    public int getCount(){
        return mapper.getCount();
    }

    public void delete(Long id){
        mapper.delete(id);
    }

    public void update(Permission permission){
        mapper.update(permission);
    }

}