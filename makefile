JC = javac

default: 
	$(JC) -sourcepath src -d classes src/*.java
	echo "\n\nRunning Tests\n"
	java -cp classes TesterClass

clean:
	$(RM) classes/*
	$(JC) -sourcepath src -d classes src/*.java
	@echo "\n\nRunning Tests\n"
	java -cp classes TesterClass