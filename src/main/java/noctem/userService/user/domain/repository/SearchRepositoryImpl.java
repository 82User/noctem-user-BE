package noctem.userService.user.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository {
    private final long NUMBER_TO_SAVE = 5L;
    private final String MENU_SEARCH_KEY_PREFIX = "menuSearch";
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Boolean save(Long userAccountId, String query) {
        String key = String.format("%s:%d", MENU_SEARCH_KEY_PREFIX, userAccountId);
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        opsForZSet.add(key, query, Double.parseDouble(dateTime));
        opsForZSet.removeRange(key, -1 - NUMBER_TO_SAVE, -1 - NUMBER_TO_SAVE);
        return true;
    }

    @Override
    public Set<String> findAllByUserAccountId(Long userAccountId) {
        String key = String.format("%s:%d", MENU_SEARCH_KEY_PREFIX, userAccountId);
        return redisTemplate.opsForZSet().reverseRange(key, 0, NUMBER_TO_SAVE);
    }
}
