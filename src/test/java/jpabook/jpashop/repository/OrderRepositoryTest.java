package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderRepositoryTest {

  @Autowired EntityManager em;
  @Autowired OrderRepository orderRepository;
  @Autowired OrderService orderService;

  static Member member1, member2;
  static Item item;

  static OrderSearch orderSearch1, orderSearch2, orderSearch3, orderSearch4;

  @Test
  @DisplayName("문자열 조합으로 jpql 생성")
  public void findAllByStringJPQL() {
    setMembers();
    setItem();
    order();
    setOrderSearch();

    for (Order order : orderRepository.findAllByCustomJpql(orderSearch1)) {
      assertThat(order.getMember().getName()).isEqualTo("Brothersoo");
      assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    for (Order order : orderRepository.findAllByCustomJpql(orderSearch2)) {
      assertThat(order.getMember().getName()).isEqualTo("Brothersoo");
      assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    for (Order order : orderRepository.findAllByCustomJpql(orderSearch3)) {
      assertThat(order.getMember().getName()).isEqualTo("MoistyBro");
      assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }
  }

  @Test
  @DisplayName("정적 쿼리의 한계")
  public void staticQueryFail() {
    setMembers();
    setItem();
    order();
    setOrderSearch();

    assertThat(orderRepository.findAllByStaticQuery(orderSearch4).size())
        .isNotEqualTo(orderRepository.findAllByCustomJpql(orderSearch4).size());
  }

  @Test
  @DisplayName("JPA Criteria를 사용한 동적 쿼리")
  public void dynamicQueryViaJpaCriteria() {
    setMembers();
    setItem();
    order();
    setOrderSearch();

    for (Order order : orderRepository.findAllByJpaCriteria(orderSearch1)) {
      assertThat(order.getMember().getName()).isEqualTo("Brothersoo");
      assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    for (Order order : orderRepository.findAllByJpaCriteria(orderSearch2)) {
      assertThat(order.getMember().getName()).isEqualTo("Brothersoo");
      assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    for (Order order : orderRepository.findAllByJpaCriteria(orderSearch3)) {
      assertThat(order.getMember().getName()).isEqualTo("MoistyBro");
      assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }
  }

  void setOrderSearch() {
    orderSearch1 = new OrderSearch();
    orderSearch1.setOrderStatus(OrderStatus.ORDER);
    orderSearch1.setMemberName("Brothersoo");

    orderSearch2 = new OrderSearch();
    orderSearch2.setOrderStatus(OrderStatus.CANCEL);
    orderSearch2.setMemberName("Brothersoo");

    orderSearch3 = new OrderSearch();
    orderSearch3.setOrderStatus(OrderStatus.ORDER);
    orderSearch3.setMemberName("MoistyBro");

    orderSearch4 = new OrderSearch();
  }

  void order() {
    orderService.order(member1.getId(), item.getId(), 2);
    orderService.order(member2.getId(), item.getId(), 3);
    Long cancelOrderId = orderService.order(member1.getId(), item.getId(), 10);

    orderService.cancel(cancelOrderId);
  }

  void setMembers() {
    member1 = new Member();
    member2 = new Member();
    member1.setName("Brothersoo");
    member2.setName("MoistyBro");

    em.persist(member1);
    em.persist(member2);
  }

  void setItem() {
    item = new Book();
    item.setPrice(10000);
    item.setName("book1");
    item.addStock(1000);
    em.persist(item);
  }
}
