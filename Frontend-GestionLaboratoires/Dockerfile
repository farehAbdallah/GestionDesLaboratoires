# Stage 1: Build the Angular app
FROM node:16-alpine as build
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Stage 2: Serve the Angular app with nginx
FROM nginx:alpine
COPY --from=build /app/dist/Frontend-GestionLaboratoires /usr/share/nginx/html
EXPOSE 80
