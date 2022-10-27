package noctem.userService.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.userService.user.domain.entity.UserAccount;

@Data
@AllArgsConstructor
public class RankingResDto {
    private Integer index;
    private Integer rank;
    private String nickname;
    private String grade;
    private Integer gradeAccumulateExp;

    public RankingResDto(UserAccount userAccount) {
        this.nickname = userAccount.getNickname();
        this.grade = userAccount.getGrade().getValue();
        this.gradeAccumulateExp = userAccount.getGradeAccumulateExp();
    }
}
