package noctem.userService.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import noctem.userService.global.common.BaseEntity;
import noctem.userService.global.enumeration.Amount;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPersonalOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_personal_option_id")
    private Long id;
    private Long personalOptionId;
    @Enumerated(EnumType.STRING)
    private Amount amount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_menu_id")
    private MyMenu myMenu;

    @Builder
    public MyPersonalOption(Long personalOptionId, Amount amount) {
        this.personalOptionId = personalOptionId;
        this.amount = amount;
    }

    public MyPersonalOption linkToMyMenu(MyMenu myMenu) {
        this.myMenu = myMenu;
        return this;
    }

    public MyPersonalOption linkToCart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public MyPersonalOption changeAmount(Amount amount) {
        this.amount = amount;
        return this;
    }
}
