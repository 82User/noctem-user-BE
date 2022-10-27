package noctem.userService.user.domain.repository;

import noctem.userService.user.domain.entity.OptionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionalInfoRepository extends JpaRepository<OptionalInfo, Long> {
    OptionalInfo findByUserAccountId(Long id);
}
