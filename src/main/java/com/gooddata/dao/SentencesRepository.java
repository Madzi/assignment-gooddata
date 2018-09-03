package com.gooddata.dao;

import com.gooddata.dao.model.SentenceEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SentencesRepository extends CrudRepository<SentenceEntity, String> {

    @Override
    List<SentenceEntity> findAll();

}
