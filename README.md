Create the database user


```
CREATE USER 'maestro'@'localhost' IDENTIFIED BY 'maestro-dev';
GRANT ALL PRIVILEGES ON maestro.* TO 'maestro'@'localhost' WITH GRANT OPTION;
CREATE USER 'maestro'@'%' IDENTIFIED BY 'maestro-dev';
GRANT ALL PRIVILEGES ON maestro.* TO 'maestro'@'%' WITH GRANT OPTION;
```

Create the table: 
``````





