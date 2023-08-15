package vn.id.pmt.spring;

import com.redis.om.spring.annotations.EnableRedisEnhancedRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableRedisEnhancedRepositories(basePackages = "vn.id.pmt.spring.*")
public class RepoPmtSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepoPmtSpringApplication.class, args);
    }

}
