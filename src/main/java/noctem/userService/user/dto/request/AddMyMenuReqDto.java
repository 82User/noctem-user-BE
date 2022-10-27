package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.user.dto.UserStaticDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class AddMyMenuReqDto {
    @Min(1)
    private Long sizeId;
    @NotBlank
    private String alias;
    private List<UserStaticDto.PersonalOptionReqDto> personalOptionList;
}
