package com.desafio.LiterAlura;

import com.desafio.LiterAlura.Main.Main;
import com.desafio.LiterAlura.repository.AutorRepository;
import com.desafio.LiterAlura.repository.DadosLivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository repository;

	@Autowired
	private DadosLivroRepository dadosLivroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository, dadosLivroRepository);
		main.ExibirMenu();
	}
}
