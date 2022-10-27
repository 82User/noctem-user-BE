package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ChangeNicknameReqDto {
    @NotBlank
    private String nickname;
}
