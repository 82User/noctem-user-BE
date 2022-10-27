package noctem.userService.user.domain.repository;

import noctem.userService.user.domain.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>, QueryDslRepository {
    @Override
    @EntityGraph(attributePaths = {"myPersonalOptionList", "userAccount"})
    Optional<Cart> findById(Long cartId);

    @EntityGraph(attributePaths = {"myPersonalOptionList"})
    List<Cart> findAllByUserAccountId(Long userAccountId);
}
