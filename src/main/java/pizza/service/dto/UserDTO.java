package pizza.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pizza.service.entity.Order;
import pizza.service.entity.User;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NonNull
    private String name;
    @NotNull
    private String surname;
    private BigDecimal money;
    private Boolean isActive = Boolean.TRUE;
    private Order order;

    public static UserDTO toDTO(final User user){
        final UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setMoney(user.getMoney());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setOrder(user.getOrder());

        return userDTO;

    }
}
