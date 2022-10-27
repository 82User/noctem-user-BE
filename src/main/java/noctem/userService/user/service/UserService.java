package noctem.userService.user.service;

import noctem.userService.user.dto.request.ChangeNicknameReqDto;
import noctem.userService.user.dto.request.SignUpReqDto;
import noctem.userService.user.dto.response.*;

import java.util.List;

public interface UserService {
    Boolean signUp(SignUpReqDto dto);

    Boolean duplCheckEmail(String email);

    Boolean duplCheckNickname(String nickname);

    OptionalInfoResDto getAllOptionalInfo();

    Boolean isDarkmode();

    Boolean getOrderMyMenuFromHome();

    Boolean changePushNotificationAgreement();

    Boolean changeAdvertisementAgreement();

    Boolean changeUseLocationInfoAgreement();

    Boolean changeShakeToPay();

    Boolean changeDarkmode();

    Boolean changeOrderMyMenuFromHome();

    UserPrivacyInfoResDto getPrivacyInfo();

    Boolean changeNickname(ChangeNicknameReqDto dto);

    GradeAndRemainingExpResDto getGradeAndRemainingExp();

    UserAccountInfoResDto getPurchaseUserAccountInfo();

    void updateLastAccessTime(Long id);

    List<RankingResDto> getRanking();
}
