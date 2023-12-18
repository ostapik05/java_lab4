package pizza.service.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pizza.service.dto.PizzaDTO;
import pizza.service.service.PizzaService;

import java.util.List;

@RestController
@RequestMapping("/api/pizza")
@RequiredArgsConstructor
public class PizzaResource {
    private final PizzaService pizzaService;

    @GetMapping("/{id}")
    public PizzaDTO findPizzaById(final @PathVariable Long id) {
        return pizzaService.findDTOById(id);
    }

    @GetMapping
    public List<PizzaDTO> findAllPizzas() {
        return pizzaService.findAll();
    }

    @PostMapping
    public PizzaDTO createPizza(final @RequestBody @Valid PizzaDTO pizzaDTO) {
        return pizzaService.save(pizzaDTO);
    }

    @PutMapping
    public PizzaDTO updatePizza(final @RequestBody PizzaDTO pizza) {
        return pizzaService.update(pizza);
    }

    @DeleteMapping("/{id}")
    public void deleteById(final @PathVariable Long id) {
        pizzaService.deleteById(id);
    }
}
