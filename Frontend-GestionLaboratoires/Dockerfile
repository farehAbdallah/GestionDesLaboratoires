# Use Node.js 20.11.1 as the base image
FROM node:20.11.1

# Set the working directory in the container
WORKDIR /app

# Copy package files and install dependencies
COPY Frontend-GestionLaboratoires/package*.json ./
RUN npm install

# Install Angular CLI globally
RUN npm install -g @angular/cli

# Copy the rest of the application code
COPY Frontend-GestionLaboratoires/ .

# Expose port 4200 to the host
EXPOSE 4200

# Command to run the Angular app and allow external access
CMD ["ng", "serve", "--disable-host-check", "0.0.0.0", "--port", "4200"]
