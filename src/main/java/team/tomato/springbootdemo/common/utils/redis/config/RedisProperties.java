package team.tomato.springbootdemo.common.utils.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * yaml配置文件
 * @author lijiaqiang
 * @date 2020年5月18日16:11:51
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private Integer database;
    private String host;
    private Integer port;
    private String password;
    private Integer timeout;
    private Pool pool;

    @Data
    public static class Pool {
        private Integer maxActive;
        private Integer minIdle;
        private Integer maxIdle;
        private Integer maxWait;
    }
}
