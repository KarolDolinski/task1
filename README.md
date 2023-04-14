
# Application run 

In order to run Application:
./gradlew bootRun

# API Docs

# Endpoints

Request


// Creates a data set

POST /data/

takes DataRequest in param. Request is being validated

GET /data/{key}

gets value of a key regardless if transaction in place or not

DELETE /data/{key}

removes value with given key in current transaction or directly from db

GET /data/{key}/count

counts value for given key in current transaction or directly from db


// Transaction

POST /data/transaction

Creates a new transaction 


POST /data/transaction/commit

Applies last transaction in progress onto db

POST /data/transaction/rollback

Removes last transaction in progress



