package pizza.service.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pizza.service.dto.IngredientDTO;
import pizza.service.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientResource {
    private final IngredientService ingredientService;

    @GetMapping("/{id}")
    public IngredientDTO findById(final @PathVariable Long id) {
        return ingredientService.findDTOById(id);
    }

    @GetMapping
    public List<IngredientDTO> findAll() {
        return ingredientService.findAll();
    }

    @PostMapping
    public IngredientDTO createIngredient(final @RequestBody @Valid IngredientDTO ingredientDTO) {
        return ingredientService.save(ingredientDTO);
    }

    @PutMapping
    public IngredientDTO updateIngredient(final @RequestBody IngredientDTO ingredientDTO) {
        return ingredientService.update(ingredientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(final @PathVariable Long id) {
        ingredientService.deleteById(id);
    }


}
