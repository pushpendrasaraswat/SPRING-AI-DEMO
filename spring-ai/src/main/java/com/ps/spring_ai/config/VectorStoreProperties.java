package com.ps.spring_ai.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "data.vector")
public class VectorStoreProperties {
    private String storePath;
    private List<Resource> documentsToLoad;

}
