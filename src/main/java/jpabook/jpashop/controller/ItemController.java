package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.UpdateItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createFrom(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm bookForm) {
        Book book = new Book();
        //createBook  메서드를 만들어서 하는게 좋은 설계
        //setter를 없애는게 좋은 설계
        book.setName(bookForm.getName());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
        book.setPrice(bookForm.getPrice());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book one = (Book)itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setAuthor(one.getAuthor());
        form.setName(one.getName());
        form.setIsbn(one.getIsbn());
        form.setStockQuantity(one.getStockQuantity());
        form.setPrice(one.getPrice());
        form.setId(one.getId());

        model.addAttribute("form", form);
        return "items/updateItemForm";

    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form) {

        //준영속성 객체
        /*Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());*/

        UpdateItemDTO updateItemDTO = new UpdateItemDTO();
        updateItemDTO.setName(form.getName());
        updateItemDTO.setPrice(form.getPrice());
        updateItemDTO.setStockQuantity(form.getStockQuantity());
        //보통 form같은 경우는 web계층에서만 사용하기 위해서 정의 해놓은것이므로
        // 1) 값들을 form에 저장하여 전달하는것 보다 "매개변수"로 등록하여 전달하는 방법이 좋다
        // 2) form을 그대로 사용하는 방법
        // 3) 매개변수 전달  이외에 방버에는 DTO를 생성하여 전달하는 것도 좋은 방법이다(매개변수가 많을 경우) Data Transform Object

// 1)방법       itemService.updateItem(form.getId(),form.getName(),form.getPrice(), form.getStockQuantity());
// 2)방법       itemService.updateItem(form.getId(),book);

        itemService.updateItem(form.getId(), updateItemDTO);



        return "redirect:/items";
    }
}
