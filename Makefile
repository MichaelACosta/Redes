all: Crawler.class Trabalho.class
Crawler.class: Trabalho.java
	javac -cp ".:js.jar" Trabalho.java
Trabalho.class: Trabalho.java
	javac -cp ".:js.jar" Trabalho.java
clean:
	rm -f *.class

