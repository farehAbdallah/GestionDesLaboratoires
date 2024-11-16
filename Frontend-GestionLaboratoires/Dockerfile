# Use Node.js 20.11.1 as the base image
FROM node:20.11.1

# Set the working directory in the container
WORKDIR /app

# Copy package files and install dependencies
COPY Frontend-GestionsLaboratoires/package*.json ./
RUN npm install

# Copy the rest of the application code
COPY Frontend-GestionsLaboratoires/ .

# Expose port 4200 to the host
EXPOSE 4200

# Command to run the Angular app
CMD ["npm", "run", "start"]
