package com.example.chatgpt_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy  // Enable AspectJ proxy support
public class ChatGptProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatGptProjectApplication.class, args);
	}

}
