package noctem.userService.user.domain.repository;

public interface QueryDslRepository {

    Integer cartTotalQty(Long userAccountId);

    void deleteAllCartByUserAccountId(Long userAccountId);
}
