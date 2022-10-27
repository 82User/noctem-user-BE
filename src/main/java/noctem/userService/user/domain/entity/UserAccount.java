package noctem.userService.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import noctem.userService.global.common.BaseEntity;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Grade;
import noctem.userService.global.enumeration.Role;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/***
 * password : 8 ~ 40자리
 * nickname : 2 ~ 8자리
 * gradeAccumulateExp : 누적 경험치
 * grade : 결제 -> 경험치 증가 -> 등급 업
 * 0 ~ 9.9만원 : Tall
 * 10 ~ 39.9만원 : Grande
 * 40 ~ 만원 : Venti
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;

    @Column(unique = true)
    private String nickname;

    private LocalDateTime lastAccessTime;

    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.POTION;
    private Integer gradeAccumulateExp = 0;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_privacy_id")
    private UserPrivacy userPrivacy;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "required_info_id")
    private RequiredInfo requiredInfo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "optional_info_id")
    private OptionalInfo optionalInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyMenu> myMenuList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartList = new ArrayList<>();

    @Builder
    public UserAccount(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nicknameFormatMatching(nickname);
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = LocalDateTime.now();
    }

    public UserAccount linkToCart(Cart cart) {
        this.cartList.add(cart);
        return this;
    }

    public UserAccount linkToMyMenu(MyMenu myMenu) {
        this.myMenuList.add(myMenu);
        return this;
    }

    public UserAccount linkToUserPrivacy(UserPrivacy userPrivacy) {
        this.userPrivacy = userPrivacy;
        userPrivacy.linkToUserAccount(this);
        return this;
    }

    public UserAccount linkToRequiredInfo(RequiredInfo requiredInfo) {
        this.requiredInfo = requiredInfo;
        requiredInfo.linkToUserAccount(this);
        return this;
    }

    public UserAccount linkToOptionalInfo(OptionalInfo optionalInfo) {
        this.optionalInfo = optionalInfo;
        optionalInfo.linkToUserAccount(this);
        return this;
    }

    public UserAccount changeNickname(String newNickname) {
        this.nickname = nicknameFormatMatching(newNickname);
        return this;
    }

    public Grade increaseAndGetGradeExp(Integer exp) {
        gradeAccumulateExp += exp;
        if (Grade.ELIXIR.getRequiredAccumulateExp() <= gradeAccumulateExp
                && gradeAccumulateExp < Grade.POWER_ELIXIR.getRequiredAccumulateExp()) {
            grade = Grade.ELIXIR;
        } else if (Grade.POWER_ELIXIR.getRequiredAccumulateExp() <= gradeAccumulateExp) {
            grade = Grade.POWER_ELIXIR;
        }
        return grade;
    }

    private String nicknameFormatMatching(String nickname) {
        if (nickname == null || !nickname.matches("^[가-힣]{2,8}$")) {
            throw CommonException.builder().errorCode(2013).httpStatus(HttpStatus.BAD_REQUEST).build();
        }
        return nickname;
    }
}
