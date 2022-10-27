package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ChangeMenuOptionReqDto {
    @Min(1)
    private Long myPersonalOptionId;
    @NotBlank
    private String amount;
}
