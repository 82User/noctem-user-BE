package noctem.userService.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import noctem.userService.global.common.BaseEntity;

import javax.persistence.*;

/***
 * termsOfServiceAgreement : 이용약관 동의 여부
 * personalInfoAgreement : 개인정보 수집 및 이용 동의 여부
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequiredInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "required_info_id")
    private Long id;

    private Boolean termsOfServiceAgreement;
    private Boolean personalInfoAgreement;

    @JsonIgnore
    @OneToOne(mappedBy = "requiredInfo", fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @Builder
    public RequiredInfo(Boolean termsOfServiceAgreement, Boolean personalInfoAgreement) {
        this.termsOfServiceAgreement = termsOfServiceAgreement;
        this.personalInfoAgreement = personalInfoAgreement;
    }

    public RequiredInfo linkToUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }
}
