package noctem.userService.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import noctem.userService.global.common.BaseEntity;

import javax.persistence.*;

/***
 * pushNotificationAgreement : 푸시 알림 동의 여부
 * advertisementAgreement : 광고 수신 동의 여부
 * useLocationInfoAgreement : 위치정보 이용 동의 여부
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionalInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optional_info_id")
    private Long id;
    private Boolean isDarkmode;
    private Boolean pushNotificationAgreement;
    private Boolean advertisementAgreement;
    private Boolean useLocationInfoAgreement;
    private Boolean shakeToPay;
    private Boolean orderMyMenuFromHome;


    @JsonIgnore
    @OneToOne(mappedBy = "optionalInfo", fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @Builder
    public OptionalInfo(Boolean advertisementAgreement) {
        this.advertisementAgreement = advertisementAgreement; // 회원가입시 입력받음
        this.isDarkmode = false;
        this.pushNotificationAgreement = false;
        this.useLocationInfoAgreement = false;
        this.shakeToPay = false;
        this.orderMyMenuFromHome = false;
    }

    public OptionalInfo linkToUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public OptionalInfo changeDarkmode() {
        this.isDarkmode = this.isDarkmode ? false : true;
        return this;
    }

    public OptionalInfo changePushNotificationAgreement() {
        this.pushNotificationAgreement = this.pushNotificationAgreement ? false : true;
        return this;
    }

    public OptionalInfo changeAdvertisementAgreement() {
        this.advertisementAgreement = this.advertisementAgreement ? false : true;
        return this;
    }

    public OptionalInfo changeUseLocationInfoAgreement() {
        this.useLocationInfoAgreement = this.useLocationInfoAgreement ? false : true;
        return this;
    }

    public OptionalInfo changeShakeToPay() {
        this.shakeToPay = this.shakeToPay ? false : true;
        return this;
    }

    public OptionalInfo changeOrderMyMenuFromHome() {
        this.orderMyMenuFromHome = this.orderMyMenuFromHome ? false : true;
        return this;
    }
}
