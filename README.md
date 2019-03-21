# Account transfer Coding Task for Revolut Interview

### Run

# To run project with maven execute

```
mvn clean install

mvn clean compile exec:java
```

#Api's can be tested by PostMan (Rest Api Tool)

## Testing with curl XML

#create new accounts

```
curl -i -X POST -H "Content-Type:application/xml" http://localhost:8080/revolut/api/account/create -d ""
curl -i -X POST -H "Content-Type:application/xml" http://localhost:8080/revolut/api/account/create -d ""
```
# Check Balance

```
curl -i http://localhost:8080/revolut/api/account/1/balance
curl -i http://localhost:8080/revolut/api/account/2/balance
```

# deposit on account id=1 amount = 20

```
curl -i -X PUT http://localhost:8080/revolut/api/account/1/deposit -H "Content-Type: application/xml" -d "<money><amount>20</amount></money>"
```

# withdraw from account id=1 amount =5

```
curl -i -X PUT http://localhost:8080/revolut/api/account/1/withdraw -H "Content-Type: application/xml" -d "<money><amount>5</amount></money>"
```
# transfer from account id=1 to account id=2 amount =3

```
curl -i -X PUT http://localhost:8080/revolut/api/transfer -H "Content-Type: application/xml" -d "<transferData><amount>1</amount><from>1</from><to>2</to></transferData>"
```
## Testing with curl JSON

#create new accounts

```
curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/revolut/api/account/create -d "{}"
curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/revolut/api/account/create -d "{}"
```

# Check Balance

```
curl -i http://localhost:8080/revolut/api/account/1/balance
curl -i http://localhost:8080/revolut/api/account/2/balance
```

# deposit on account id=1 amount = 20

```
curl -i -X PUT http://localhost:8080/revolut/api/account/1/deposit -H "Content-Type: application/json" -d "{\"amount\":\"20\"}"
```

# withdraw from account id=1 amount =5

```
curl -i -X PUT http://localhost:8080/revolut/api/account/1/withdraw -H "Content-Type: application/json" -d "{\"amount\":\"4.25\"}"
```

# transfer from account id=1 to account id=2 amount =3

```
curl -i -X PUT http://localhost:8080/revolut/api/transfer -H "Content-Type: application/json" -d "{\"amount\":\"3\",\"from\":\"1\",\"to\":\"2\"}"
```
