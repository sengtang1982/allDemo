package com.ebitg.spring.taskscheduler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.ebitg.spring.taskscheduler")
@EnableScheduling
public class Config {

}
