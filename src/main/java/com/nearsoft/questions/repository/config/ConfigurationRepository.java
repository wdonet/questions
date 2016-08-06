package com.nearsoft.questions.repository.config;

import com.nearsoft.questions.domain.config.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, Long>{

    Configuration findByName(String name);

    @Override
    @RestResource(exported = false)
    void delete(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Configuration configuration);

    @Override
    @RestResource(exported = false)
    void delete(Iterable<? extends Configuration> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
