# Sample Application Working with Kafka Using Protobuf and Avro

## Set up Kafka

Serialize and deserialize messages in protobuf and avro has a dependency on Kafka schema registry, which does not come with Kafka installer by default.
One easy way is to use the Docker images. The original author provides a Docker compose script here: https://github.com/codingharbour/kafka-docker-compose.

To set up Docker, one easy way is to simply [install Docker Desktop](https://docs.docker.com/desktop/), which comes with Docker Compose.
After that, do the following:
```text
git clone git@github.com:codingharbour/kafka-docker-compose.git
cd single-node-avro-kafka
docker-compose up -d
```
Now, you have Kafka, Zookeeper and Schema Registry up and running, `docker-compose ps` shows the running containers:
```text
➜  single-node-avro-kafka git:(master) docker-compose ps
NAME                  COMMAND                  SERVICE             STATUS              PORTS
sna-kafka             "/etc/confluent/dock…"   kafka               running             0.0.0.0:9092->9092/tcp, :::9092->9092/tcp
sna-schema-registry   "/etc/confluent/dock…"   schema-registry     running             0.0.0.0:8081->8081/tcp, :::8081->8081/tcp
sna-zookeeper         "/etc/confluent/dock…"   zookeeper           running             0.0.0.0:2181->2181/tcp, :::2181->2181/tcp, 2888/tcp, 3888/tcp
```

## Create New Topics
We need to use two new topics: `protobuf-topic`, `avro-topic`. To create them:
```text
➜  single-node-avro-kafka git:(master) docker exec -it sna-kafka /usr/bin/kafka-topics --create --topic protobuf-topic --bootstrap-server localhost:9092
Created topic protobuf-topic.
➜  single-node-avro-kafka git:(master) docker exec -it sna-kafka /usr/bin/kafka-topics --create --topic avro-topic --bootstrap-server localhost:9092
Created topic avro-topic.
```

## Produce and Consume Protobuf Messages

## Produce and Consume Avro Messages

## References
   * [Original repo by chromy96](https://github.com/codingharbour/kafka-protobuf)
   * [Original tutorial by chromy96 overing protobuf](https://codingharbour.com/apache-kafka/how-to-use-protobuf-with-apache-kafka-and-schema-registry/)
   * [Original Docker Kafka setup with schema registry](https://github.com/codingharbour/kafka-docker-compose)