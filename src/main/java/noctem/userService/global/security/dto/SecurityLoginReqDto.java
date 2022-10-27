package noctem.userService.global.security.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityLoginReqDto {
    @Email
    private String email;
    @NotEmpty
    private String password;
}
