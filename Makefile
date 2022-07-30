SCALA := scala-cli

.PHONY: run
run:
	$(SCALA) run .

.PHONY: compile
compile:
	$(SCALA) compile .

.PHONY: test
test:
	$(SCALA) test .

.PHONY: fmt
fmt:
	$(SCALA) fmt .

.PHONY: clean
clean:
	$(SCALA) clean .

