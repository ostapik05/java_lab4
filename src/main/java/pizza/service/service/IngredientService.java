package pizza.service.service;

import pizza.service.dto.IngredientDTO;
import pizza.service.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    IngredientDTO findDTOById(Long id);
    List<IngredientDTO> findAll();
    void deleteById(Long id);
    IngredientDTO save(IngredientDTO order);
    IngredientDTO update(IngredientDTO order);
    Ingredient findById(Long toppingId);
}
