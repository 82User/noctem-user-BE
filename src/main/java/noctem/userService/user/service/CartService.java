package noctem.userService.user.service;

import noctem.userService.user.dto.request.AddCartReqDto;
import noctem.userService.user.dto.request.ChangeMenuOptionReqDto;
import noctem.userService.user.dto.request.ChangeMenuQtyReqDto;
import noctem.userService.user.dto.response.CartListResDto;

import java.util.List;

public interface CartService {
    List<CartListResDto> getCartList();

    Integer getCartTotalQty();

    Boolean addMenuToCart(AddCartReqDto dto);

    Boolean changeMenuQty(Long cartId, ChangeMenuQtyReqDto dto);

    Boolean changeMenuOption(Long cartId, List<ChangeMenuOptionReqDto> dtoList);

    Boolean delMenu(Long cartId);

    Boolean delAllMenu();
}
