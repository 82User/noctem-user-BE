package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncreaseUserExpKafkaDto {
    @Min(1)
    private Long userAccountId;
    @Min(0)
    private Integer purchaseTotalPrice;
}
