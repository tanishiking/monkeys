SCALA := scala-cli
SCALA_VERSION := "3.2.0-RC2"

.PHONY: run
run:
	$(SCALA) run . -S $(SCALA_VERSION)

.PHONY: test
test:
	$(SCALA) test . -S $(SCALA_VERSION)

.PHONY: clean
clean:
	$(SCALA) clean .

