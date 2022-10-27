package noctem.userService.user.domain.repository;

import noctem.userService.user.domain.entity.UserPrivacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivacyRepository extends JpaRepository<UserPrivacy, Long> {

    UserPrivacy findByUserAccountId(Long id);
}
