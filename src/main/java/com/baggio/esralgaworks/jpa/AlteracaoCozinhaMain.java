package com.baggio.esralgaworks.jpa;

import com.baggio.esralgaworks.EsralgaworksApplication;
import com.baggio.esralgaworks.domain.model.Cozinha;
import com.baggio.esralgaworks.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AlteracaoCozinhaMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(EsralgaworksApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha = new Cozinha();
        cozinha.setId(1L);
        cozinha.setNome("Brasileira");

        cozinhaRepository.salvar(cozinha);

        System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
    }

}
