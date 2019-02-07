package com.mqt.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mqt.beans.BeanMapper;

/**
 * Classe de démarrage de la plateforme serveur
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 14/07/2018
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mqt"})
@EntityScan(basePackages = {"com.mqt.pojo.entities"})
@EnableJpaRepositories(basePackages = {"com.mqt.repositories"})
@Configuration
@EnableWebMvc
public class Server extends WebMvcConfigurerAdapter {

  /**
   * Methode principale de démarrage de la plateforme
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(Server.class, args);
  }

  /**
   * Configuration du cross mapping
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }

  @Bean
  public BeanMapper beanMapper() {
    return BeanMapper.getInstance();
  }
  
  /**
   * Configure mutlipart requests size for uploading files
   * @return
   */
  @Bean
  EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
      return (ConfigurableEmbeddedServletContainer container) -> {
          if (container instanceof TomcatEmbeddedServletContainerFactory) {
              TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
              tomcat.addConnectorCustomizers(
                  (connector) -> {
                      connector.setMaxPostSize(25000000);
                  }
              );
          }
      };
  }
}
