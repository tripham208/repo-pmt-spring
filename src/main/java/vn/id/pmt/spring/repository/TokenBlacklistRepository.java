package vn.id.pmt.spring.repository;



import org.springframework.data.repository.CrudRepository;
import vn.id.pmt.spring.entity.cache.TokenBlacklist;

public interface TokenBlacklistRepository extends CrudRepository<TokenBlacklist, String> {

}