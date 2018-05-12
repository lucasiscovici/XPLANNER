
prod:
	make front_prod
prodb:
	make front_prodb
dev:
	make front

web:
	docker-compose up -d web

db:
	docker-compose up -d db

front:
	docker-compose up -d --build front

front_prod:
	docker-compose up -d front_prod
	
front_prodb:
	docker-compose up -d --build front_prod
