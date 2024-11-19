package boosters.fundboost.like.repository;

import boosters.fundboost.like.domain.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Like, Long> {
    Page<Like> findAllByUser_Id(Long userId, Pageable pageable);
}
