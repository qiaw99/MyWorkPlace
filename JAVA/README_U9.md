U9: heap-structure
在dequeue()的时候不能把根元素设置为null，这样会产生NullPointException,需要把heap里的最后一个
元素设置为根元素，然后再去调用buildPriorityQueue()来重塑heap结构。
