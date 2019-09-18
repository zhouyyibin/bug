package seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.entity.Project;
import seed.mapper.ProjectMapper;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    @Autowired
    ProjectMapper mapper;


    public Project get(Long id){
        return mapper.get(id);
    }

    public Project getByName(String name){
        return mapper.getByName(name);
    }

    public Long  save(Project data){
       return mapper.save(data);
    }

    public List<Project> find(int page, int size){
        return mapper.find((page-1)*size,size);
    }

    public int getCount(){
        return mapper.getCount();
    }

    public void delete(Long id){
        mapper.delete(id);
    }

    public void update(Project project){
        mapper.update(project);
    }


}