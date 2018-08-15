#!/bin/bash
java -Xmx280g -jar TfidfPrefIndexBuild.jar 64000000 mt.txt tokenBtree 27000 4096 2 0 60000 4096 1 0 15000 4000 4096 1 0 5000 4096 2 0 6000 4096 2 0 0 500
