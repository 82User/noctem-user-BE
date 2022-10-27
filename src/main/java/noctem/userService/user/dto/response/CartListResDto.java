package noctem.userService.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.userService.user.domain.entity.Cart;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartListResDto {
    private Integer index;
    private Long cartId;
    private Long sizeId;
    private Integer qty;
    private List<String> myPersonalOptionList;

    public CartListResDto(Cart cart) {
        this.cartId = cart.getId();
        this.sizeId = cart.getSizeId();
        this.qty = cart.getQty();
        this.myPersonalOptionList = new ArrayList<>();
    }
}
