package com.fredtm.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mangofactory.swagger.configuration.DocumentationConfig;

@Configuration
@Import(DocumentationConfig.class)
public class CustomDocumentationConfig {
    

 
}