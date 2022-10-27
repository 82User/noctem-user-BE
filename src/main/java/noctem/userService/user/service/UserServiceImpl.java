package noctem.userService.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.AppConfig;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Grade;
import noctem.userService.global.security.bean.ClientInfoLoader;
import noctem.userService.user.domain.entity.OptionalInfo;
import noctem.userService.user.domain.entity.RequiredInfo;
import noctem.userService.user.domain.entity.UserAccount;
import noctem.userService.user.domain.entity.UserPrivacy;
import noctem.userService.user.domain.repository.OptionalInfoRepository;
import noctem.userService.user.domain.repository.UserAccountRepository;
import noctem.userService.user.domain.repository.UserPrivacyRepository;
import noctem.userService.user.dto.request.ChangeNicknameReqDto;
import noctem.userService.user.dto.request.SignUpReqDto;
import noctem.userService.user.dto.response.*;
import noctem.userService.user.dto.vo.SignUpVo;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final String SIGN_UP_EMAIL_TOPIC = "sign-up-email";
    private final UserAccountRepository userAccountRepository;
    private final UserPrivacyRepository userPrivacyRepository;
    private final OptionalInfoRepository optionalInfoRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PasswordEncoder passwordEncoder;
    private final ClientInfoLoader clientInfoLoader;

    @Override
    public Boolean signUp(SignUpReqDto dto) {
        // dto validate
        dto.dataformatMatching();

        UserAccount userAccount = UserAccount.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();

        UserPrivacy userPrivacy = UserPrivacy.builder()
                .name(dto.getName())
                .birthdayYear(dto.getRrnFront().substring(0, 2))
                .birthdayMonth(dto.getRrnFront().substring(2, 4))
                .birthdayDay(dto.getRrnFront().substring(4))
                .rrnBackFirst(dto.getRrnBackFirst())
                .build();

        RequiredInfo requiredInfo = RequiredInfo.builder()
                .termsOfServiceAgreement(dto.getTermsOfServiceAgreement())
                .personalInfoAgreement(dto.getPersonalInfoAgreement())
                .build();

        OptionalInfo optionalInfo = OptionalInfo.builder()
                .advertisementAgreement(dto.getAdvertisementAgreement())
                .build();

        userAccount.linkToUserPrivacy(userPrivacy)
                .linkToRequiredInfo(requiredInfo)
                .linkToOptionalInfo(optionalInfo);

        userAccountRepository.save(userAccount);
        try {
            String signUpVoJson = AppConfig.objectMapper().writeValueAsString(new SignUpVo(dto.getNickname(), dto.getEmail()));
            kafkaTemplate.send(SIGN_UP_EMAIL_TOPIC, signUpVoJson);
        } catch (JsonProcessingException e) {
            throw CommonException.builder().errorCode(2027).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean duplCheckEmail(String email) {
        return userAccountRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean duplCheckNickname(String nickname) {
        return userAccountRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    @Override
    public UserPrivacyInfoResDto getPrivacyInfo() {
        UserPrivacy userPrivacy = userPrivacyRepository.findByUserAccountId(clientInfoLoader.getUserAccountId());
        return new UserPrivacyInfoResDto(userPrivacy.getUserAccount(), userPrivacy).applyMasking();
    }

    @Override
    public Boolean changeNickname(ChangeNicknameReqDto dto) {
        userAccountRepository.findById(clientInfoLoader.getUserAccountId()).get()
                .changeNickname(dto.getNickname());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public OptionalInfoResDto getAllOptionalInfo() {
        return new OptionalInfoResDto(optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId()));
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean isDarkmode() {
        return optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId())
                .getIsDarkmode();
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean getOrderMyMenuFromHome() {
        return optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId())
                .getOrderMyMenuFromHome();
    }

    @Override
    public Boolean changePushNotificationAgreement() {
        optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId())
                .changePushNotificationAgreement();
        return true;
    }

    @Override
    public Boolean changeAdvertisementAgreement() {
        optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId())
                .changeAdvertisementAgreement();
        return true;
    }

    @Override
    public Boolean changeUseLocationInfoAgreement() {
        optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId())
                .changeUseLocationInfoAgreement();
        return true;
    }

    @Override
    public Boolean changeShakeToPay() {
        optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId())
                .changeShakeToPay();
        return true;
    }

    @Override
    public Boolean changeDarkmode() {
        optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId())
                .changeDarkmode();
        return true;
    }

    @Override
    public Boolean changeOrderMyMenuFromHome() {
        optionalInfoRepository.findByUserAccountId(clientInfoLoader.getUserAccountId())
                .changeOrderMyMenuFromHome();
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public GradeAndRemainingExpResDto getGradeAndRemainingExp() {
        if (clientInfoLoader.isAnonymous()) {
            throw CommonException.builder().errorCode(2001).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        UserAccount userAccount = userAccountRepository.findById(clientInfoLoader.getUserAccountId()).get();
        String userGrade = userAccount.getGrade().getValue();
        Integer userExp;
        String nextGrade = null;
        Integer requiredExpToNextGrade = null;
        // 경험치 계산
        if (userAccount.getGrade() == Grade.POTION) {
            nextGrade = Grade.ELIXIR.getValue();
            userExp = userAccount.getGradeAccumulateExp() / Grade.divisionRatio;
            requiredExpToNextGrade = Grade.ELIXIR.getRequiredAccumulateExp() / Grade.divisionRatio;

        } else if (userAccount.getGrade() == Grade.ELIXIR) {
            nextGrade = Grade.POWER_ELIXIR.getValue();
            userExp = (userAccount.getGradeAccumulateExp() - Grade.ELIXIR.getRequiredAccumulateExp()) / Grade.divisionRatio;
            requiredExpToNextGrade = (Grade.POWER_ELIXIR.getRequiredAccumulateExp() - Grade.ELIXIR.getRequiredAccumulateExp()) / Grade.divisionRatio;
        } else {
            userExp = (userAccount.getGradeAccumulateExp() - Grade.POWER_ELIXIR.getRequiredAccumulateExp()) / Grade.divisionRatio;
        }

        return new GradeAndRemainingExpResDto(userGrade, userExp, nextGrade, requiredExpToNextGrade);
    }

    @Transactional(readOnly = true)
    @Override
    public UserAccountInfoResDto getPurchaseUserAccountInfo() {
        UserPrivacy userPrivacy = userPrivacyRepository.findByUserAccountId(clientInfoLoader.getUserAccountId());
        return new UserAccountInfoResDto(userPrivacy.getTodayUserAge(), userPrivacy.getSex().getValue());
    }

    @Override
    public void updateLastAccessTime(Long userAccountId) {
        userAccountRepository.findById(userAccountId).get().updateLastAccessTime();
    }

    @Override
    public List<RankingResDto> getRanking() {
        List<RankingResDto> dtoList = userAccountRepository.findTop100ByOrderByGradeAccumulateExpDesc()
                .stream().map(RankingResDto::new).collect(Collectors.toList());
        dtoList.forEach(e -> e.setRank(dtoList.indexOf(e) + 1));
        return dtoList;
    }
}
