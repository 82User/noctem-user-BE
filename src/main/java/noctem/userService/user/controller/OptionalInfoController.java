package noctem.userService.user.controller;

import lombok.RequiredArgsConstructor;
import noctem.userService.user.service.UserService;
import noctem.userService.global.common.CommonResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${global.api.base-path}/optionalInfo")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class OptionalInfoController {
    private final UserService userService;

    @GetMapping("")
    public CommonResponse getOptionalInfo() {
        return CommonResponse.builder()
                .data(userService.getAllOptionalInfo())
                .build();
    }

    @GetMapping("/darkmode")
    public CommonResponse isDarkmode() {
        return CommonResponse.builder()
                .data(userService.isDarkmode())
                .build();
    }

    @GetMapping("/orderHome")
    public CommonResponse getOrderMyMenuFromHome() {
        return CommonResponse.builder()
                .data(userService.getOrderMyMenuFromHome())
                .build();
    }

    @PatchMapping("/darkmode")
    public CommonResponse changeDarkmode() {
        return CommonResponse.builder()
                .data(userService.changeDarkmode())
                .build();
    }

    @PatchMapping("/orderHome")
    public CommonResponse changeOrderMyMenuFromHome() {
        return CommonResponse.builder()
                .data(userService.changeOrderMyMenuFromHome())
                .build();
    }

    @PatchMapping("/pushNotification")
    public CommonResponse changePushNotificationAgreement() {
        return CommonResponse.builder()
                .data(userService.changePushNotificationAgreement())
                .build();
    }

    @PatchMapping("/advertisement")
    public CommonResponse changeAdvertisementAgreement() {
        return CommonResponse.builder()
                .data(userService.changeAdvertisementAgreement())
                .build();
    }

    @PatchMapping("/location")
    public CommonResponse changeUseLocationInfoAgreement() {
        return CommonResponse.builder()
                .data(userService.changeUseLocationInfoAgreement())
                .build();
    }

    @PatchMapping("/shakeToPay")
    public CommonResponse changeShakeToPay() {
        return CommonResponse.builder()
                .data(userService.changeShakeToPay())
                .build();
    }
}
