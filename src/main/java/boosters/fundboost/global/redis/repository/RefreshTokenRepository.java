package boosters.fundboost.global.redis.repository;

import boosters.fundboost.global.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long> {
}
