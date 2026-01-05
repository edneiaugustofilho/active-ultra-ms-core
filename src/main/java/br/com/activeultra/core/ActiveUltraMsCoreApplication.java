package br.com.activeultra.core;

import br.com.activeultra.core.util.ConsoleLogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableFeignClients
public class ActiveUltraMsCoreApplication {

    @Value("${app.company.name}")
    private String companyName;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.profiles.version}")
    private String versao;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public static void main(String[] args) {
        SpringApplication.run(ActiveUltraMsCoreApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printStartedBanner() {
        String labelName = ConsoleLogUtil.formatar("Nome", ConsoleLogUtil.Cor.AMARELO, ConsoleLogUtil.Estilo.NEGRITO);
        String formatarName = ConsoleLogUtil.formatar("%s", ConsoleLogUtil.Cor.AZUL);
        String labelVersao = ConsoleLogUtil.formatar("Versão", ConsoleLogUtil.Cor.AMARELO, ConsoleLogUtil.Estilo.NEGRITO);
        String formatarVersao = ConsoleLogUtil.formatar("%s", ConsoleLogUtil.Cor.ROXO);
        String labelProfile = ConsoleLogUtil.formatar("Profile", ConsoleLogUtil.Cor.AMARELO, ConsoleLogUtil.Estilo.NEGRITO);
        String formatarProfile = ConsoleLogUtil.formatar("%s", ConsoleLogUtil.Cor.CIANO);

        String labelPoweredBy = ConsoleLogUtil.formatar("Powered by", ConsoleLogUtil.Cor.AMARELO, ConsoleLogUtil.Estilo.NEGRITO);
        String formatarPoweredBy = ConsoleLogUtil.formatar("%s", ConsoleLogUtil.Cor.VERDE);

        String banner =
                ":: " + labelName + " " + formatarName + " ::\n" +
                        ":: " + labelVersao + " " + formatarVersao + " ::\n" +
                        ":: " + labelProfile + " " + formatarProfile + " ::\n" +
                        ":: " + labelPoweredBy + " " + formatarPoweredBy + " ::\n";

        System.out.printf((banner) + "%n", applicationName, versao, activeProfile, companyName);
    }

}
