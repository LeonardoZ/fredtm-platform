package com.fredtm.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.OperationRepository;

public class Application implements CommandLineRunner {

	@Autowired
	OperationRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	@Transactional
	
	public void run(String... args) throws Exception {
		Operation operation = new Operation("Coleta de café", "Café S.A.",
				"Contagem de tempo dos trabalhadores J. e Y.");
		Operation operation2 = new Operation("Coleta de Milho", "Milho S.A.",
				"Contagem de tempo dos trabalhadores J. e Y.");
		Operation operation3 = new Operation("Coleta de cana", "Cana S.A.",
				"Contagem de tempo dos trabalhadores J. e Y.");

		repo.save(operation);
		repo.save(operation2);
		repo.save(operation3);

		Iterable<Operation> findAll = repo.findAll();
		findAll.forEach(saved -> {
			System.out.println("Id: " + saved.getId());
			System.out.println("Name: " + saved.getName());
			System.out.println("Com: " + saved.getCompany());
			System.out.println("Tech: " + saved.getTechnicalCharacteristics());
		});
	}

}
