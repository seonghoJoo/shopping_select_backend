package naver.shopping.select.repository;

import naver.shopping.select.model.ApiUseTime;
import naver.shopping.select.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {

    Optional<ApiUseTime> findByUser(User user);
}
