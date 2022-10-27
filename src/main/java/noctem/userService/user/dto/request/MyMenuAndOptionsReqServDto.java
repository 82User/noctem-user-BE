package noctem.userService.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyMenuAndOptionsReqServDto {
    @Min(1)
    private Long sizeId;

    private List<Long> optionIdList;
}
