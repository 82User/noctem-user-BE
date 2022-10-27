package noctem.userService.user.dto.response;

import lombok.Data;
import noctem.userService.user.domain.entity.OptionalInfo;

@Data
public class OptionalInfoResDto {
    private Boolean isDarkmode;
    private Boolean pushNotificationAgreement;
    private Boolean advertisementAgreement;
    private Boolean useLocationInfoAgreement;
    private Boolean shakeToPay;

    public OptionalInfoResDto(OptionalInfo optionalInfo) {
        this.isDarkmode = optionalInfo.getIsDarkmode();
        this.pushNotificationAgreement = optionalInfo.getPushNotificationAgreement();
        this.advertisementAgreement = optionalInfo.getAdvertisementAgreement();
        this.useLocationInfoAgreement = optionalInfo.getUseLocationInfoAgreement();
        this.shakeToPay = optionalInfo.getShakeToPay();
    }
}
