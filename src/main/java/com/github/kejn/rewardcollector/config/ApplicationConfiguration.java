package com.github.kejn.rewardcollector.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JacksonConfiguration.class, ModelMapperConfiguration.class})
public class ApplicationConfiguration {

}
