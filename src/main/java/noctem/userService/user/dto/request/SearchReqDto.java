package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SearchReqDto {
    @NotBlank
    private String query;
}
