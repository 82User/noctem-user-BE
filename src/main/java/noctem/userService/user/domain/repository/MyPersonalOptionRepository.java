package noctem.userService.user.domain.repository;

import noctem.userService.user.domain.entity.MyPersonalOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPersonalOptionRepository extends JpaRepository<MyPersonalOption, Long> {
    List<MyPersonalOption> findAllByMyMenuId(Long id);
}
