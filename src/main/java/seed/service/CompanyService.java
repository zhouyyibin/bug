package seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.entity.Company;
import seed.entity.Department;
import seed.mapper.CompanyMapper;
import seed.mapper.DepartmentMapper;

import java.util.List;

@Service
@Transactional
public class CompanyService {

    @Autowired
    CompanyMapper mapper;

    public Company get(Long userId){
        return  mapper.get(userId);
    }

    public Company getByName(String name){
        return mapper.getByName(name);
    }

    public Long save(Company data){
       return mapper.save(data);
    }

    public List<Company> find(int page, int size){

       return mapper.find((page-1)*size,size);

    }

    public int getCount(){
        return mapper.getCount();
    }

    public void delete( Long id){
        mapper.delete(id);
    }

    public void update(Company company){
        mapper.update(company);
    }
}
