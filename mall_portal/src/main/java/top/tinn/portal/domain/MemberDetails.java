package top.tinn.portal.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.tinn.model.UmsMember;

import java.util.Arrays;
import java.util.Collection;

/**
 * @Description MemberDetails 用户角色均为TEST 没有实现动态资源分配
 * @Author Tinn
 * @Date 2020/4/10 15:43
 */
public class MemberDetails implements UserDetails {
    @Getter
    @Setter
    private UmsMember umsMember;

    public MemberDetails(UmsMember umsMember) {
        this.umsMember = umsMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("TEST"));
    }

    @Override
    public String getPassword() {
        return umsMember.getPassword();
    }

    @Override
    public String getUsername() {
        return umsMember.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 做了改动
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return umsMember.getStatus() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
