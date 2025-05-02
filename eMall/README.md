

Deploy nacos on container

```bash

docker run -d --name nacos  --network hm-net  --env-file /Users/zoegong/Projects/nacoscustom.env  -p 8848:8848 -p 9848:9848 -p 9849:9849  --restart=always nacos/nacos-server:v2.1.0-slim
```

Seata

```bash
docker stop seata
docker rm seata
docker run --platform linux/arm64/v8 --name seata -p 8099:8099 -p 7099:7099 -e SEATA_IP=127.0.0.1 -v /Users/zoegong/aMall/seata:/seata-server/resources --privileged=true --network hm-net -d seataio/seata-server:1.6.0

```