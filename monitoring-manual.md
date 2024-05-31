1. Run `docker exec -u 0 -it database bash`
2. Run `psql -U pmm postgres`
3. Run `CREATE EXTENSION pg_stat_monitor;`
4. Go to `https://localhost/graph/`
5. Login: **admin** Password: **admin**
6. Go to services `https://localhost/graph/inventory/services`
7. Add PostgreSQL service
8. Enter any service name
9. Enter contaner IP from the docker network as the hostname
10. Enter Username: **pmm** Password: **pmm**
11. Click `Add service`

Metrics should be under the PostgreSQL tab and under the Query Analitycs (QAN) tab