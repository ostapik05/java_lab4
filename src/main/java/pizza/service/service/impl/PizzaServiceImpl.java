package pizza.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pizza.service.dto.PizzaDTO;
import pizza.service.entity.Pizza;
import pizza.service.exceptions.BadRequestException;
import pizza.service.repository.PizzaRepository;
import pizza.service.service.PizzaService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository pizzaRepository;
    @Override
    public PizzaDTO findDTOById(Long id) {
        Pizza pizza = findById(id);
        return PizzaDTO.toDTO(pizza);
    }

    public Pizza findById(Long id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Pizza with id {%s} not found", id)));
    }

    @Override
    public List<PizzaDTO> findAll() {
        return pizzaRepository.findAll().stream()
                .map(PizzaDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Pizza pizza = findById(id);
        pizzaRepository.delete(pizza);
    }

    @Override
    public PizzaDTO save(PizzaDTO pizzaDTO) {
        Pizza pizza = new Pizza();
        pizza.setName(pizzaDTO.getName());
        pizza.setIsActive(pizzaDTO.getIsActive());
        pizza.setIngredients(pizzaDTO.getIngredients());
        pizza.setPrice(pizzaDTO.getPrice());
        pizzaRepository.save(pizza);
        return PizzaDTO.toDTO(pizza);
    }

    public PizzaDTO save(Pizza pizza) {
        Pizza pizzaDb = new Pizza();
        pizza.setName(pizzaDb.getName());
        pizza.setIsActive(pizzaDb.getIsActive());
        pizza.setIngredients(pizzaDb.getIngredients());
        pizza.setPrice(pizzaDb.getPrice());
        pizzaRepository.save(pizzaDb);
        return PizzaDTO.toDTO(pizzaDb);
    }

    @Override
    public PizzaDTO update(PizzaDTO pizzaDTO) {
        if (pizzaDTO.getId() == null) {
            throw new BadRequestException("Id can't be null");
        }

        final Pizza savedPizza = findById(pizzaDTO.getId());
        savedPizza.setName(pizzaDTO.getName());
        savedPizza.setPrice(pizzaDTO.getPrice());
        savedPizza.setIsActive(pizzaDTO.getIsActive());
        savedPizza.setIngredients(pizzaDTO.getIngredients());
        pizzaRepository.save(savedPizza);

        return PizzaDTO.toDTO(savedPizza);
    }

}
