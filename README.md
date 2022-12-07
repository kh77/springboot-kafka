# Spring Boot - Kafka

- There is a scheduler for every producer in producer-project, just change cron time to play around and see the logs in the consumer
- Create topics in kafka using below kafka command
- Docker compose file to run kafka


### Example 1: Simple Json is being passed and consume

  * MagicNumberProducer, MagicNumberConsumer


### Example 2: Using OrderErrorHandler to handle any kind of exception in this consumer and GlobalErrorHandler will also be fired

  * OrderProducer, OrderConsumer, OrderErrorHandler, GlobalErrorHandler


### Example 3: will retry 3 times and if it is still failed then send to DLQ t-invoice-dead topic , blocking retry

  * InvoiceProducer, InvoiceConsumer


### Example 4: Filter out the message in the consumer level

  * CarLocationProducer, CarLocationConsumer


### Example 5: Manually Pause/Start consumer from the code based upon consumer id

  * GeneralLedgerProducer, GeneralLedgerConsumer, GeneralLedgerScheduler


### Example 6: Non-blocking-retry , Retry 2 times and if it fails then it will go to DLQ

  * ImageProcessProducer, ImageProcessConsumer


    - Start Kafka:

    docker-compose -f docker-compose-core.yml -p core up -d

    
    - Stop Kafka:
        
    docker-compose -f docker-compose-core.yml -p core down -d


    - Terminal of kafka bash
    
    docker exect -it kafka bash

    
    - Create Topic:

    kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 1 --topic t-magic-number
    kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 1 --topic t-order
    kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 2 --topic t-image-process
    kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 2 --topic t-image-process-dead
    kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 2 --topic t-invoice
    kafka-topics.sh --bootstrap-server localhost:9092 --create --replication-factor 1 --partitions 2 --topic t-invoice-dead


    - List of Topic:
    
    kafka-topics.sh --bootstrap-server localhost:9092 --list


    - Describe Topic:
    
    kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic t-magic-number


    - Listen Consumer Message:
    
    kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t-invoice-dead


    - Describe Consumer:

    kafka-consumer-groups.sh --describe --bootstrap-server localhost:9092 --group default-spring-consumer



