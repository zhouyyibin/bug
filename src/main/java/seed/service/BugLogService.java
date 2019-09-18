package seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.entity.BugLog;
import seed.mapper.BugLogMapper;

import java.util.List;

@Service
@Transactional
public class BugLogService {

    @Autowired
    BugLogMapper mapper;


    public BugLog get(Long id){
        return mapper.get(id);
    }

    public Long  save(BugLog data){
       return mapper.save(data);
    }

    public List<BugLog> find(Long bugId){
        return mapper.find(bugId);
    }

    public int getCount(Long bugId){
        return mapper.getCount(bugId);
    }

}