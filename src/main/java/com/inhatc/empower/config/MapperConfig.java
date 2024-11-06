package com.inhatc.empower.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class MapperConfig {
    // DTO와Entity 변환을 위한 Mapper
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
