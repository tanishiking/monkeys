SCALA := scala-cli

.PHONY: run
run:
	$(SCALA) run .

.PHONY: test
test:
	$(SCALA) test .

.PHONY: clean
clean:
	$(SCALA) clean .

