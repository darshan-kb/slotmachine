package com.game.slotmachine.service;

import com.game.slotmachine.model.dto.UserDTO;
import com.game.slotmachine.model.mapper.Mapper;
import com.game.slotmachine.repository.UserRepository;
import com.game.slotmachine.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SignUpService {
    UserRepository userRepository;
    public UserDTO signUpUser(UserDTO user){
        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
        if(dbUser.isPresent()) {
            throw new IllegalStateException("email already taken");
        }
        return new UserDTO(userRepository.save(Mapper.toUser(user)));
    }

}
