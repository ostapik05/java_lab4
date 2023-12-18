package pizza.service.repository.stream;

import org.springframework.stereotype.Repository;
import pizza.service.entity.Pizza;
import pizza.service.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PizzaStreamRepository {
    private List<Pizza> pizzaList = new ArrayList<>();

    private Long lastUsedId = 0L;

    private Long generateId() {
        ++lastUsedId;
        return lastUsedId;
    }

    public Pizza save(final Pizza pizza) {
        pizza.setId(generateId());
        pizzaList.add(pizza);
        return pizza;
    }

    public Optional<Pizza> findById(final Long id) {
        return pizzaList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public List<Pizza> findAll() {
        return pizzaList;
    }

    public void deleteById(final Long id) {
        pizzaList = pizzaList.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
    }

    public Pizza update(final Pizza pizza) {
        Pizza savedPizza = findById(pizza.getId())
                .orElseThrow(() -> new BadRequestException(String.format("Pizza with id {%s} not found", pizza.getId())));

        return savedPizza;
    }
}

