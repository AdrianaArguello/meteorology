# Meteorology
To run the project, use:
`docker compose up`

```
Attaching to meteorology-app, redis-server
redis-server     | 1:C 23 Feb 2025 21:48:23.117 * oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
redis-server     | 1:C 23 Feb 2025 21:48:23.117 * Redis version=7.4.2, bits=64, commit=00000000, modified=0, pid=1, just started
redis-server     | 1:C 23 Feb 2025 21:48:23.117 # Warning: no config file specified, using the default config. In order to specify a config file use redis-server /path/to/redis.conf                                                                                                                                       
redis-server     | 1:M 23 Feb 2025 21:48:23.118 * monotonic clock: POSIX clock_gettime
redis-server     | 1:M 23 Feb 2025 21:48:23.119 * Running mode=standalone, port=6379.
redis-server     | 1:M 23 Feb 2025 21:48:23.120 * Server initialized                                                                                          
redis-server     | 1:M 23 Feb 2025 21:48:23.121 * Ready to accept connections tcp                                                                             
meteorology-app  | 2025-02-23 21:48:24.040 [main] INFO  io.ktor.server.Application - Autoreload is disabled because the development mode is off.              
meteorology-app  | 2025-02-23 21:48:24.830 [redisson-netty-2-27] INFO  o.r.c.p.MasterPubSubConnectionPool - 1 connections initialized for redis/172.18.0.2:6379
meteorology-app  | 2025-02-23 21:48:24.957 [main] INFO  io.ktor.server.Application - Application started in 1.142 seconds.
meteorology-app  | 2025-02-23 21:48:24.985 [DefaultDispatcher-worker-3] INFO  io.ktor.server.Application - Responding at http://0.0.0.0:8080
```
## Query this Endpoint

http://localhost:8080/weather?query=santiago

The query parameter refers to the location. This resource will provide weather information for the requested location.
