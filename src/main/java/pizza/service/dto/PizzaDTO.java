package pizza.service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pizza.service.entity.Ingredient;
import pizza.service.entity.Pizza;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaDTO {
    @NotNull
    private Long id;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String name;
    private List<Ingredient> ingredients;
    @NotNull
    private Boolean isActive = Boolean.TRUE;

    public static PizzaDTO toDTO(final Pizza pizza) {
        final PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setId(pizza.getId());
        pizzaDTO.setName(pizza.getName());
        pizzaDTO.setPrice(pizza.getPrice());
        pizzaDTO.setIsActive(pizza.getIsActive());
        pizzaDTO.setIngredients(pizza.getIngredients());
        return pizzaDTO;
    }
    public static Pizza toNormal(final PizzaDTO pizzaDTO) {
        final Pizza pizza = new Pizza();
        pizza.setId(pizzaDTO.getId());
        pizza.setName(pizzaDTO.getName());
        pizza.setPrice(pizzaDTO.getPrice());
        pizza.setIsActive(pizzaDTO.getIsActive());
        pizza.setIngredients(pizzaDTO.getIngredients());
        return pizza;
    }
}
