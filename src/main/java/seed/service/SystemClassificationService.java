package seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.entity.SystemClassification;
import seed.mapper.SystemClassificationMapper;
import java.util.List;

@Service
@Transactional
public class SystemClassificationService {

    @Autowired
    SystemClassificationMapper mapper;

   public SystemClassification get(Long id){
        return mapper.get(id);
    }

   public SystemClassification getByNameAndType(String name, String type){
        return mapper.getByNameAndType(name,type);
    }

   public  Long  save(SystemClassification data){
        return mapper.save(data);

    }

    public  void  updateContent(SystemClassification data){
         mapper.updateContent(data);

    }

   public List<SystemClassification> findByType( String type){
        return mapper.findByType(type);
    }

   public int getCountByType(String type){
        return mapper.getCountByType(type);
    }

   public void delete( Long id){
        mapper.delete(id);
    }

}