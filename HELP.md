# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0-SNAPSHOT/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0-SNAPSHOT/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0-SNAPSHOT/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

Your task is to write a very simple, transactional, single-threaded, in-memory, key-value
database with a limited set of instructions and provide a REST API to it.
Below there is a description of commands in pseudo-code, which should be exposed via your
API (it's not the required format)
Data commands
SET {name} {value} - set a variable {name} to the value {value}. Both name and value
should match the pattern [0-9A-Za-z]{1,10}. This command can create a new variable or
override the value of the existing one.
GET {name} - return the value stored under the variable {name}. Return null if that variable
name hasn't been set.
DELETE {name} - Delete the variable {name}.
COUNT {value} - return the number of variables equal to {value}. If no values are equal,
this should output 0. This command should execute in O(log n) time or better, where n is the
number of actually stored variables.
Transactional commands
BEGIN - open a transaction block. Transactions could be nested. Each new nested transaction
should take a constant amount of space and must not depend on the number of variables set.
ROLLBACK - rollback all the commands from the most recent transaction block. If there is no
open transaction an exception should be thrown.
COMMIT - store in memory all the operations from all presently open transaction blocks. If
there is no open transaction, nothing should happen.
Any data command executed outside the transaction block is committed immediately.