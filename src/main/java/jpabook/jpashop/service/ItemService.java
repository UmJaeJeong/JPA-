package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional//Overriding으로 readOnly를 풀어줌
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    //매개변수 방식
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        /*findItem.setName(form.getName());
        findItem.setPrice(form.getPrice());
        findItem.setStockQuantity(form.getStockQuantity());*/

        //유지보수성을 높이기 위해서 set을 사용하지 않는것이 좋다
        //별도의 Method를 생성하여 변경하는것이 좋은방법이다.
        //JPA의 특성인 영속성으로 인해 find로 찾은 객체의 데이터값을 변경했을시에는
        //dirty checking으로 인해 플래쉬단계에서 변경된값을 저장하며 commit을 해준다.
        findItem.change(name,price,stockQuantity);

    }

    //기존 Form방식
    @Transactional
    public void updateItem(Long itemId, Book form) {
        Item findItem = itemRepository.findOne(itemId);
        /*findItem.setName(form.getName());
        findItem.setPrice(form.getPrice());
        findItem.setStockQuantity(form.getStockQuantity());*/

        //유지보수성을 높이기 위해서 set을 사용하지 않는것이 좋다
        //별도의 Method를 생성하여 변경하는것이 좋은방법이다.
        //JPA의 특성인 영속성으로 인해 find로 찾은 객체의 데이터값을 변경했을시에는
        //dirty checking으로 인해 플래쉬단계에서 변경된값을 저장하며 commit을 해준다.
        findItem.change(form.getName(),form.getPrice(), form.getStockQuantity());

    }

    //DTO방식
    @Transactional
    public void updateItem(Long itemId, UpdateItemDTO updateDTO) {
        Item findItem = itemRepository.findOne(itemId);
        /*findItem.setName(form.getName());
        findItem.setPrice(form.getPrice());
        findItem.setStockQuantity(form.getStockQuantity());*/

        //유지보수성을 높이기 위해서 set을 사용하지 않는것이 좋다
        //별도의 Method를 생성하여 변경하는것이 좋은방법이다.
        //JPA의 특성인 영속성으로 인해 find로 찾은 객체의 데이터값을 변경했을시에는
        //dirty checking으로 인해 플래쉬단계에서 변경된값을 저장하며 commit을 해준다.
        findItem.change(updateDTO.getName(),updateDTO.getPrice(), updateDTO.getStockQuantity());

    }


    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
