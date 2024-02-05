package com.baggio.esralgaworks.jpa;

import com.baggio.esralgaworks.EsralgaworksApplication;
import com.baggio.esralgaworks.domain.model.Cozinha;
import com.baggio.esralgaworks.domain.repository.CozinhaRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(EsralgaworksApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        List<Cozinha>  cozinhas =  cozinhaRepository.listar();
        for (Cozinha cozinha : cozinhas) {
            System.out.println(cozinha.getId() + " - " + cozinha.getNome());
        }
    }

}
