# Doc
Översiktligt diagram:
https://docs.google.com/drawings/d/1CXgOUZJbhQP6pSPz5kCRPMUCS8q9Txd3o_xxKVDOg6E/edit?usp=sharing

# Startup

~/java/confluent/confluent-3.3.0/bin/kafka-topics --zookeeper localhost 2191 --list
~/java/confluent/confluent-3.3.0/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic loan-app-response --from-beginning
