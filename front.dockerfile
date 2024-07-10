FROM nginx:alpine
COPY html /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]