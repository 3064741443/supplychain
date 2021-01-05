package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.SubjectMapper;
import cn.com.glsx.supplychain.model.bs.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectService.class);

    @Autowired
    private SubjectMapper subjectMapper;

    public List<Subject> getlist(Subject subject){
        LOGGER.info("查询项目定义表入参{}",subject);
        List<Subject> result;
        result = subjectMapper.select(subject);
        return result;
    }
}
