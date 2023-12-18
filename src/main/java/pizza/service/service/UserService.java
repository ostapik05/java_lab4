package pizza.service.service;

import pizza.service.dto.UserDTO;

import java.math.BigDecimal;
import java.util.List;


public interface UserService {
    UserDTO findDTOById(Long id);
    List<UserDTO> findAll();
    UserDTO save(UserDTO user);
    void deleteById(Long id);
    UserDTO update(UserDTO user);
    void addMoney(Long userId, BigDecimal money);
    void removeMoney(Long userId, BigDecimal money);

    UserDTO searchByNameAndSurname(String name, String surname);

}
