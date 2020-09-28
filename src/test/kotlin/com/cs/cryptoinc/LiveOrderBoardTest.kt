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
    fun `cancelling an existing order de-register the order`(){
        val orderBoard = LiveOrderBoard()
        val order = Order("user1", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5"), OrderType.BUY)
        orderBoard.registerOrder(order)
        assertThat(orderBoard.orders).contains(order)
        orderBoard.cancelOrder(order)
        assertThat(orderBoard.orders).doesNotContain(order)
    }

    @Test
    fun `cancelling a non existing order should not remove anything`(){
        val orderBoard = LiveOrderBoard()
        val order = Order("user1", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5"), OrderType.BUY)
        orderBoard.registerOrder(order)
        assertThat(orderBoard.orders.size).isEqualTo(1)
        assertThat(orderBoard.orders).contains(order)
        orderBoard.cancelOrder( Order("user2", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5"), OrderType.BUY))
        assertThat(orderBoard.orders.size).isEqualTo(1)
        assertThat(orderBoard.orders).contains(order)
    }

}
