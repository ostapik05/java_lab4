package pizza.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pizza.service.entity.Ingredient;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Boolean isActive = Boolean.TRUE;

    public static IngredientDTO toDTO(final Ingredient ingredient){
        final IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(ingredient.getId());
        ingredientDTO.setName(ingredient.getName());
        ingredientDTO.setPrice(ingredient.getPrice());
        ingredientDTO.setIsActive(ingredient.getIsActive());
        return ingredientDTO;
    }
}
