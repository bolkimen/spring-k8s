https://hub.docker.com/r/apache/kafka

Open a shell in the broker container:

docker exec --workdir /opt/kafka/bin/ -it broker sh
A topic is a logical grouping of events in Kafka. From inside the container, create a topic called test-topic:

./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic test-topic --partitions 40
Write two string events into the test-topic topic using the console producer that ships with Kafka:

./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test-topic
This command will wait for input at a > prompt. Enter hello, press Enter, then world, and press Enter again. Enter Ctrl+C to exit the console producer.

Now read the events in the test-topic topic from the beginning of the log:

./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning

./kafka-topics.sh --bootstrap-server localhost:9092 --list
./kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic 123


