package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.dto.UserDtoDetails;
import by.academy.pharmacy2.entity.UserEntity;
import by.academy.pharmacy2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static by.academy.pharmacy2.entity.Constant.USER_WITH_THIS_LOGIN_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByLogin(username);
        if (optionalUserEntity.isPresent()) {
            return new UserDtoDetails(modelMapper.map(optionalUserEntity.get(), UserDTO.class));
        }
        throw new UsernameNotFoundException(USER_WITH_THIS_LOGIN_NOT_EXIST);
    }
}
