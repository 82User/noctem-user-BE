package noctem.userService.global.security.auth;

import lombok.Getter;
import noctem.userService.user.domain.entity.UserAccount;
import noctem.userService.global.security.dto.ClientInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private String email;
    private String password;
    private final ClientInfoDto clientInfoDto;
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(UserAccount userAccount, Collection<? extends GrantedAuthority> authorities) {
        this.id = userAccount.getId();
        this.email = userAccount.getEmail();
        this.password = userAccount.getPassword();
        this.authorities = (List<GrantedAuthority>) authorities;
        this.clientInfoDto = ClientInfoDto.builder()
                .userAccountId(userAccount.getId())
                .nickname(userAccount.getNickname())
                .email(userAccount.getEmail())
                .role(userAccount.getRole())
                .build();
    }

    public UserDetailsImpl(ClientInfoDto clientInfoDto, Collection<? extends GrantedAuthority> authorities) {
        this.id = clientInfoDto.getUserAccountId();
        this.clientInfoDto = clientInfoDto;
        this.authorities = (List<GrantedAuthority>) authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
