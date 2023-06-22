package com.fiap.grupo9.appconsumoeletro.controllers.mapper;

import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
    private org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();

    public ModelMapper(){

    }
    public org.modelmapper.ModelMapper getMapper(){
        return modelMapper;
    }
}
