package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "B")
@Getter
public class Book extends Item{

  private String author;
  private String isbn;

  public void setBookInfo(Long id, String name, Integer price, String author, String isbn,
      Integer quantity) {
    super.setItemInfo(id, name, price, quantity);
    this.author = author;
    this.isbn = isbn;
  }
}
