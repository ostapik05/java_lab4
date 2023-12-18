package pizza.service.repository.stream;

import org.springframework.stereotype.Repository;
import pizza.service.entity.Receipt;
import pizza.service.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ReceiptStreamRepository {
    private List<Receipt> receiptList = new ArrayList<>();

    private Long lastUsedId = 0L;
    private Long generateId(){
        ++ lastUsedId;
        return lastUsedId;
    }

    public Receipt save(final Receipt receipt){
        receipt.setId(generateId());
        receiptList.add(receipt);
         return receipt;
    }

    public Optional<Receipt> findById(final Long id){
        return receiptList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public List<Receipt> findAll(){
        return receiptList;
    }

    public void deleteById(final Long id){
        receiptList = receiptList.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
    }

    public Receipt update(final Receipt receipt){

        Receipt savedReceipt = findById(receipt.getId())
                .orElseThrow(() -> new BadRequestException(String.format("Receipt with id {%s} not found", receipt.getId())));

        return savedReceipt;

    }

}
