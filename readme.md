# Crypto-Inc

You are working for Crypto Inc. and we would like you to create a program to show the top 10 BUY or
SELL orders in the Crypto Inc. marketplace.
The Crypto Inc. 'Live Order Board' should support these features:

### Placing an order. 

An order can be either a BUY or a SELL and should capture
- user id
- coin type (Litecoin, Ethereum.. etc)
- order quantity (how many coins)
- price per coin (e.g.: £125)
### Cancel a registered order - this will remove the order from 'Live Order Board'


### Get summary information of live orders 

Imagine we have received the following orders:
- a) SELL: 350.1 Ethereum @ £13.6 [user1]
- b) SELL: 50.5 for £14 [user2]
- c) SELL: 441.8 for £13.9 [user3]
- d) SELL: 3.5 for £13.6 [user4]

Our ‘Live Order Board’ should provide us the following summary information:
- 353.6 for £13.6 // order a + order d
- 441.8 for £13.9 // order c
- 50.5 for £14 // order b

The first thing to note here is that orders for the same price should be merged together (even when they
are from different users). In this case it can be seen that order a) and d) were for the same price (£13.6)
and this is why only their sum (353.6) is displayed (for £13.6) and not the individual orders (350.1 and
3.5).The last thing to note is that for SELL orders the orders with lowest prices are displayed first.
Opposite is true for the BUY orders.

## Running the Tests
~~~shell script
mvn clean test
~~~

### Assumptions/Open Discussion

- There could be orders with both buy and sell showing at the same live order board
    In this case I am implicitly showing both orders 