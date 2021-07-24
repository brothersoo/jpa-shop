package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

  @Autowired ItemService 상품서비스;

  @Test
  @DisplayName("상품 저장 - 앨범")
  public void saveAlmum() {
    Album 앨범 = new Album();
    앨범.setAlbumInfo(null, "Abby road", 150000, "The Beatles", "Greatest songs", 100);

    상품서비스.saveItem(앨범);
    assertThat(상품서비스.findOne(1L)).isEqualTo(앨범);
  }

  @Test
  @DisplayName("상품 모두 찾기")
  public void findAllItems() {
    Album 앨범 = new Album();
    앨범.setAlbumInfo(null, "Abby road", 150000, "The Beatles", "Greatest songs", 100);
    상품서비스.saveItem(앨범);
    Book 책 = new Book();
    책.setBookInfo(null, "Harris and the tortoise", 12000, "Anonymous", "1234-5678", 2000);
    상품서비스.saveItem(책);
    Movie 영화 = new Movie();
    영화.setMovieInfo(null, "About Time", 8000, "Rachel McAdams", "Richard Curtis", 1000000);
    상품서비스.saveItem(영화);

    List<Item> 상품리스트 = 상품서비스.findAll();
    assertThat(상품리스트.size()).isEqualTo(3);
    assertThat(상품리스트.contains(앨범)).isTrue();
    assertThat(상품리스트.contains(책)).isTrue();
    assertThat(상품리스트.contains(영화)).isTrue();
  }

  @Test
  @DisplayName("상품 정보 변경")
  public void changeItemInfo() {
    Book 상품 = new Book();
    상품.setBookInfo(null, "Clean Code", 25000, "none", "12321", 100);
    상품서비스.saveItem(상품);

    상품서비스.updateItem(상품.getId(), "Changed", 0, "Changed", "Changed", 0);

    Book 검색상품 = (Book) 상품서비스.findOne(상품.getId());
    assertThat(검색상품.getName()).isEqualTo("Changed");
    assertThat(검색상품.getPrice()).isZero();
    assertThat(검색상품.getAuthor()).isEqualTo("Changed");
    assertThat(검색상품.getIsbn()).isEqualTo("Changed");
    assertThat(검색상품.getStockQuantity()).isZero();
  }
}
