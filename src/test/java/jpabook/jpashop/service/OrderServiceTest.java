package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired EntityManager em;
  @Autowired OrderService orderService;
  @Autowired OrderRepository orderRepository;

//  static Member member;
//  static Item item;

//  @BeforeAll
//  static void set() {
//    System.out.println("Start order service test");
//    member = new Member();
//    member.setName("brothersoo");
//    member.setAddress(new Address("서울", "종로 3가", "01010"));
//    em.persist(member);
//
//    item = new Book();
//    item.setName("Mobidic");
//    item.setPrice(21000);
//    item.addStock(10);
//    em.persist(item);
//  }

  @Test
  @DisplayName("상품주문")
  public void order() {
    Member member = createMember("brothersoo", "서울", "종로 3가", "01010");
    Item item = createBook("name", 21000, 10);

    Long orderId = orderService.order(member.getId(), item.getId(), 1);

    Order order = orderRepository.findOne(orderId);
    assertThat(order.getDelivery().getOrder()).isEqualTo(order);
    assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    assertThat(order.getMember()).isEqualTo(member);
    assertThat(order.getOrderItems().get(0).getItem()).isEqualTo(item);
    assertThat(order.getTotalPrice()).isEqualTo(item.getPrice());
    assertThat(item.getStockQuantity()).isEqualTo(9);
  }

  @Test
  @DisplayName("상품주문_재고수량초과")
  public void orderFail_notEnoughStock() {
    Member member = createMember("brothersoo", "서울", "종로 3가", "01010");
    Item item = createBook("name", 21000, 10);

    assertThrows(NotEnoughStockException.class,
        () -> orderService.order(member.getId(), item.getId(), 11));
  }

  @Test
  @DisplayName("주문취소")
  public void cancelOrder() {
    Member member = createMember("brothersoo", "서울", "종로 3가", "01010");
    Item item = createBook("name", 21000, 10);;

    Long orderId = orderService.order(member.getId(), item.getId(), 1);

    orderService.cancel(orderId);
    Order order = orderRepository.findOne(orderId);

    assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
    assertThat(item.getStockQuantity()).isEqualTo(10);
  }

  private Member createMember(String name, String city, String street, String zipcode) {
    Member member = new Member();
    member.setName(name);
    member.setAddress(new Address(city, street, zipcode));
    em.persist(member);
    return member;
  }

  private Item createBook(String name, int price, int quantity) {
    Item item = new Book();
    item.setName(name);
    item.setPrice(price);
    item.addStock(quantity);
    em.persist(item);
    return item;
  }
}
