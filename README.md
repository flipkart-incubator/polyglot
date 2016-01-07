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

## Features
* Transactional - limited to a shard (has to be explicitly defined), 2PC may be later
* Secondary indices - read supported across shards. Possibly inconsistent writes (in the absence of 2PC). Also supports Eventually Consistent secondary indices (has to be explicitly defined) and Free-Text search.
* Consistency - No loss of data with failure tolerance of 1 node in a shard. Possible loss with simultaneous failure of 2 or more nodes. RYW when limited to single shard. Writes limited to one shard and will fail across shards (2PC scenario)
* SQL or a subset as the primary query language
* Choice of Read Performance vs Mutable secondary indices
* Availability - Read availability upto N-1 failures of nodes in a shard. Write availability affected by re-parenting duration (reparenting is automatic) and detection of failure (automatic except in case of network partitions)
* Data Change propagation (with at least once delivery guarantees) with guaranteed ordering within a partition(a subset within a shard). During re-sharding, manual intervention may be needed to provide ordering guarantees.


## Getting help
polyglot is in very early stages of evolution. For discussion, subscribe to the polyglot interest mailing list: http://groups.google.com/group/polyglot-interest

## License
polyglot is licensed under : The Apache Software License, Version 2.0. Here is a copy of the license (http://www.apache.org/licenses/LICENSE-2.0.txt)

