package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.common.reflection.XMember;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="oder_item_id")
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;
    private int orderPrice; //주문 가격
    private int count; //주문수량

    public static OrderItem createdOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

//        Member member = new Member();
//        member.setName("dasdsad");
//
//        memberRepository.save(member);

        item.removeStock(count);
        return orderItem;
    }

    //비즈니즈로직

    public void cancel() {
        getItem().addStock(count);
    }

    /**
     *
     * 주문상품 전체 가격조회
     */

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
