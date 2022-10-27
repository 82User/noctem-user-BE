package noctem.userService.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Amount;
import noctem.userService.global.security.bean.ClientInfoLoader;
import noctem.userService.user.domain.entity.MyMenu;
import noctem.userService.user.domain.entity.MyPersonalOption;
import noctem.userService.user.domain.repository.MyMenuRepository;
import noctem.userService.user.domain.repository.UserAccountRepository;
import noctem.userService.user.dto.MenuComparisonJsonDto;
import noctem.userService.user.dto.request.AddMyMenuReqDto;
import noctem.userService.user.dto.request.ChangeMyMenuAliasReqDto;
import noctem.userService.user.dto.request.ChangeMyMenuOrderReqDto;
import noctem.userService.user.dto.response.MyMenuListResDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyMenuServiceImpl implements MyMenuService {
    private final UserAccountRepository userAccountRepository;
    private final MyMenuRepository myMenuRepository;
    private final ClientInfoLoader clientInfoLoader;

    @Transactional(readOnly = true)
    @Override
    public List<MyMenuListResDto> getMyMenuList() {
        List<MyMenu> myMenuList = myMenuRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        return myMenuList.stream().map(MyMenuListResDto::new).collect(Collectors.toList());
    }

    @Override
    public Boolean addMyMenu(AddMyMenuReqDto dto) {
        List<MyMenu> myMenuList = myMenuRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        Map<String, MyMenu> myMenuMap = new HashMap<>();
        myMenuList.forEach(e -> myMenuMap.put(new MenuComparisonJsonDto().myMenuAndOptionEntityToJson(e), e));
        String dtoJson = new MenuComparisonJsonDto().addMyMenuReqDtoToJson(dto);
        if (myMenuMap.containsKey(dtoJson)) {
            // 이미 존재하는 메뉴
            return false;
        } else {
            // 존재하지 않는 메뉴 -> 추가
            MyMenu myMenu = MyMenu.builder().alias(dto.getAlias()).sizeId(dto.getSizeId()).build();
            myMenu.linkToUserAccount(userAccountRepository.findById(clientInfoLoader.getUserAccountId()).get());
            dto.getPersonalOptionList().forEach(e ->
                    myMenu.linkToMyPersonalOption(MyPersonalOption.builder()
                            .personalOptionId(e.getOptionId())
                            .amount(Amount.findByValue(e.getAmount()))
                            .build())
            );
            myMenuRepository.save(myMenu);
        }
        return true;
    }

    @Override
    public Boolean changeMyMenuOrder(ChangeMyMenuOrderReqDto dto) {
        Map<Long, Integer> indexMap = dto.generateIndexMap();
        List<MyMenu> myMenuList = myMenuRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        myMenuList.forEach(e -> e.changeMyMenuOrder(indexMap.get(e.getId())));
        return true;
    }

    @Override
    public Boolean changeMyMenuAlias(Long myMenuId, ChangeMyMenuAliasReqDto dto) {
        identificationMyMenu(myMenuId).changeAlias(dto.getAlias());
        return true;
    }

    @Override
    public Boolean delMyMenu(Long myMenuId) {
        myMenuRepository.delete(identificationMyMenu(myMenuId));
        return true;
    }

    // 요청한 myMenu가 본인 것이 맞는지 확인
    private MyMenu identificationMyMenu(Long myMenuId) {
        Optional<MyMenu> optionalMyMenu = myMenuRepository.findById(myMenuId);
        if (optionalMyMenu.isEmpty()) {
            throw CommonException.builder().errorCode(2025).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        if (!Objects.equals(optionalMyMenu.get().getUserAccount().getId(), clientInfoLoader.getUserAccountId())) {
            throw CommonException.builder().errorCode(2001).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        return optionalMyMenu.get();
    }
}
