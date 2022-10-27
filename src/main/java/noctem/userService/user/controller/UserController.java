package noctem.userService.user.controller;

import lombok.RequiredArgsConstructor;
import noctem.userService.user.dto.request.ChangeNicknameReqDto;
import noctem.userService.user.dto.request.SignUpReqDto;
import noctem.userService.user.dto.response.RankingResDto;
import noctem.userService.user.service.UserService;
import noctem.userService.global.common.CommonResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${global.api.base-path}")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public CommonResponse signUp(@Validated @RequestBody SignUpReqDto dto) {
        return CommonResponse.builder()
                .data(userService.signUp(dto))
                .build();
    }

    @GetMapping("/duplicationCheck/email/{email}")
    public CommonResponse duplCheckEmail(@PathVariable String email) {
        return CommonResponse.builder()
                .data(userService.duplCheckEmail(email))
                .build();
    }

    @GetMapping("/duplicationCheck/nickname/{nickname}")
    public CommonResponse duplCheckNickname(@PathVariable String nickname) {
        return CommonResponse.builder()
                .data(userService.duplCheckNickname(nickname))
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/userAccount")
    public CommonResponse getUserAccountAndPrivacy() {
        return CommonResponse.builder()
                .data(userService.getPrivacyInfo())
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/userAccount/nickname")
    public CommonResponse changeNickname(@Validated @RequestBody ChangeNicknameReqDto dto) {
        return CommonResponse.builder()
                .data(userService.changeNickname(dto))
                .build();
    }

    @GetMapping("/userAccount/grade")
    public CommonResponse getGradeAndRemainingExp() {
        return CommonResponse.builder()
                .data(userService.getGradeAndRemainingExp())
                .build();
    }

    @GetMapping("/userAccount/info")
    public CommonResponse getPurchaseUserAccountInfo() {
        return CommonResponse.builder()
                .data(userService.getPurchaseUserAccountInfo())
                .build();
    }

    @GetMapping("/ranking")
    public CommonResponse ranking() {
        List<RankingResDto> dtoList = userService.getRanking();
        dtoList.forEach(e -> e.setIndex(dtoList.indexOf(e)));
        return CommonResponse.builder()
                .data(dtoList)
                .build();
    }
}
