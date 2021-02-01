.PHONY: unit-test integration-test test acceptance-test build_ start-dependencies do-integrate integrate validate

###############################################
# INTEGRATION TASK SUITE
###############################################

unit-test:
	$(info ~~~~~~~~~~ UNIT TEST ~~~~~~~~~~)
	mvn test

integration-test:
	$(info ~~~~~~~~~~ INTEGRATION TEST ~~~~~~~~~~)

test: unit-test integration-test

build_:
	$(info ~~~~~~~~~~ BUILD ~~~~~~~~~~)
	mvn clean install

start-dependencies:
	$(info ~~~~~~~~~~~ START DEPENDENCIES ~~~~~~~~~~~)

run:
	$(info ~~~~~~~~~~ RUN ~~~~~~~~~)
	./support/scripts/run.sh

stop:
	$(info ~~~~~~~~~~ STOP ~~~~~~~~~)
	lsof -ti:8096 | xargs kill

stop-dependencies:
	$(info ~~~~~~~~~~ STOP ~~~~~~~~~)
	docker-compose stop

acceptance-test:
	$(info ~~~~~~~~~~ ACCEPTANCE TEST ~~~~~~~~~)

do-integrate:
	$(info ~~~~~~~~~~ INTEGRATE ~~~~~~~~~)
	./support/scripts/integrate.sh

validate: test build_ start-dependencies run acceptance-test

integrate: validate do-integrate