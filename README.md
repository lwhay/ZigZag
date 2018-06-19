# ZigZag
This is the source code of the paper "ZigZag: Supporting Similarity Queries on Vector Space Models" published in SIGMOD 2018. 
(https://dl.acm.org/citation.cfm?id=3196936)
## Index Build
java -Xmx280g -jar ./TfidfPrefIndexBuild.jar 40000000 ./stanford_data.txt ./tokenBtree  27000 4096 2 0 60000 4096 1 0 15000 4000 4096 1 0 5000 4096 2 0 6000 4096 2 0 0 500
## Algorithms
### Baseline
### Zig
java -Xmx1g -jar AlgoZigTest.jar ./tokenBtree ./query-stanford.txt 0.9 0 0 1 0
### Zig+
java -Xmx1g -jar AlgoZigPlusTest.jar ./tokenBtree ./query-stanford.txt 0.9 0 0 1 0
### Zag
java -Xmx1g -jar AlgoZagTest.jar ./tokenBtree ./query-stanford.txt 0.9 0 0 1 0
### Zag+
java -Xmx1g -jar AlgoZagPlusTest.jar ./tokenBtree ./query-stanford.txt 0.9 0 0 1 0
### ZigZag
java -Xmx1g -jar AlgoZigZagTest.jar ./tokenBtree ./query-stanford.txt 0.9 0 0 1 0
