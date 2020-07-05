package com.example.demo.cursomc.service.validation;

import com.example.demo.cursomc.domain.enums.TipoCliente;
import com.example.demo.cursomc.dto.ClienteNewDto;
import com.example.demo.cursomc.resources.exceptions.FieldMessage;
import com.example.demo.cursomc.service.validation.utils.Br;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto> {

    @Override
    public void initialize(ClienteInsert cli){
    }

    @Override
    public boolean isValid(ClienteNewDto objDto, ConstraintValidatorContext context){
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !Br.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido!"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !Br.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido!"));
        }

        for(FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
