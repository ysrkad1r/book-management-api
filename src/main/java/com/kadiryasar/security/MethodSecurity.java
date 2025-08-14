package com.kadiryasar.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity //methodlar icin proxy disinda kendi kontrolleri icin bir kanal olusturur
public class MethodSecurity {



}
