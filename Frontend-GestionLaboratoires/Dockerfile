# Use Node.js 20.11.1 as the base image
FROM node:20.11.1

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire project to the working directory
COPY . .

# Expose port 4200
EXPOSE 4200

# Start the Angular development server
CMD ["npm", "run", "start", "--", "--host", "0.0.0.0", "--port", "4200"]
