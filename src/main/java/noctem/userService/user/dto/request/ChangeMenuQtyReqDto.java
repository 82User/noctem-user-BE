package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class ChangeMenuQtyReqDto {
    @Range(min = 1, max = 99)
    private Integer qty;
}
