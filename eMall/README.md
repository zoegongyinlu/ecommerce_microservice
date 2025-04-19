

Deploy nacos on container

```bash

docker run -d --name nacos  --network hm-net  --env-file /Users/zoegong/Projects/nacoscustom.env  -p 8848:8848 -p 9848:9848 -p 9849:9849  --restart=always nacos/nacos-server:v2.1.0-slim
```