package noctem.userService.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.userService.user.domain.entity.UserAccount;
import noctem.userService.user.domain.entity.UserPrivacy;

/***
 * email : 첫 3자리만 마스킹
 * name : 박*우 -> 2글자 -> 이름 마스킹, 3글자 이상 첫/마지막 글자 외 마스킹
 * sex : 여자 | 남자
 * birthdayYear : **** -> 전부 마스킹 처리
 * phoneNumber : null | 010****0000 -> 중간 번호 마스킹
 */
@Data
@AllArgsConstructor
public class UserPrivacyInfoResDto {
    private String email;
    private String name;
    private String sex;
    private String birthdayYear;
    private String birthdayMonth;
    private String birthdayDay;
    private String phoneNumber;
    private String nickname;

    public UserPrivacyInfoResDto(UserAccount userAccount, UserPrivacy userPrivacy) {
        this.email = userAccount.getEmail();
        this.name = userPrivacy.getName();
        this.sex = userPrivacy.getSex().getValue();
        this.birthdayYear = userPrivacy.getBirthdayYear();
        this.birthdayMonth = userPrivacy.getBirthdayMonth();
        this.birthdayDay = userPrivacy.getBirthdayDay();
        this.phoneNumber = userPrivacy.getPhoneNumber();
        this.nickname = userAccount.getNickname();
    }

    public UserPrivacyInfoResDto applyMasking() {
        // email masking
        email = email.replaceAll("(?<=.{3}).(?=.*@)", "*");
        // name masking
        if (name.length() == 2) {
            name = name.replaceAll("(?<=.).", "*");
        } else {
            name = name.replaceAll("(?<=.).(?=.)", "*");
        }
        // birthdayYear masking
        birthdayYear = "****";
        // phoneNumber masking
        if (phoneNumber != null) {
            phoneNumber = phoneNumber.replaceAll("(?<=.{3}).(?=.{4,})", "*");
        }
        return this;
    }
}
