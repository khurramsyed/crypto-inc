package com.cs.cryptoinc

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal

class LiveOrderBoardTest {
    @Test
    fun `Invalid order should not register`() {
        val orderBoard = LiveOrderBoard()
        assertThat(orderBoard.orders).isEmpty()
        val orderStatus = orderBoard.registerOrder(Order("", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5"), OrderType.BUY))
        assertThat(orderStatus).isEqualTo(OrderStatus.NOT_REGISTERED)
        assertThat(orderBoard.orders.size).isEqualTo(0)
    }

    @Test
    fun `Valid order should register`() {
        val orderBoard = LiveOrderBoard()
        assertThat(orderBoard.orders).isEmpty()
        val orderStatus = orderBoard.registerOrder(Order("user1", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5"), OrderType.BUY))
        assertThat(orderStatus).isEqualTo(OrderStatus.REGISTERED)
        assertThat(orderBoard.orders.size).isEqualTo(1)
    }

    @Test
    fun `cancelling an existing order de-register the order`() {
        val orderBoard = LiveOrderBoard()
        val order = Order("user1", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5"), OrderType.BUY)
        orderBoard.registerOrder(order)
        assertThat(orderBoard.orders).contains(order)
        orderBoard.cancelOrder(order)
        assertThat(orderBoard.orders).doesNotContain(order)
    }

    @Test
    fun `cancelling a non existing order should not remove anything`() {
        val orderBoard = LiveOrderBoard()
        val order = Order("user1", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5"), OrderType.BUY)
        orderBoard.registerOrder(order)
        assertThat(orderBoard.orders.size).isEqualTo(1)
        assertThat(orderBoard.orders).contains(order)
        orderBoard.cancelOrder(Order("user2", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5"), OrderType.BUY))
        assertThat(orderBoard.orders.size).isEqualTo(1)
        assertThat(orderBoard.orders).contains(order)
    }

    @Test
    fun `Sell Orders are arranged by ascending price on live board`() {
        val orderBoard = LiveOrderBoard()
        orderBoard.registerOrder(Order("user1", CoinType.ETHEREUM, BigDecimal("13.6"), BigDecimal("350.1"), OrderType.SELL))
        orderBoard.registerOrder(Order("user2", CoinType.ETHEREUM, BigDecimal("14.0"), BigDecimal("50.5"), OrderType.SELL))
        orderBoard.registerOrder(Order("user3", CoinType.ETHEREUM, BigDecimal("13.9"), BigDecimal("441.8"), OrderType.SELL))
        orderBoard.registerOrder(Order("user4", CoinType.ETHEREUM, BigDecimal("13.6"), BigDecimal("3.5"), OrderType.SELL))
        assertThat(orderBoard.orders.size).isEqualTo(4)
        val orderSummary = orderBoard.orderSummary();
        assertThat(orderSummary.size).isEqualTo(3)
        assertThat(orderSummary[0].price).isEqualTo(BigDecimal("13.6"))
        assertThat(orderSummary[0].quantity).isEqualTo(BigDecimal("353.6"))
        assertThat(orderSummary[1].price).isEqualTo(BigDecimal("13.9"))
        assertThat(orderSummary[1].quantity).isEqualTo(BigDecimal("441.8"))
        assertThat(orderSummary[2].price).isEqualTo(BigDecimal("14.0"))
        assertThat(orderSummary[2].quantity).isEqualTo(BigDecimal("50.5"))
    }


    @Test
    fun `Buy Orders are arranged by ascending price on live board`() {
        val orderBoard = LiveOrderBoard()
        orderBoard.registerOrder(Order("user1", CoinType.ETHEREUM, BigDecimal("13.6"), BigDecimal("350.1"), OrderType.BUY))
        orderBoard.registerOrder(Order("user2", CoinType.ETHEREUM, BigDecimal("14.0"), BigDecimal("50.5"), OrderType.BUY))
        orderBoard.registerOrder(Order("user3", CoinType.ETHEREUM, BigDecimal("13.9"), BigDecimal("441.8"), OrderType.BUY))
        orderBoard.registerOrder(Order("user4", CoinType.ETHEREUM, BigDecimal("13.6"), BigDecimal("3.5"), OrderType.BUY))
        assertThat(orderBoard.orders.size).isEqualTo(4)
        val orderSummary = orderBoard.orderSummary();
        assertThat(orderSummary.size).isEqualTo(3)
        assertThat(orderSummary[0].price).isEqualTo(BigDecimal("14.0"))
        assertThat(orderSummary[0].quantity).isEqualTo(BigDecimal("50.5"))
        assertThat(orderSummary[1].price).isEqualTo(BigDecimal("13.9"))
        assertThat(orderSummary[1].quantity).isEqualTo(BigDecimal("441.8"))
        assertThat(orderSummary[2].price).isEqualTo(BigDecimal("13.6"))
        assertThat(orderSummary[2].quantity).isEqualTo(BigDecimal("353.6"))
    }

}
