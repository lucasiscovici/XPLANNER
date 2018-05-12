FROM node:6

RUN mkdir -p /usr/src/app 
ADD prod/ /usr/src/app
ADD dist /usr/src/app/dist

WORKDIR /usr/src/app

ENV PORT=80
EXPOSE 80

CMD [ "npm", "start" ]
