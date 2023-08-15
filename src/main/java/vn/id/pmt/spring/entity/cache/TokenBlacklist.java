package vn.id.pmt.spring.entity.cache;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Data
@Builder
@RedisHash(value = "token", timeToLive = 3600)
@AllArgsConstructor
@NoArgsConstructor
public class TokenBlacklist {
    @Id
    private String token;
}
