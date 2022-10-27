package noctem.userService.user.domain.repository;

import noctem.userService.user.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    List<UserAccount> findTop100ByOrderByGradeAccumulateExpDesc();
}
