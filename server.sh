#!/bin/bash

java -Xms128m -Xmx256m -jar keysmith-messenger/target/keysmith-messenger-0.1.0-SNAPSHOT.jar server messenger-config.yml &

java -Xms128m -Xmx256m -jar keysmith-server/target/keysmith-server-0.1.0-SNAPSHOT.jar server server-config.yml &

