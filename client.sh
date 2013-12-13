#!/bin/bash

java -Xms128m -Xmx256m -jar keysmith-client/target/keysmith-client-0.2.3-SNAPSHOT.jar generate client-config.yml

java -Xms128m -Xmx256m -jar keysmith-client/target/keysmith-client-0.2.3-SNAPSHOT.jar send client-config.yml

java -Xms128m -Xmx256m -jar keysmith-client/target/keysmith-client-0.2.3-SNAPSHOT.jar read client-config.yml
