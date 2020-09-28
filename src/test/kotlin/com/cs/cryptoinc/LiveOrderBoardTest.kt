package com.cs.cryptoinc

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal

class LiveOrderBoardTest {
    @Test
    fun `Invalid order should not register`() {
        val orderBoard = LiveOrderBoard()
        assertThat(orderBoard.orders).isEmpty()
        val orderStatus = orderBoard.registerOrder(Order("", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5")))
        assertThat(orderStatus).isEqualTo(OrderStatus.NOT_REGISTERED)
        assertThat(orderBoard.orders.size).isEqualTo(0)
    }

    @Test
    fun `Valid order should register`() {
        val orderBoard = LiveOrderBoard()
        assertThat(orderBoard.orders).isEmpty()
        val orderStatus = orderBoard.registerOrder(Order("user1", CoinType.BITCOIN, BigDecimal("1.5"), BigDecimal("0.5")))
        assertThat(orderStatus).isEqualTo(OrderStatus.REGISTERED)
        assertThat(orderBoard.orders.size).isEqualTo(1)
    }

}
