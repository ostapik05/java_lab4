package pizza.service.repository.stream;

import org.springframework.stereotype.Repository;
import pizza.service.entity.Ingredient;
import pizza.service.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class IngredientStreamRepository {
    private List<Ingredient> ingredientList = new ArrayList<>();

    private Long lastUsedId = 0L;

    private Long generateId() {
        ++lastUsedId;
        return lastUsedId;
    }

    public Ingredient save(final Ingredient ingredient) {
        ingredient.setId(generateId());
        ingredientList.add(ingredient);
        return ingredient;
    }

    public Optional<Ingredient> findById(final Long id) {
        return ingredientList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public List<Ingredient> findAll() {
        return ingredientList;
    }

    public void deleteById(final Long id) {
        ingredientList = ingredientList.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
    }

    public Ingredient update(final Ingredient ingredient) {
        Ingredient savedIngredient = findById(ingredient.getId())
                .orElseThrow(() -> new BadRequestException(String.format("Ingredient with id {%s} not found", ingredient.getId())));

        return savedIngredient;
    }
}

