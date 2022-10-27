package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.global.common.CommonException;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;

/***
 * phoneNumber : 하이픈(-) 빼고 숫자만 전달받기
 */
@Getter
@NoArgsConstructor
public class SignUpReqDto {
    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    @NotBlank
    private String rrnFront;
    @NotBlank
    private String rrnBackFirst;
    @Email
    private String email;
    @Length(min = 8, max = 40)
    private String password;

    // 필수
    @AssertTrue
    private Boolean termsOfServiceAgreement;
    @AssertTrue
    private Boolean personalInfoAgreement;
    // 선택
    private Boolean advertisementAgreement = false;

    public SignUpReqDto setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
        return this;
    }

    public SignUpReqDto dataformatMatching() {
        // 이름 검증
        if (this.name.length() < 2) {
            throw CommonException.builder().errorCode(2019).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        // 날짜형식 검증
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        try {
            sdf.setLenient(false);
            sdf.parse(this.rrnFront);
        } catch (Exception exception) {
            throw CommonException.builder().errorCode(2012).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        // 비밀번호 검증
        if (this.password == null || !this.password.matches("^[\\w/\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-+<>@\\#$%&\\\\\\=\\(\\'\\\"]{8,40}$")) {
            throw CommonException.builder().errorCode(2014).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return this;
    }
}
