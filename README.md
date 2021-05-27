# crawl-corporate

```
git clone https://github.com/minhdatplus/crawl-corporate
```

Thay đổi đường dẫn trong file application.properties với đường dẫn của database và fundametal-service đã triển khai phía trên

```
url.fundamental.host=<fundamental_service_url>

spring.datasource.url=jdbc:oracle:thin:@<database_url>:1521:xe
```

Sau đó tiến hành build docker image theo lệnh bên dưới
```
docker build -t corporate-action-service:1.0 .

docker run -it -d -p 8080:8080 corporate-action-service:1.0 .
```

Truy cập API Document tại đường dẫn
```
http://localhost:8080/corporate/swagger-ui.html#/
