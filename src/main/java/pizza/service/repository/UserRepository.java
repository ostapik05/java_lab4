package pizza.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizza.service.entity.User;
import pizza.service.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByNameAndSurname(String name, String surname);
    List<User> findAllByIsActiveTrue();

}
