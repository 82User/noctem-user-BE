package noctem.userService.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.global.common.BaseEntity;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.MobileCarrier;
import noctem.userService.global.enumeration.Sex;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDate;

/***
 * name : 2글자 이상
 * mobileCarrier(통신사) : SKT|KT|LGU+
 * phoneNumber : 010\\d{7, 8} (프론트에서 하이픈 지우고 보내기)
 * birthdayYear : 00 ~ 99
 * birthdayMonth : 1 ~ 12
 * birthdayDay : 1 ~ 31 (상세검증은 프론트에서)
 * sex : M|F
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPrivacy extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_privacy_id")
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private MobileCarrier mobileCarrier;
    private String phoneNumber;
    private String birthdayYear;
    private String birthdayMonth;
    private String birthdayDay;
    private String rrnBackFirst;
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @JsonIgnore
    @OneToOne(mappedBy = "userPrivacy", fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @Builder
    public UserPrivacy(String name, String phoneNumber, String birthdayYear, String birthdayMonth, String birthdayDay, String rrnBackFirst) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthdayYear = birthdayYear;
        this.birthdayMonth = birthdayMonth;
        this.birthdayDay = birthdayDay;
        this.rrnBackFirst = rrnBackFirst;
        transSex(rrnBackFirst);
    }

    public UserPrivacy linkToUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public Integer getTodayUserAge() {
        LocalDate today = LocalDate.now();
        int todayYear = today.getYear();
        int todayMonth = today.getMonthValue();
        int todayDay = today.getDayOfMonth();
        int userYear = Integer.parseInt(birthdayYear);

        if (rrnBackFirst.equals("1") || rrnBackFirst.equals("2")) {
            userYear += 1900;
        } else if (rrnBackFirst.equals("3") || rrnBackFirst.equals("4")) {
            userYear += 2000;
        } else if (rrnBackFirst.equals("0") || rrnBackFirst.equals("9")) {
            userYear += 1800;
        }

        int age = todayYear - userYear - 1;

        if (Integer.parseInt(birthdayMonth) < todayMonth) {
            age++;
        } else if (Integer.parseInt(birthdayMonth) == todayMonth && Integer.parseInt(birthdayDay) <= todayDay) {
            age++;
        }

        return age;
    }

    private void transSex(String rrnBackFirst) {
        switch (rrnBackFirst) {
            case "1":
            case "3":
            case "5":
            case "7":
                this.sex = Sex.MALE;
                break;
            case "2":
            case "4":
            case "6":
            case "8":
                this.sex = Sex.FEMALE;
                break;
            default:
                throw CommonException.builder().errorCode(2011).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
