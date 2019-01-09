# ZigZag
This is the source code of the paper "ZigZag: Supporting Similarity Queries on Vector Space Models" published in SIGMOD 2018. 
(https://dl.acm.org/citation.cfm?id=3196936)
## Index Build
java -Xmx280g -jar ./TfidfPrefIndexBuild.jar 40000000 ./stanford_data.txt ./index  27000 4096 2 0 60000 4096 1 0 15000 4000 4096 1 0 5000 4096 2 0 6000 4096 2 0 0 500
## Algorithms
### Baseline
### Zig
java -Xmx1g -jar AlgoZigTest.jar ./index ./query-stanford.txt 0.9 0 0 1 0
### Zig+
java -Xmx1g -jar AlgoZigPlusTest.jar ./index ./query-stanford.txt 0.9 0 0 1 0
### Zag
java -Xmx1g -jar AlgoZagTest.jar ./index ./query-stanford.txt 0.9 0 0 1 0
### Zag+
java -Xmx1g -jar AlgoZagPlusTest.jar ./index ./query-stanford.txt 0.9 0 0 1 0
### ZigZag
java -Xmx1g -jar AlgoZigZagTest.jar ./index ./query-stanford.txt 0.9 0 0 1 0
## Dataset
We have uploaded three datasets, pubmed title, twitter text, stanford quotes, onto Googledrive, which can be obtained from https://drive.google.com/drive/folders/1XyqcpqAyLlPfnX3d8N9wSEDQLlj_oXAK.
