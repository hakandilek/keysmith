@echo off

start java -Xms128m -Xmx256m -jar keysmith-server\target\keysmith-server-0.2.2-SNAPSHOT.jar server server-config.yml

