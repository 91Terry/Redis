# Redis真的那么好用吗？
## 一、Redis是什么？
Redis是一个开源的底层使用C语言编写的key-value存储数据库。可用于缓存、事件发布订阅、高速队列等场景。而且支持丰富的数据类型：string(字符串)、hash(哈希)、list(列表)、set(无序集合)、zset(sorted set：有序集合)

Redis和Memcached类似，为保证效率，数据都是缓存在内存中。区别的是Redis会**周期性**的把更新的数据**写入磁盘**或者把修改操作写入追加的记录文件，并且在此基础上实现了**master-slave（主从）** 同步。

## 二、Redis在项目中的应用场景
### 1、缓存数据
对于最常用的数据，经常要查询并且变动不是很频繁的数据：

* 需要高频次访问
* 持久化数据访问较慢、
* 用key查询

### 2、消息队列
相当于消息订阅系统，比如ActiveMQ、RocketMQ。如果对数据有较高一致性要求时，还是建议使用MQ)

### 3、计数器

比如统计点击率、秒杀，排行榜Top N，redis具有原子性，可以避免并发问题。

### 4、热点数据

比如新闻网站实时热点、微博热搜等，需要频繁更新。总数据量比较大的时候直接从数据库查询会影响性能。

## 三、为什么选择Redis？
### 1、单点服务器
通常，在单节点的服务器是这样的：

![1be74d3e233cb95e032c7807160ced60.png](en-resource://database/869:0)

随着企业的发展、业务的扩展。面对海量的数据，直接使用MySql会导致性能下降，数据的读写也会非常慢。于是我们就可以搭配缓存来处理海量数据。

于是现在我们是这样的：

![cf341539d4f8d8ece71b1c40170c8559.png](en-resource://database/871:0)

上图只是加上了缓存的作用，当数据继续增大我们需要利用主从复制技术来达到读写分离数据库层直接与缓存进行交互，如果缓存中有数据直接返回客户端，如果没有才会从MySql中去查询。从而减小了数据库的压力，提升了效率。


平时发布了一款新手机，会有抢购活动。同一时间段，服务端会收到很多的下单请求。我们需要使用redis的原子操作来实现这个“单线程”。首先我们把库存存在一个列表中，假设有10件库存，就往列表中push10个数，这个数没有实际意义，仅仅只是代表10件库存。抢购开始后，每到来一个用户，就从列表中pop一个数，表示用户抢购成功。当列表为空时，表示已经被抢光了。因为列表的pop操作是原子的，即使有很多用户同时到达，也是依次执行的。

### 2、Redis为什么会快？

1. Redis是纯内存操作，需要的时候需要我们手动持久化到硬盘中，内存中的数据是全量的。
2. Redis是单线程，从而避开了多线程中上下文频繁切换的操作。
3. Redis数据结构简单、对数据的操作也比较简单，执行查询速度快。
4. 使用底层模型不同，它们之间底层实现方式以及与客户端之间通信的应用协议不一样，Redis直接自己构建了VM 机制 ，因为一般的系统调用系统函数的话，会浪费一定的时间去移动和请求。
5. 使用多路I/O复用模型，非阻塞I/O。

**多路复用原理：** 多路Io复用是利用select、poll、epoll（不同的监控策略）可以同时监察多个流的IO事件的能力，在空闲的时候会把当前线程阻塞，当有一个或多个流由IO事件发生时，就从阻塞态中唤醒，处理就绪的流。

## 四、Redis的数据类型
### 1、String

字符串是最常用的数据类型，他能够存储任何类型的字符串，当然也包括二进制、JSON化的对象、甚至是base64编码之后的图片。在Redis中一个字符串最大的容量为512MB，可以说是无所不能了。

常用命令：get、set、incr、decr、mget等。

使用场景：常规key-value缓存应用。常规计数: 微博数, 粉丝数。 实现方式：String在redis内部存储默认就是一个字符串，被redisObject所引用，当遇到incr,decr等操作时会转成数值型进行计算，此时redisObject的encoding字段为int。


### 2、List

List的实现为一个双向链表，即可以支持反向查找和遍历，更方便操作，不过带来了部分额外的内存开销，Redis 内部的很多实现，包括发送缓冲队列等也都是用的这个数据结构。另外，可以利用 lrange 命令，做基于 Redis 的分页功能，性能极佳，用户体验好。


常用命令：lpush（添加左边元素）,rpush,lpop（移除左边第一个元素）,rpop,lrange（获取列表片段，LRANGE key start stop）等。

### 3、Set

set 是string类型的无序集合。集合是通过hashtable实现的，概念和数学中个的集合基本类似，可以交集，并集，差集等等，set中的元素是没有顺序的。所以添加，删除，查找的复杂度都是O(1)。


常用命令：sadd,spop,smembers,sunion 等。


使用场景：Redis set对外提供的功能与list类似是一个列表的功能，特殊之处在于set是可以自动排重的，当你需要存储一个列表数据，又不希望出现重复数据时，set是一个很好的选择，并且set提供了判断某个成员是否在一个set集合内的重要接口，这个也是list所不能提供的。

### 4、Hash

Hash 是一个键值(key => value)对集合。Redis hash 是一个 string 类型的 field 和 value 的映射表，hash 特别适合用于存储对象。 

常用命令：hget,hset,hgetall 等。 

使用场景：我们简单举个实例来描述下Hash的应用场景，比如我们要存储一个用户信息对象数据，包含以下信息：用户ID为查找的key，存储的value用户对象包含姓名，年龄，生日等信息。

![4358e32c6369d79c10479f811ea39568.png](en-resource://database/873:0)


Key是用户ID, value是一个Map，这个Map的key是成员的属性名，value是属性值，这样对数据的修改和存取都可以直接通过其内部Map的Key(Redis里称内部Map的key为field), 也就是通过 key(用户ID) + field(属性标签) 就可以操作对应属性数据了，既不需要重复存储数据，也不会带来序列化和并发修改控制的问题，很好的解决了问题。

### 5、Zset（sorted set）

Redis有序集合zset与普通集合set非常相似，是一个**没有重复元素**的字符串集合。不同之处是有序集合的每个成员都关联了一个评分（score）,这个评分（score）被用来按照从最低分到最高分的方式排序集合中的成员。集合的成员是唯一的，但是评分可以是重复了 。

常用命令：zadd,zrange,zrem,zcard等。

使用场景：当需要一个有序的并且不重复的集合列表，那么可以选择sorted set数据结构，例如文章访问量排名等。


### 6、各种数据类型的应用场景


| 类型 |简介  |特性  |场景  |
| --- | --- | --- | --- |
|String(字符串)  |二进制安全  |可以包含任何数据,比如jpg图片或者序列化的对象,一个键最大能存储512M  | --------------- |
| List(列表) | 链表(双向链表) | 增删快,提供了操作某一段元素的API | 1、最新消息排行等功能(比如朋友圈的时间线) 2、消息队列 |
| Set(集合) |哈希表实现,元素不重复  |1、添加、删除、查找的复杂度都是O(1)  2、为集合提供了求交集、并集、差集等操作  |1、共同好友 2、利用唯一性,统计访问网站的所有独立ip 3、好友推荐时,根据tag求交集,大于某个阈值就可以推荐  |
|Hash(字典)  |键值对集合,即java中的Map类型  |适合存储对象,并且可以像数据库中update一个属性一样只修改某一项属性值(Memcached中需要取出整个字符串反序列化成对象修改完再序列化存回去)  |存储、读取、修改用户属性  |
|Sorted Set(有序集合)  |将Set中的元素增加一个权重参数score,元素按score有序排列  |数据插入集合时,已经进行天然排序  |1、排行榜 2、带权重的消息队列  |


