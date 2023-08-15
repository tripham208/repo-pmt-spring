package vn.id.pmt.spring.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.id.pmt.spring.entity.cache.TokenBlacklist;
import vn.id.pmt.spring.repository.TokenBlacklistRepository;
import vn.id.pmt.spring.service.TokenService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    TokenBlacklistRepository repository;

    /**
     * Check black list boolean.
     *
     * @param token the token
     * @return the boolean
     */
    @Override
    public boolean checkBlackList(String token) {
        Optional<TokenBlacklist> tokenBlacklist = repository.findById(token);
        return tokenBlacklist.isPresent();
    }

    /**
     * Insert black list.
     *
     * @param token the token
     */
    @Override
    public void insertBlackList(TokenBlacklist token) {
        repository.save(token);
    }
}
