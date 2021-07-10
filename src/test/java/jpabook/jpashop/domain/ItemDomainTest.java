package jpabook.jpashop.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class ItemDomainTest {

  @Test
  @DisplayName("상품 수량 재고 증가")
  public void addStock() {
    Album 앨범 = new Album();
    int prevStockQuantity = 앨범.getStockQuantity();
    앨범.addStock(100);
    assertThat(prevStockQuantity + 100).isEqualTo(앨범.getStockQuantity());
  }

  @Test
  @DisplayName("상품 수량 재고 감소")
  public void subStock() {
    Album 앨범 = new Album();
    앨범.addStock(100);
    int prevStockQuantity = 앨범.getStockQuantity();
    앨범.subStock(10);
    assertThat(prevStockQuantity - 10).isEqualTo(앨범.getStockQuantity());
  }

  @Test
  @DisplayName("상품 수량 재고 감소 실패")
  public void subStockFail() {
    Album 앨범 = new Album();
    앨범.addStock(100);
    int prevStockQuantity = 앨범.getStockQuantity();
    org.junit.jupiter.api.Assertions.assertThrows(NotEnoughStockException.class, () -> 앨범.subStock(1000));
  }
}
