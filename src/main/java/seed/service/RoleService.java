package seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.entity.Role;
import seed.mapper.RoleMapper;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleMapper roleMapper;


    public Role get(Long id){
        return roleMapper.get(id);
    }

    public Role getByName(String name){
        return roleMapper.getByName(name);
    }

    public Long  save(Role data){
       return roleMapper.save(data);
    }

    public List<Role> find(int page, int size){
        return roleMapper.find((page-1)*size,size);
    }

    public int getCount(){
        return roleMapper.getCount();
    }

    public void delete(Long id){
        roleMapper.delete(id);
    }

    public void update( Long id, String name, String describe){
        roleMapper.update(id,name,describe);
    }

    public void updatePermission( Long id, String permission){
        roleMapper.updatePermission(id,permission);
    }

}