package jpabook.jpashop.controller;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

  private final MemberService memberService;
  private final ItemService itemService;
  private final OrderService orderService;

  @GetMapping("/order")
  public String createForm(Model model) {
    List<Member> members = memberService.findMembers();
    List<Item> items = itemService.findAll();

    model.addAttribute("members", members);
    model.addAttribute("items", items);

    return "/orders/createOrderForm";
  }

  @PostMapping("/order")
  public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId,
      @RequestParam("count") Integer count) {

    try {
      orderService.order(memberId, itemId, count);
    } catch (NotEnoughStockException nese) {
      log.error(nese.getMessage());
      return "redirect:/order";
    }
    return "redirect:/orders";
  }

  @GetMapping("/orders")
  public String list(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

    model.addAttribute("orders", orderService.findOrders(orderSearch));
    return "/orders/orderList";
  }

  @PostMapping("/orders/{orderId}/cancel")
  public String cancel(@PathVariable("orderId") Long orderId) {
    orderService.cancel(orderId);
    return "redirect:/orders";
  }
}
