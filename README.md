# hibernate-sandbox
The purpose of this repository is to familiarize myself with using Hibernate and also to store all the common interview questions related to it.

## Using Hibernate
In order to use Hibernate within our java project , we only really need two dependencies - hibernate-core and jdbc driver for whatever database we want to use (H2 in this case)
```
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.5.2.Final</version>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>2.2.222</version>
        </dependency>
```
## Common Interview questions - 
### 1) What's the difference between JDBC and Hibernate ?

| JDBC  | Hibernate |
| ------------- | ------------- |
| JDBC is a java API that allows us to connect to a database and execute queries on it.  | Hibernate is an ORM that helps us to map Java objects to database tables and is an implementation of the JPA specification. |
| JDBC queries are database specific.  | Hibernate queries are not database specific. We can change to a different database and if we specify the proper dialect for the db we are using , hibernate will be able to get the data that we need. |
| JDBC does not support caching. | Hibernate supports caching. |

### 2) SessionFactory vs Session ?
| SessionFactory  | Session |
| ------------- | ------------- |
| It's a factory Object whose only purpose is to provide us with the session object.  | We can use this object to perform CRUD operations on our database. |
| Heavy weight Object , ideally initialised only once per database.  | Light weight object , can be initialised multiple times as per our need. |
| Can be utilized by multiple threads. | Can be used by only one thread at a time.|

### 3) What is the equivalent object of Session in JPA ? 
EntityManager - it's an interface with methods like persist(), find() , getReference() etc.

### 4) What is Persistance context in JPA/Hibernate ?
- The persistence context is the *first-level cache* where all the entities are fetched from the database or saved to the database.
- It keeps track of all entity states and does the commit only once when we are done changing the object.
- An entity object can be in one of the three states mentioned below with respect to a given persistance context -
  - transient - not associated with the persistant context.
  - persistance - associated with the current persistant context.
  - detached - previously persistent in another session, but not currently associated with _this_ persistence context.

### 5)  Difference between get() and load() method in hibernate ?
| get | load |
| ------------- | ------------- |
| When we use this method , it immediately makes a call to the database and gets the data that we need. If there is no data present in db with the primary key we have given , it will return a null value to us. | This method does not immediately make the query to the db , it is lazy in nature.  It returns an ObjectNotFoundException if there is no data available in the db.|

For load() method , consider the following example - 
```java
Song song = session.load(Song.class,1); 
```
At this point a fake/proxy object is assigned to the variable song.When we actually need this song variable in our code is when the call to the db will be executed. For example -
```java
if(song.equals("hey jude")){
// some operation
}
```
### 6) What is a Transient state in JPA/Hibernate ?
Any entity object that is not managed by the persistant context/session is said to be in transient state.

### 7) How do we change the state of an entity object to detached without closing the current session ?
Using session.evict() 
