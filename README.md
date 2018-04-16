# ToDo code test app #

## Features ##
  **-implemented with spring boot + java 8**<br>
  **-support /tasks, /todo and /integrationTest endpoints**<br>
  **-support unit tests**<br>
  **-passed integration test**<br>
  **-support JPA ORM mapping to H2 database**<br>
  **-support swagger code generation plugin**<br>
  **-Maven build**<br>

## Deploy base url:
  **http://ec2-54-89-237-241.compute-1.amazonaws.com:8090**

# start it
  1. ```git clone``` source code from gitHub;
  2. import project into Intellij by open source code directory;
  3. execute ```mvn compile``` to trigger swagger code gen plugin to generate code;
  4. configure "project structure" to add ```target/generated-sources/swagger/src/main/java``` as ```sources```, and choose ```java1.8``` as project SDK;
  5. execute ```mvn clean package```;
  6. start application. It can be started by ```java -jar codetest-0.1.0.jar```, or by right click ```Application.java``` and click ```Run Application.main()```.
  
# endpoints:  

## Create a to do item
url:  <br>
`/todo`<br>

method:<br>
`post`<br>

request:<br>
`{
  "text": "Text to be created."
}`

return:
```
{
    "id": 1,
    "text": "Text to be created.",
    "isCompleted": false,
    "createdAt": "2018-04-14T04:02:01Z"
}
```

## Retrieve a specific item by id
url:<br>
`/todo/{id}`<br>

method:<br>
`get`<br>

request:<br>

return:
```
{
    "id": 1,
    "text": "Text to be created.",
    "isCompleted": false,
    "createdAt": "2018-04-14T04:02:01Z"
}
```

## Modify an item
url:<br>
`/todo/{id}`<br>

method:<br>
`patch`<br>

request:<br>
```
{
  "text": "Text to be updated.",
  "isCompleted": true
}
```

return:
```
{
    "id": 1,
    "text": "Text to be updated.",
    "isCompleted": true,
    "createdAt": "2018-04-14T04:02:01Z"
}
```
<br>

## Checks if brackets in a string are balanced
url:<br>
`/validateBrackets`<br>

method:<br>
`get`<br>

request:<br>
`"input=wfs'fs[{{{(s;dkls(dslkf)s;dlkf}]}]}}}sd"`<br>

return:
```
{
     "input": "wfs'fs[{{{(s;dkls(dslkf)s;dlkf}]}]}}}sd",
     "isBalanced": false
 }
```