package com.gooddata.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SentencesRepository extends CrudRepository<SentenceEntity, Long> {

    @Override
    List<SentenceEntity> findAll();

}
