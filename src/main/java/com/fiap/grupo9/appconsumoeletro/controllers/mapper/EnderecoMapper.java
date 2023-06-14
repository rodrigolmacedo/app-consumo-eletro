package com.fiap.grupo9.appconsumoeletro.controllers.mapper;

import com.fiap.grupo9.appconsumoeletro.controllers.form.EnderecoForm;
import com.fiap.grupo9.appconsumoeletro.dominio.Endereco;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public EnderecoMapper(){

    }
    public ModelMapper getMapper(){
        return modelMapper;
    }
}
