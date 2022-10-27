package noctem.userService.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import noctem.userService.global.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    private Long sizeId;
    private Integer qty;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyPersonalOption> myPersonalOptionList = new ArrayList<>();

    @Builder
    public Cart(Long sizeId, Integer qty) {
        this.sizeId = sizeId;
        this.qty = qty;
    }

    public Cart linkToUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        userAccount.linkToCart(this);
        return this;
    }

    public Cart linkToMyPersonalOption(MyPersonalOption myPersonalOption) {
        this.myPersonalOptionList.add(myPersonalOption);
        myPersonalOption.linkToCart(this);
        return this;
    }

    public Cart linkToMyPersonalOptionList(List<MyPersonalOption> myPersonalOptionList) {
        this.myPersonalOptionList.addAll(myPersonalOptionList);
        myPersonalOptionList.forEach(e -> e.linkToCart(this));
        return this;
    }

    public Cart changeQty(Integer qty) {
        this.qty = qty;
        return this;
    }

    public Cart plusQty(Integer qty) {
        this.qty += qty;
        return this;
    }
}
