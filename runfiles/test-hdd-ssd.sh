#!/bin/bash
for t in 0.5 0.6 0.7 0.8 0.9
do
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx2g -jar AlgoBaselineTest.jar ./index ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoBaselinePruningTest.jar ./index ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZigTest.jar ./index ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZigPlusTest.jar ./index ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZagTest.jar ./index ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZagPlusTest.jar ./index ./query-stanford.txt $t 0 0 1 0
	echo 3 > /proc/sys/vm/drop_caches
	java -Xmx1g -jar AlgoZigZagTest.jar ./index ./query-stanford.txt $t 0 0 1 0
done
