package noctem.userService.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ChangeMyMenuOrderReqDto {
    private List<Long> myMenuIdOrderList;

    public Map<Long, Integer> generateIndexMap() {
        Map<Long, Integer> indexMap = new HashMap<>();
        myMenuIdOrderList.forEach(e -> indexMap.put(e, myMenuIdOrderList.indexOf(e)));
        return indexMap;
    }
}
