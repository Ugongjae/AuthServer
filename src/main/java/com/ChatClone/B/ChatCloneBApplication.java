package com.ChatClone.B;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javassist.bytecode.stackmap.TypeData.ClassName;


@SpringBootApplication
public class ChatCloneBApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatCloneBApplication.class, args);
	}

}
