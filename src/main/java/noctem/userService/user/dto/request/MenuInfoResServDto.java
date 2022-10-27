package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoResServDto {
    @Min(1)
    private Long cartOrMyMenuId;
    @Min(1)
    private Long sizeId;
    @NotBlank
    private String menuName;
    @NotBlank
    private String menuEngName;
    @NotBlank
    private String menuImg;
    @NotBlank
    private String temperature;
    @NotBlank
    private String size;
    @Min(0)
    private Integer totalPrice; // 옵션을 제외한 메뉴가격
}
