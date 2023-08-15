package vn.id.pmt.spring.service;

import vn.id.pmt.spring.entity.cache.TokenBlacklist;

import java.util.Optional;

public interface TokenService {

    /**
     * Check black list boolean.
     *
     * @param token the token
     * @return the boolean
     */
    boolean checkBlackList(String token);

    /**
     * Insert black list.
     *
     * @param token the token
     */
    void insertBlackList(TokenBlacklist token);
}
