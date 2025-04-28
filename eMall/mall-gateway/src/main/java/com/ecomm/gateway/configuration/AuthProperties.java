package com.ecomm.gateway.configuration;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mall.auth")
public class AuthProperties {
    private List<String> includePaths;
    private List<String> excludePaths;
}
