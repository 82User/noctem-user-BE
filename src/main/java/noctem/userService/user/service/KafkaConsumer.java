package noctem.userService.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.AppConfig;
import noctem.userService.global.enumeration.Grade;
import noctem.userService.user.domain.entity.UserAccount;
import noctem.userService.user.domain.repository.UserAccountRepository;
import noctem.userService.user.dto.request.IncreaseUserExpKafkaDto;
import noctem.userService.user.dto.vo.SignUpVo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class KafkaConsumer {
    private final String STORE_TO_USER_GRADE_EXP_TOPIC = "store-to-user-grade-exp";
    private final String SIGN_UP_EMAIL_TOPIC = "sign-up-email";
    private final UserAccountRepository userAccountRepository;
    private final JavaMailSender mailSender;

    @KafkaListener(topics = STORE_TO_USER_GRADE_EXP_TOPIC)
    public void increaseUserExp(String userExpDtoAsJson) {
        log.info("Receive userExpDto through [{}] TOPIC", STORE_TO_USER_GRADE_EXP_TOPIC);
        try {
            IncreaseUserExpKafkaDto userExpDto = AppConfig.objectMapper()
                    .readValue(userExpDtoAsJson, IncreaseUserExpKafkaDto.class);

            UserAccount userAccount = userAccountRepository.getById(userExpDto.getUserAccountId());
            Grade beforeGrade = userAccount.getGrade();
            Grade nowGrade = userAccount.increaseAndGetGradeExp(userExpDto.getPurchaseTotalPrice());
            if (beforeGrade != nowGrade) {
                // 등급업 푸시알림
            }
        } catch (JsonMappingException e) {
            log.warn("Occurred JsonMappingException. [{}]", KafkaConsumer.class.getSimpleName());
        } catch (JsonProcessingException e) {
            log.warn("Occurred JsonProcessingException. [{}]", KafkaConsumer.class.getSimpleName());
        }
    }

    @KafkaListener(topics = SIGN_UP_EMAIL_TOPIC)
    public void signUpEmail(String signUpVoJson) {
        try {
            SignUpVo signUpVo = AppConfig.objectMapper()
                    .readValue(signUpVoJson, SignUpVo.class);

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(signUpVo.getEmail());
            email.setSubject("Cafe Noctem 회원가입을 축하드립니다.");
            email.setText(String.format("%s님 Cafe Noctem 회원가입을 축하드립니다.", signUpVo.getNickname()));

            mailSender.send(email);
        } catch (JsonMappingException e) {
            log.warn("Occurred JsonMappingException. [{}]", KafkaConsumer.class.getSimpleName());
        } catch (JsonProcessingException e) {
            log.warn("Occurred JsonProcessingException. [{}]", KafkaConsumer.class.getSimpleName());
        }
    }
}
