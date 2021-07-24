package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "M")
@Getter
public class Movie extends Item{

  private String director;
  private String actor;

  public void setMovieInfo(Long id, String name, Integer price, String director, String actor, Integer quantity) {
    super.setItemInfo(id, name, price, quantity);
    this.director = director;
    this.actor = actor;
  }
}
