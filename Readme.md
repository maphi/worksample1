## Session Service - Work sample

Webservice using only akka + akka http to provide a RESTful CRUD interface for a JSON based session service.

### Demonstrating

* distributing load with actors and akka http
* easily extendable with other repository implementations (e.g. SQL/NoSQL Database for persistence) by implementing SessionRepo trait and injecting it in Main (thin cake pattern)
* JSON marshalling/unmarshalling with custom types
* easy validation using case classes and require
* holding state with actors

### Usage

* run with ***"sbt run"*** (binds to localhost:8080)
* test with ***"sbt test"***

### REST Endpoints

* POST /v1/sessions - add a sessions
* GET /v1/sessions/<id> - get a sessions by id
* PUT /v1/sessions/<id> - update a sessions (specify any attribute combination, e.g. only userName)
* DELETE /v1/sessions/<id> - delete a sessions

### Data Structure

```JSON
  {
    "userName": "userName",
    "email": "user@example.org",
    "address": {
      "street": "Musterstrasse 1",
      "postalCode": 40000,
      "city": "Musterstadt"
    },
    "gender": "male",
    "basket": [
      {"id": "prodId1", "name": "iPhone 7"},
      {"id": "prodId2", "name": "iPhone 7 Case"}
    ]
  }
```

Restrictions/Validation:

* ***userName***, ***basketItem.name*** must be at least 3 characters long
* ***postalCode*** must be between 1 and 99999
* ***gender*** must be male/female (case insensitive)

### Limitations

* no persistence in case of restart/crash (actors hold all state)
