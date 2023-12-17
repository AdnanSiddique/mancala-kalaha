FROM node:20.9.0-alpine
WORKDIR /manacala-kalaha-react-ui

COPY public/ /manacala-kalaha-react-ui/public
COPY src/ /manacala-kalaha-react-ui/src
COPY package.json /manacala-kalaha-react-ui/

RUN npm install --silent
CMD ["npm", "start"]
