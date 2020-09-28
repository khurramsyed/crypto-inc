package com.cs.cryptoinc

import java.math.BigDecimal
import java.util.function.Function

class LiveOrderBoard() {
    var orders = mutableListOf<Order>()

    fun registerOrder(order: Order): OrderStatus {
        if (!isValidOrder(order)) {
            return OrderStatus.NOT_REGISTERED
        }
        orders.add(order)
        return OrderStatus.REGISTERED
    }

    fun cancelOrder(order: Order) {
        orders.remove(order)
    }

    fun orderSummary(): List<OrderSummary> {
        val buySellOrders = orders.partition { order -> order.orderType == OrderType.SELL }

        return createOrderSummariesByType(OrderType.SELL, buySellOrders.first)
                .sortedBy { orderSummary -> orderSummary.price } +
                createOrderSummariesByType(OrderType.BUY, buySellOrders.second)
                        .sortedByDescending { orderSummary -> orderSummary.price }
    }

    private fun createOrderSummariesByType(orderType: OrderType, orderList: List<Order>) = orderList.groupBy { it.price }
            .map { OrderSummary(calculateSumOfQuantities(it), it.key, orderType) }

    private fun calculateSumOfQuantities(ordersByPrice: Map.Entry<BigDecimal, List<Order>>) = ordersByPrice.value.map { it.quantity }
            .reduce(BigDecimal::add)

    private fun isValidOrder(order: Order) = order.userId.isNotEmpty() && order.userId.isNotBlank()

}