# Build stage
FROM node:18-alpine AS builder

WORKDIR /app

# Copy package files
COPY services/shop/shop-frontend/package*.json ./

# Install dependencies
RUN npm ci

# Copy source code
COPY services/shop/shop-frontend/ ./

# Build the application
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy built assets from builder stage
COPY --from=builder /app/dist /usr/share/nginx/html

# Copy custom nginx configuration
COPY services/shop/shop-frontend/nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 8080
EXPOSE 8080

# Start nginx
CMD ["nginx", "-g", "daemon off;"]