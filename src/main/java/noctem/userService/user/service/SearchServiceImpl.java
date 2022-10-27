package noctem.userService.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.global.security.bean.ClientInfoLoader;
import noctem.userService.user.domain.repository.SearchRepository;
import noctem.userService.user.dto.request.SearchReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;
    private final ClientInfoLoader clientInfoLoader;

    @Override
    public Boolean save(SearchReqDto dto) {
        return searchRepository.save(clientInfoLoader.getUserAccountId(), dto.getQuery());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> getAllQuery() {
        return searchRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
    }
}
