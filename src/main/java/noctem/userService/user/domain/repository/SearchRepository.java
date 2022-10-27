package noctem.userService.user.domain.repository;

import java.util.Set;

public interface SearchRepository {
    Boolean save(Long userAccountId, String query);

    Set<String> findAllByUserAccountId(Long userAccountId);
}
