package com.game.slotmachine.model.dto;

import com.game.slotmachine.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String role;

    public UserDTO(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
