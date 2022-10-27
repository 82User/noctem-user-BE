package noctem.userService.user.service;

import noctem.userService.user.dto.request.AddMyMenuReqDto;
import noctem.userService.user.dto.request.ChangeMyMenuAliasReqDto;
import noctem.userService.user.dto.request.ChangeMyMenuOrderReqDto;
import noctem.userService.user.dto.response.MyMenuListResDto;

import java.util.List;

public interface MyMenuService {
    List<MyMenuListResDto> getMyMenuList();

    Boolean addMyMenu(AddMyMenuReqDto dto);

    Boolean changeMyMenuOrder(ChangeMyMenuOrderReqDto dto);

    Boolean changeMyMenuAlias(Long myMenuId, ChangeMyMenuAliasReqDto dto);

    Boolean delMyMenu(Long myMenuId);

}
