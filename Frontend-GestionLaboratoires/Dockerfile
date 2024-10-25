# Use a Node.js base image
FROM node:18

# Set the working directory in the container
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Install Angular CLI globally
RUN npm install -g @angular/cli

# Copy the rest of the application code
COPY . .

# Expose port 4200 to the host
EXPOSE 4200

# Command to run the Angular app
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "4200"]
