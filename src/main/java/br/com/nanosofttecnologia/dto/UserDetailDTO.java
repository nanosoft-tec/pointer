package br.com.nanosofttecnologia.dto;

import br.com.nanosofttecnologia.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailDTO extends User implements UserDetails {

  public UserDetailDTO(User user) {
    this.setId(user.getId());
    this.setUsername(user.getUsername());
    this.setPassword(user.getPassword());
    this.setName(user.getName());
    this.setEmail(user.getEmail());
    this.setStatus(user.isStatus());
    this.setCreated(user.getCreated());
    this.setUpdated(user.getUpdated());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  public String getUsername() {
    return super.getUsername();
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
    return super.isStatus();
  }
}
