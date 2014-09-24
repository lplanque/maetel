package com.lplanque.maetel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.lplanque.maetel.service.ReleaseService;
import com.lplanque.maetel.service.UpdateService;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Component
public class Maetel implements CommandLineRunner {

	public static final String RELEASE_CMD = "release";
	public static final String UPDATE_CMD  = "update";
	
	@Autowired private ReleaseService release;
	@Autowired private UpdateService  update;
	
	@Value("${cmd:_}") private String cmd;
	
	private void help() { // TODO
		System.out.println("...");
	}
	
	@Override 
	public void run(String... arg0) throws Exception {
		switch(cmd.toLowerCase()) {
		case RELEASE_CMD: release.walk(); break;
		case UPDATE_CMD:  update.walk();  break;
		default:          help();
		}
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Maetel.class, args);
	}
}
