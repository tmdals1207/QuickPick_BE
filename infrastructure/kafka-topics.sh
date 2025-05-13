kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic ticket.purchase
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic ticket.purchase --partitions 3