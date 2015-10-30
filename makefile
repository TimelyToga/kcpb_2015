JC = javac

default: 
	$(JC) -sourcepath src -d bin src/*.java
	echo "\n\nRunning Tests\n"
	java -cp bin TesterClass

clean:
	$(RM) /bin/*
	$(JC) -sourcepath src -d bin src/*.java
	@echo "\n\nRunning Tests\n"
	java -cp bin TesterClass