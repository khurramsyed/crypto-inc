package com.cs.cryptoinc

import java.math.BigDecimal

data class Order(val userId: String, val coinType: CoinType, val price: BigDecimal, val quantity: BigDecimal, val orderType: OrderType)

data class OrderSummary(val quantity: BigDecimal, val price: BigDecimal, val orderType: OrderType)

enum class CoinType(val coinType: String) {
    LITECOIN("LiteCoin"),
    ETHEREUM("Ethereum"),
    BITCOIN("BitCoin")
}

enum class OrderType(val orderType: String) {
    BUY("Buy"),
    SELL("Sell")
}

enum class OrderStatus(val status: String) {
    REGISTERED("Registered"),
    NOT_REGISTERED("Not Registered")
}