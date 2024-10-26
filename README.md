# bookmarksample
jersey-examples-bookmark with jetty(gretty)

## Overview
jersey-examples-bookmark uses JTA and is thought to be intended to run on EJB containers such as Glashfish.
I want to run this sample in jetty without using libraries such as OpenEJB.

I implemented each function using the following:
* JTA - Atomikos
* JPA - EclipseLink
* PersistenceUnit annotation - Jersey Inject HK2

## How to Run

```sh
$ cd bookmarksample
$ gradle appRun
```
open browser http://localhost:8080/bookmarksample/users/

## Create User

```sh
$ curl -i -X PUT -H "Content-Type:application/json" -d "{\"userid\":\"1\",\"username\":\"tm000\",\"password\":\"abcdef\",\"email\":\"tm000@email.com\"}" http://localhost:8080/bookmarksample/users/1
```

## Create Bookmarks

```sh
$ curl -i -X POST -H "Content-Type:application/json" -d "{\"uri\":\"company\",\"updated\":\"2024-10-17T01:23:45\",\"ldesc\":\"1\",\"sdesc\":\"2\"}" http://localhost:8080/bookmarksample/users/1/bookmarks
```