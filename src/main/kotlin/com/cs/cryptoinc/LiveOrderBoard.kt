package com.cs.cryptoinc

class LiveOrderBoard(){
    var orders = mutableListOf<Order>()

    fun registerOrder(order: Order): OrderStatus {
        if(!isValidOrder(order)){
           return OrderStatus.NOT_REGISTERED
        }
        orders.add(order)
        return OrderStatus.REGISTERED
    }

    private fun isValidOrder(order: Order):Boolean{
        if(order.userId.isEmpty() || order.userId.isBlank())
            return false;
        return true;
    }

}