
all:
	./gradlew build && make compose

compose:
	docker-compose up --remove-orphans -d
	
web-compose:
	docker-compose up --remove-orphans -d --build web

web:
	./gradlew build && make web-compose
db:
	docker-compose up --remove-orphans -d --build db
