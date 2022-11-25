package com.aios.bananesexport.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "commande")
@Getter
@Setter
public class ConfigProperties {
    
    private double prixKg;
    private int quantiteMinimum;
    private int quantiteMaximum;
    private int quantiteMultiple;
}