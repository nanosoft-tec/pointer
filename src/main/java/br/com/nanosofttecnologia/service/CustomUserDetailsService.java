package br.com.nanosofttecnologia.service;

import br.com.nanosofttecnologia.dto.UserDetailDTO;
import br.com.nanosofttecnologia.model.User;
import br.com.nanosofttecnologia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

    User user =
        userRepository
            .findByUsername(login)
            .orElseThrow(() -> new UsernameNotFoundException("invalid.credencials"));
    return new UserDetailDTO(user);
  }
}
