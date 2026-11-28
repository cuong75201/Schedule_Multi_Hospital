package com.carepath.configuration;

import java.util.Collection;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.carepath.entities.PlatformAdminEntity;
import com.carepath.entities.UserAccountEntity;
import com.carepath.entities.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {
    private UserAccountEntity userAccountEntity;
    private PlatformAdminEntity adminEntity;
    private AccountType accountType;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public @Nullable String getPassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.userAccountEntity.getUsername();
    }

    public String getAdminUsername() {
        return this.adminEntity.getUsername();
    }

    public String getAccountType() {
        return this.accountType.name();
    }

    public UUID getAccountId() {
    return switch (accountType) {
        case USER -> userAccountEntity.getId();
        case SUPERADMIN -> adminEntity.getId();
    };
}

}
