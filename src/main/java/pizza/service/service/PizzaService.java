package pizza.service.service;

import pizza.service.dto.PizzaDTO;
import pizza.service.entity.Pizza;

import java.util.List;

public interface PizzaService {
    PizzaDTO findDTOById(Long id);
    public Pizza findById(Long id);
    List<PizzaDTO> findAll();
    void deleteById(Long id);
    PizzaDTO save(PizzaDTO position);
    PizzaDTO update(PizzaDTO position);
}
