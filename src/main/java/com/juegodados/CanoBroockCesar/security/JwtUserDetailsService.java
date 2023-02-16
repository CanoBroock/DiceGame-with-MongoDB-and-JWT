package com.juegodados.CanoBroockCesar.security;

import com.juegodados.CanoBroockCesar.model.repository.PlayerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//En esta clase personalizaremos la forma predeterminada de seguridad de Spring para obtener usuarios mediante
// la implementación de la interfaz UserDetailsService.
@Service
public class JwtUserDetailsService implements UserDetailsService {

    final PlayerRepository playerRepository;

    public JwtUserDetailsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.juegodados.CanoBroockCesar.model.domain.PlayerEntity playerEntity = playerRepository.findUserByUsername(username);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
        return new User(playerEntity.getUserName(), playerEntity.getPassword(), authorityList);
    }
}
