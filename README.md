### Azure Spring Cloud Stream graceful shutdown

Project contains code to reproduce issue that after expected shutdown of incoming event consumption, events are still being consumed.
Event suppliers are shut down before consumers.

### Running

#### Spring Boot

Update `application-local.yml` with appropriate environment configuration.

```shell
./gradlew bootRun --args='--spring.profiles.active=dev'
```

#### Event generator

Change script with appropriate `connection_str` and `topic_name` value.

```shell
cd scripts
pip3 install azure-servicebus
python3 send_asb_messages_from_file.py shutdown-messages-100.json
```
