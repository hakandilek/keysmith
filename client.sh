#!/bin/bash

java -Xms128m -Xmx256m -jar keysmith-client/target/keysmith-client-0.2.0-SNAPSHOT.jar generate client-config.yml

java -Xms128m -Xmx256m -jar keysmith-client/target/keysmith-client-0.2.0-SNAPSHOT.jar send client-config.yml

java -Xms128m -Xmx256m -jar keysmith-client/target/keysmith-client-0.2.0-SNAPSHOT.jar read client-config.yml
