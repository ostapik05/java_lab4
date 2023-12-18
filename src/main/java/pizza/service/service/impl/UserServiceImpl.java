package pizza.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pizza.service.dto.UserDTO;
import pizza.service.entity.User;
import pizza.service.exceptions.BadRequestException;
import pizza.service.repository.UserRepository;
import pizza.service.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO findDTOById(final Long id) {
        final User user = findById(id);

        return UserDTO.toDTO(user);
    }

    private User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("User with id {%s} not found", id)));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAllByIsActiveTrue().stream()
                .map(e -> UserDTO.toDTO(e))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            throw new BadRequestException("Id can not be null");
        }
        final User savedUser = findById(userDTO.getId());
        savedUser.setName(userDTO.getName());
        savedUser.setSurname(userDTO.getSurname());
        userRepository.save(savedUser);

        return UserDTO.toDTO(savedUser);
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        user.setIsActive(Boolean.FALSE);
        userRepository.save(user);
    }


    @Override
    public UserDTO save(final UserDTO userDTO) {
        final User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        userRepository.save(user);

        return UserDTO.toDTO(user);
    }

    @Override
    public void addMoney(Long userId, BigDecimal money) {
        User user = findById(userId);
        user.setMoney(user.getMoney().add(money));
        userRepository.save(user);
    }

    @Override
    public void removeMoney(Long userId, BigDecimal money) {
        User user = findById(userId);
        user.setMoney(user.getMoney().subtract(money));
        userRepository.save(user);
    }

    @Override
    public UserDTO searchByNameAndSurname(String name, String surname) {
        return userRepository.findOneByNameAndSurname(name, surname)
                .map(e -> UserDTO.toDTO(e))
                .orElseThrow(() -> new BadRequestException(String.format("User with name {%s} and surname {%s} not found", name, surname)));

    }
}
