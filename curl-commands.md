1. First you need to register a user:

curl  -H 'Content-Type: application/json' --data '{"name":"Mikhail","password":"password"}' http://localhost:8081/register

2. After registration, generate a token:

curl  -H 'Content-Type: application/json' --data '{"name":"Mikhail","password":"password"}' http://localhost:8081/authentication

3. The generated token must be added to "Bearer_" to get the final string in the form of Bearer_{token}.

In the current example, this is the line: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNaWtoYWlsIn0.ELR0wizpR9VvHnZ5r7Pih355wFAZ3B1UcwQqIsQ70T8

4. To add a message from the user, you should get a command in the form:

curl  -H 'Content-Type: application/json' -H 'Authorization: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNaWtoYWlsIn0.ELR0wizpR9VvHnZ5r7Pih355wFAZ3B1UcwQqIsQ70T8' --data '{"name":"Mikhail","message":"text1"}' http://localhost:8081/messages

5. After filling in the table, you can check the unloading of accumulated messages, you can specify "history N" as the message text, where "N" is the number of recent messages of the current user that you need to receive.
The result will be a query like:

curl  -H 'Content-Type: application/json' -H 'Authorization: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNaWtoYWlsIn0.ELR0wizpR9VvHnZ5r7Pih355wFAZ3B1UcwQqIsQ70T8' --data '{"name":"Mikhail","message":"history 10"}' http://localhost:8081/messages
