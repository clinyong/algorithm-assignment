build:
	javac-coursera Deque.java

run:build
	java-coursera Deque

check:build
	checkstyle-coursera week2/*.java

zip:check
	rm -rf queues.zip && zip queues.zip *.java

random:
	javac-coursera RandomizedQueue.java
	java-coursera RandomizedQueue

client:
	javac-coursera Permutation.java