package com.kadiryasar.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"com.kadiryasar"}) //your basePackageName
@EntityScan(basePackages = {"com.kadiryasar"}) //your basePackageName
@EnableJpaRepositories(basePackages = {"com.kadiryasar"}) //your basePackageName
@SpringBootApplication
public class BookCommentSystemApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(BookCommentSystemApplicationStarter.class, args);
	}

}
