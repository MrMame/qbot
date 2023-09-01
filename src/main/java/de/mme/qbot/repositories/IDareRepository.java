package de.mme.qbot.repositories;

import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.model.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface IDareRepository extends CrudRepository<Dare,Long> {

    Dare findById(long id);

}
