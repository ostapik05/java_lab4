package pizza.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pizza.service.dto.IngredientDTO;
import pizza.service.entity.Ingredient;
import pizza.service.exceptions.BadRequestException;
import pizza.service.repository.IngredientRepository;
import pizza.service.service.IngredientService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    @Override
    public IngredientDTO findDTOById(Long id) {
        Ingredient ingredient = findById(id);
        return IngredientDTO.toDTO(ingredient);
    }

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Ingredient with id {%s} not found", id)));
    }

    @Override
    public List<IngredientDTO> findAll() {
        return ingredientRepository.findAll().stream()
                .map(IngredientDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Ingredient ingredient = findById(id);
        ingredientRepository.delete(ingredient);
    }

    @Override
    public IngredientDTO save(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDTO.getName());
        ingredient.setPrice(ingredientDTO.getPrice());
        ingredient.setIsActive(ingredientDTO.getIsActive());
        ingredientRepository.save(ingredient);
        return IngredientDTO.toDTO(ingredient);
    }

    @Override
    public IngredientDTO update(IngredientDTO ingredientDTO) {
        if (ingredientDTO.getId() == null) {
            throw new BadRequestException("Id can't be null");
        }

        Ingredient savedIngredient = findById(ingredientDTO.getId());

        savedIngredient.setName(ingredientDTO.getName());
        savedIngredient.setPrice(ingredientDTO.getPrice());
        savedIngredient.setIsActive(ingredientDTO.getIsActive());
        ingredientRepository.save(savedIngredient);

        return IngredientDTO.toDTO(savedIngredient);
    }

}
