# ToDo code test app #

## Features ##
  **-implemented with spring boot + java 8**<br>
  **-implemented /tasks, /todo and /integrationTest endpoints**<br>
  **-unit tests implemented**<br>
  **-integration test implemented**<br>
  **-support Hibernate ORM mapping to H2 database**<br>
  **-support swagger code gen plugin**<br>
  **-Maven build**<br>

# start it
  1. ```git clone``` source code from gitHub;
  2. import project into Intellij by open source code directory;
  3. execute ```mvn compile``` to trigger swagger code gen plugin to generate code;
  4. configure "project structure" to add ```target/generated-sources/swagger/src/main/java``` as ```sources```, and choose ```java1.8``` as project SDK;
  5. execute ```mvn clean package```;
  6. start application. It can be started by ```java -jar codeTest-0.1.0.jar```, or by right click ```Application.java``` and click ```Run Application.main()```.
  
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

## Run integration tests against remote API
url:<br>
`/integrationTest`<br>

method:<br>
`get`<br>

request:<br>
`"url=http://localhost:8080"`<br>

return:<br>
```
{
     "bracers": [
         {
             "input": "GET /tasks/validateBrackets?input=([])",
             "result": true,
             "expected": true,
             "isCorrect": true
         },
         {
             "input": "GET /tasks/validateBrackets?input=([)]",
             "result": false,
             "expected": false,
             "isCorrect": true
         }
     ],
     "todo": [
         {
             "input": "POST http://localhost:8090/todo",
             "result": {
                 "id": 1,
                 "text": "sample text",
                 "isCompleted": false,
                 "createdAt": "2018-04-14T17:22:36Z"
             },
             "expected": {
                 "id": 1,
                 "text": "sample text",
                 "isCompleted": false,
                 "createdAt": "2018-04-14T17:21:06Z"
             },
             "isCorrect": true
         },
         {
             "input": "GET http://localhost:8090/todo/1",
             "result": {
                 "id": 1,
                 "text": "sample text",
                 "isCompleted": false,
                 "createdAt": "2018-04-14T17:22:36Z"
             },
             "expected": {
                 "id": 1,
                 "text": "sample text",
                 "isCompleted": false,
                 "createdAt": "2018-04-14T17:21:06Z"
             },
             "isCorrect": true
         },
         {
             "input": "PATCH http://localhost:8090/todo/1",
             "result": {
                 "id": 1,
                 "text": "new text",
                 "isCompleted": true,
                 "createdAt": "2018-04-14T17:22:36Z"
             },
             "expected": {
                 "id": 1,
                 "text": "new text",
                 "isCompleted": true,
                 "createdAt": "2018-04-14T17:22:36Z"
             },
             "isCorrect": true
         }
     ],
     "isCorrect": true
 }
```