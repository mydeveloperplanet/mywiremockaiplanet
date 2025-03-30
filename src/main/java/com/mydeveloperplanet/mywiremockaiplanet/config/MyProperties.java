package com.mydeveloperplanet.mywiremockaiplanet.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties("my.properties")
public record MyProperties (@DefaultValue("http://localhost:1234/v1") String lmStudioBaseUrl) {
}
