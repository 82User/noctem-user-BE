package noctem.userService.user.service;

import noctem.userService.user.dto.request.SearchReqDto;

import java.util.Set;

public interface SearchService {
    Boolean save(SearchReqDto dto);

    Set<String> getAllQuery();
}
