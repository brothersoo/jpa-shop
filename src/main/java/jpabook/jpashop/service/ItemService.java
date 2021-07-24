package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  @Transactional
  public void updateItem(Long id, String name, Integer price, String author, String isbn,
      Integer quantity) {
    Book item = (Book) itemRepository.findOne(id);
    item.setBookInfo(id, name, price, author, isbn, quantity);
  }

  public Item findOne(Long id) {
    return itemRepository.findOne(id);
  }

  public List<Item> findAll() {
    return itemRepository.findAll();
  }
}
