#!/bin/bash
for t in 0.5 0.6 0.7 0.8 0.9
do
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoBaselineTest.jar ./tokenBtree ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZigTest.jar ./tokenBtree ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZigPlusTest.jar ./tokenBtree ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZagTest.jar ./tokenBtree ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZagPlusTest.jar ./tokenBtree ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZigZagTest.jar ./tokenBtree ./query-stanford.txt $t 0 0 1 0
done
