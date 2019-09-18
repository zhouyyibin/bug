package seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.entity.Department;
import seed.mapper.DepartmentMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    public Department get(Long userId){
        return  departmentMapper.get(userId);
    }

    public Long save(Department data){
       Long id = departmentMapper.save(data);
       if(data.getParentId()==null || data.getParentId().equals(0)){
           updateCode(data.getId(),data.getId()+"");
           return  id;
       }
       Department department = get(data.getParentId());
       if(department!=null){
           String code = department.getCode()+"|"+data.getId();
           updateCode(data.getId(),code);
       }
       return id;
    }

    public Department getByName(String name){
        return departmentMapper.getByName(name);
    }

    public Department getByNameAndParentId(String name, Long parentId){
        return departmentMapper.getByNameAndParentId( name,  parentId);
    }

    public List<Department> find(int page, int size){

       return departmentMapper.find((page-1)*size,size);

    }

    public int getCount(){
        return departmentMapper.getCount();
    }

    public void delete( Long id){
        departmentMapper.delete(id);
    }

    public void update(Long id,String name,Long userId,Long parentId){
        Department department = get(id);
        Department parentD = get(id);
        if(parentD!=null){
            department.setCode(parentD.getCode()+"|"+department.getCode());
        }
        departmentMapper.update(id,name,userId,parentId,department.getCode());
    }

    public void updateCode( Long id,  String code){
        departmentMapper.updateCode(id,code);
    }

    public List<Department> findTree(List<Department> departments){
        List<Department> reslutDepartment = new ArrayList<>();
       if(departments.size()>0){
           departments.forEach(parent->{
               getChileTree(parent);
               reslutDepartment.add(parent);
              // List<Department> departments = departmentMapper.findByParentId(parent.getId());
               //parent.setDepartments(departments);
           });
       }

       return reslutDepartment;
    }

    public List<Department> findByParentId(Long parentId){return departmentMapper.findByParentId(parentId);}

    public void getChileTree(Department department){
        List<Department> departments = departmentMapper.findByParentId(department.getId());
        if(departments.size()==0){
            return ;
        }
        department.setDepartments(departments);
        department.getDepartments().forEach(item -> {
            getChileTree(item);
        });
    }

}
