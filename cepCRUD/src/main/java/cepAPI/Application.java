package cepAPI;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "cepAPI.repository")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ Aplicação conectada! [Running]");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Falha ao carregar a aplicação! [ERROR]");
            e.printStackTrace();
        }
    }
}