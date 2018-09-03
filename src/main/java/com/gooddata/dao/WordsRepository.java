package com.gooddata.dao;

import com.gooddata.dao.model.WordEntity;
import com.gooddata.domain.model.Category;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface WordsRepository extends CrudRepository<WordEntity, Long> {

    @Override
    List<WordEntity> findAll();

    List<WordEntity> findByName(String name);

    List<WordEntity> findByCategory(Category category);

}
