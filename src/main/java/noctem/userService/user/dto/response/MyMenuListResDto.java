package noctem.userService.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import noctem.userService.user.domain.entity.MyMenu;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MyMenuListResDto {
    private Integer index;
    private Long myMenuId;
    private String alias;
    private Long sizeId;
    private List<String> myPersonalOptionList;

    public MyMenuListResDto(MyMenu myMenu) {
        this.myMenuId = myMenu.getId();
        this.alias = myMenu.getAlias();
        this.sizeId = myMenu.getSizeId();
        this.myPersonalOptionList = new ArrayList<>();
    }
}
