package de.mme.qbot;

import de.mme.qbot.model.domain.Question;
import de.mme.qbot.repositories.IQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.core.env.Environment;

import java.io.IOException;


@SpringBootApplication
public class QbotApplication {


	static Logger logger = LoggerFactory.getLogger(QbotApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(QbotApplication.class, args);

	}


	@Bean
	public CommandLineRunner demo(IQuestionRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Question("Wieviel uhr haben wir?"));
			repository.save(new Question("Was hast du gegessen?"));
			repository.save(new Question("Warum liegt da Heu?"));

			// fetch all customers
			logger.info("Question found with findAll():");
			logger.info("-------------------------------");
			for (Question question : repository.findAll()) {
				logger.info(question.toString());
			}
			logger.info("");

			// fetch an individual customer by ID
			Question question = repository.findById(1L);
			logger.info("Question found with findById(1L):");
			logger.info("--------------------------------");
			logger.info(question.toString());
			logger.info("");

		};
	}


}
