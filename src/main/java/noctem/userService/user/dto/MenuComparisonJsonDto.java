package noctem.userService.user.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.AppConfig;
import noctem.userService.user.dto.request.AddCartReqDto;
import noctem.userService.user.dto.request.AddMyMenuReqDto;
import noctem.userService.user.domain.entity.Cart;
import noctem.userService.user.domain.entity.MyMenu;
import noctem.userService.global.common.CommonException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
public class MenuComparisonJsonDto {
    private Long sizeId;
    private List<UserStaticDto.PersonalOptionReqDto> personalOptionList;

    public String addCartReqDtoToJson(AddCartReqDto dto) {
        this.sizeId = dto.getSizeId();
        this.personalOptionList = dto.getPersonalOptionList();
        sortPersonalOptionList();
        return toJson();
    }

    public String addMyMenuReqDtoToJson(AddMyMenuReqDto dto) {
        this.sizeId = dto.getSizeId();
        this.personalOptionList = dto.getPersonalOptionList();
        sortPersonalOptionList();
        return toJson();
    }

    public String cartAndOptionEntityToJson(Cart cart) {
        this.sizeId = cart.getSizeId();
        this.personalOptionList = new ArrayList<>();
        cart.getMyPersonalOptionList()
                .forEach(e -> this.personalOptionList.add(new UserStaticDto.PersonalOptionReqDto(e)));
        sortPersonalOptionList();
        return toJson();
    }

    public String myMenuAndOptionEntityToJson(MyMenu myMenu) {
        this.sizeId = myMenu.getSizeId();
        this.personalOptionList = new ArrayList<>();
        myMenu.getMyPersonalOptionList()
                .forEach(e -> this.personalOptionList.add(new UserStaticDto.PersonalOptionReqDto(e)));
        sortPersonalOptionList();
        return toJson();
    }

    public String toJson() {
        try {
            sortPersonalOptionList();
            return AppConfig.objectMapper().writeValueAsString(this);
        } catch (JsonProcessingException exception) {
            log.warn("It's a problem with the json parsing.");
            throw CommonException.builder().errorCode(2022).build();
        }
    }

    public void sortPersonalOptionList() {
        this.personalOptionList.sort(Comparator.comparingLong(UserStaticDto.PersonalOptionReqDto::getOptionId));
    }
}
