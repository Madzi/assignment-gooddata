package com.gooddata.dao;

import com.gooddata.dao.model.WordEntity;
import org.springframework.data.repository.CrudRepository;

public interface WordsRepository extends CrudRepository<WordEntity, Long> {
}
