polyglot
=======
polyglot is a tiered data store solution for use by on-line applications.

Large scale internet systems such as eCommerce platforms often use a combination of relational (SQL) and non-relational (NoSQL) data stores. 
Contrary to product claims, it is hard to find a single data store that meets common read-write patterns of online applications. 
Different databases try to optimize for specific workload patterns and data durability, consistency guarantees. While some store the entire dataset in memory, 
others leverage disks - spinning or optimized for SSDs. The underlying storage is also quite different between Relational, Columnar, Key-Value or Document store.

polyglot is a tiered data store solution for online applications that combines a set of database features. Work on polyglot is derived from
solutions for scaling MySQL such as [Vitess](https://github.com/youtube/vitess), a Reliable change propagation system like [Aesop](https://github.com/Flipkart/aesop) 
and inspired by literature available on systems like [Espresso](https://engineering.linkedin.com/espresso/introducing-espresso-linkedins-hot-new-distributed-document-store).

## Where it fits
![polyglot infographic](https://github.com/flipkart-incubator/polyglot/raw/master/docs/polyglot_infographic.jpg)
