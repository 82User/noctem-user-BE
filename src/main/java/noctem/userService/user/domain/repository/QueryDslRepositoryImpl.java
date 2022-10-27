package noctem.userService.user.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static noctem.userService.user.domain.entity.QCart.cart;
import static noctem.userService.user.domain.entity.QMyPersonalOption.myPersonalOption;
import static noctem.userService.user.domain.entity.QUserAccount.userAccount;

@Repository
public class QueryDslRepositoryImpl implements QueryDslRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public QueryDslRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Integer cartTotalQty(Long userAccountId) {
        return queryFactory.select(cart.qty.sum())
                .from(cart)
                .join(cart.userAccount, userAccount)
                .where(userAccount.id.eq(userAccountId))
                .fetchOne();
    }

    @Override
    public void deleteAllCartByUserAccountId(Long userAccountId) {
        List<Long> cartIdList = queryFactory.select(cart.id)
                .from(cart)
                .join(cart.userAccount, userAccount)
                .where(userAccount.id.eq(userAccountId))
                .fetch();
        queryFactory.delete(myPersonalOption)
                .where(myPersonalOption.cart.id.in(cartIdList))
                .execute();
        queryFactory.delete(cart)
                .where(cart.id.in(cartIdList))
                .execute();
    }
}
