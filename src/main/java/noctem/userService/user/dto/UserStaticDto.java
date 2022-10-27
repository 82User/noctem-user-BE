package noctem.userService.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import noctem.userService.user.domain.entity.MyPersonalOption;

public class UserStaticDto {
    @Data
    @NoArgsConstructor
    public static class PersonalOptionReqDto {
        private Long optionId;
        private String amount;

        public PersonalOptionReqDto(MyPersonalOption myPersonalOption) {
            this.optionId = myPersonalOption.getPersonalOptionId();
            this.amount = myPersonalOption.getAmount().getValue();
        }
    }
}
