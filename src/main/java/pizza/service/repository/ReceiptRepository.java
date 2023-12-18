package pizza.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pizza.service.entity.Order;
import pizza.service.entity.Receipt;
import pizza.service.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

}
