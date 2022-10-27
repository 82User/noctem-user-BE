package noctem.userService.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAccountInfoResDto {
    private Integer userAge;
    private String userSex;
}
