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
    앨범.setName("Abby road");
    앨범.setPrice(150000);
    앨범.setArtist("The Beatles");
    앨범.setEtc("Greatest songs");

    상품서비스.saveItem(앨범);
    assertThat(상품서비스.findOne(1L)).isEqualTo(앨범);
  }

  @Test
  @DisplayName("상품 모두 찾기")
  public void findAllItems() {
    Album 앨범 = new Album();
    앨범.setName("Abby road");
    앨범.setPrice(150000);
    앨범.setArtist("The Beatles");
    앨범.setEtc("Greatest songs");
    상품서비스.saveItem(앨범);
    Book 책 = new Book();
    책.setName("Harris and the tortoise");
    책.setPrice(12000);
    책.setAuthor("Anonymous");
    책.setIsbn("1234-5678");
    상품서비스.saveItem(책);
    Movie 영화 = new Movie();
    영화.setName("About Time");
    영화.setPrice(8000);
    영화.setActor("Rachel McAdams");
    영화.setDirector("Richard Curtis");
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
    상품.setName("Clean Code");
    상품.setPrice(25000);
    상품.setAuthor("none");
    상품.setIsbn("12321");
    상품.setStockQuantity(100);
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
