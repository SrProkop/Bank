package ru.t1.bank.mappers;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.t1.bank.dto.UserDTO;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.UserService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.getRoles().addAll(user.getRoles());
        userDTO.getAccounts().addAll(user.getAccounts().stream().map(Account::getNumber).collect(Collectors.toList()));
        return userDTO;
    }

    public User toUser(UserDTO userDTO) throws NotFoundException {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFullName(userDTO.getFullName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.getRoles().addAll(userDTO.getRoles());
        user.getAccounts().addAll(userDTO.getAccounts().stream().map(x -> {
            try {
                return accountService.findByNumber(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList()));
        User userOld = userService.findById(userDTO.getId());
        user.setPassword(userOld.getPassword());
        user.setUsername(userOld.getUsername());
        return user;
    }

    public abstract Set<UserDTO> toUserDTO(Collection<User> users);

    public abstract Set<User> toUser(Collection<UserDTO> userDTOS);

}
