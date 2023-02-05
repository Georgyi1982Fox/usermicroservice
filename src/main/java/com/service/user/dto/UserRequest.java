package com.service.user.dto;

import com.service.user.persistence.model.Role;
import lombok.*;
import java.util.List;
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserRequest{
    private String username;
    private String password;
    private String email;
    private List<Role> roles;
}
