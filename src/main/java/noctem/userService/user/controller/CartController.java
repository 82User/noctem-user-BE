package noctem.userService.user.controller;

import lombok.RequiredArgsConstructor;
import noctem.userService.user.dto.request.AddCartReqDto;
import noctem.userService.user.dto.request.ChangeMenuOptionReqDto;
import noctem.userService.user.dto.request.ChangeMenuQtyReqDto;
import noctem.userService.user.dto.response.CartListResDto;
import noctem.userService.user.service.CartService;
import noctem.userService.global.common.CommonResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${global.api.base-path}/carts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CartController {
    private final CartService cartService;

    @GetMapping("")
    public CommonResponse getCartList() {
        List<CartListResDto> cartList = cartService.getCartList();
        cartList.forEach(e -> e.setIndex(cartList.indexOf(e)));
        return CommonResponse.builder()
                .data(cartList)
                .build();
    }

    @GetMapping("/qty")
    public CommonResponse getCartTotalQty() {
        return CommonResponse.builder()
                .data(cartService.getCartTotalQty())
                .build();
    }

    @PostMapping("")
    public CommonResponse addMenuToCart(@Validated @RequestBody AddCartReqDto dto) {
        return CommonResponse.builder()
                .data(cartService.addMenuToCart(dto))
                .build();
    }

    @PatchMapping("/{sizeId}/quantities")
    public CommonResponse changeMenuQty(@PathVariable Long sizeId,
                                        @Validated @RequestBody ChangeMenuQtyReqDto dto) {
        return CommonResponse.builder()
                .data(cartService.changeMenuQty(sizeId, dto))
                .build();
    }

    @PutMapping("/{sizeId}/personalOptions")
    public CommonResponse changeMenuOption(@PathVariable Long sizeId,
                                           @Validated @RequestBody List<ChangeMenuOptionReqDto> dtoList) {
        return CommonResponse.builder()
                .data(cartService.changeMenuOption(sizeId, dtoList))
                .build();
    }

    @DeleteMapping("/{sizeId}")
    public CommonResponse delMenu(@PathVariable Long sizeId) {
        return CommonResponse.builder()
                .data(cartService.delMenu(sizeId))
                .build();
    }

    @DeleteMapping("/all")
    public CommonResponse delAllMenu() {
        return CommonResponse.builder()
                .data(cartService.delAllMenu())
                .build();
    }
}
