build:
	javac-coursera Percolation.java

run:
	java-coursera Percolation ./test/input1.txt

check:build
	checkstyle-coursera week1/Percolation.java
	checkstyle-coursera week1/PercolationStats.java

zip:check
	rm -rf percolation.zip && zip percolation.zip Percolation.java PercolationStats.java