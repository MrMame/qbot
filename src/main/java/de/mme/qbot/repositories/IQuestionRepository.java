package de.mme.qbot.repositories;

import de.mme.qbot.model.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface IQuestionRepository extends CrudRepository<Question,Long> {

    Question findById(long id);

}
