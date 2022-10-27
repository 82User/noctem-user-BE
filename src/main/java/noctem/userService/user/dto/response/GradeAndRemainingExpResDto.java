package noctem.userService.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeAndRemainingExpResDto {
    private String userGrade;
    private Integer userExp;
    private String nextGrade;
    private Integer requiredExpToNextGrade;
}
