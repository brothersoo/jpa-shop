package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "A")
@Getter
public class Album extends Item{

  private String artist;
  private String etc;

  public void setAlbumInfo(Long id, String name, Integer price, String artist, String etc, Integer quantity) {
    super.setItemInfo(id, name, price, quantity);
    this.artist = artist;
    this.etc = etc;
  }
}
