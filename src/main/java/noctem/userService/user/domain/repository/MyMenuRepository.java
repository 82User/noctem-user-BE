package noctem.userService.user.domain.repository;

import noctem.userService.user.domain.entity.MyMenu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyMenuRepository extends JpaRepository<MyMenu, Long> {
    @EntityGraph(attributePaths = {"myPersonalOptionList", "userAccount"})
    @Override
    Optional<MyMenu> findById(Long myMenuId);

    @EntityGraph(attributePaths = {"myPersonalOptionList"})
    List<MyMenu> findAllByUserAccountId(Long userAccountId);
}
