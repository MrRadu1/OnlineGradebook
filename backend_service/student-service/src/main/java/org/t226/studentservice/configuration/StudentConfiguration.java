package org.t226.studentservice.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.t226.studentservice.controller.model.ExternalStudent;
import org.t226.studentservice.persistance.model.Student;


@Configuration
public class StudentConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Student, ExternalStudent>() {
            @Override
            protected void configure() {
                map(source.getData().getFirstname(), destination.getFirstname());
                map(source.getData().getLastname(), destination.getLastname());
                map(source.getData().getGrades(), destination.getGrades());
                map(source.getData().getCnp(), destination.getCnp());
                map(source.getData().getAddress(), destination.getAddress());
            }
        });
        modelMapper.addMappings(new PropertyMap<ExternalStudent, Student>() {
            @Override
            protected void configure() {
                map(source.getFirstname(), destination.getData().getFirstname());
                map(source.getLastname(), destination.getData().getLastname());
                map(source.getGrades(), destination.getData().getGrades());
                map(source.getCnp(), destination.getData().getCnp());
                map(source.getAddress(), destination.getData().getAddress());
            }
        });
        return modelMapper;
    }
}
