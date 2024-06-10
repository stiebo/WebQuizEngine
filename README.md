# SpringBoot Rest API Web Quiz Engine

## Background And Task
<p>In the Internet, you can often find sites where you need to answer some questions. It can be educational sites, sites with psychological tests, job search services, or just entertaining sites like web quests. The common thing for them is the ability to answer questions (or quizzes) and then see some results. In this project, you will create a complex web service and learn about REST API, an embedded database, security, and other technologies.</p>

Link to the project: https://hyperskill.org/projects/91

Check out my profile: https://hyperskill.org/profile/500961738

# REST API Documentation
All documentation retrieved from https://hyperskill.org/projects/91, provided by JetBrains Academy.

Documentation provides an overview of the endpoints, password requirements and other documentation related to the REST API.

## Users and Authorization
To perform any operations with quizzes (create, solve, get one, get all, delete), the user has to be registered and then authorized via HTTP Basic Auth by sending their registered email and password for each request.

## Endpoints

### Registration

#### POST /api/register

Allows users to register on the service.

Request Body:
```json
{
  "email": "<username>@<domain>.<extension>",
  "password": "<string, at least 5 characters long>"
}
```

Response (200 OK)

### Quiz Engine

#### POST /api/quizzes

Create a new quiz.

Request Body:
```json
{
  "title": "<string, not null, not empty>",
  "text": "<string, <not null, not empty>",
  "options": ["<string 1>","<string 2>","<string 3>", ...],
  "answer": [<integer>,<integer>, ...]
}
```
Response (200 OK):
```json
{
  "id": <integer>,
  "title": "<string>",
  "text": "<string>",
  "options": ["<string 1>","<string 2>","<string 3>", ...]
}
```

#### GET /api/quizzes/{id}

Get a quiz by its id.

Response (200 OK):
```json
{
  "id": <integer>,
  "title": "<string>",
  "text": "<string>",
  "options": ["<string 1>","<string 2>","<string 3>", ...]
}
```

#### GET /api/quizzes/completed?page={number}

Get all existing quizzes in pages.

```json
{
  "totalPages":1,
  "totalElements":3,
  "last":true,
  "first":true,
  "sort":{ },
  "number":0,
  "numberOfElements":3,
  "size":10,
  "empty":false,
  "pageable": { },
  "content":[
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>","<string>","<string>", ...]},
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>", "<string>", ...]},
    {"id":<quiz id>,"title":"<string>","text":"<string>","options":["<string>","<string>", ...]}
  ]
}
```

#### DELETE /api/quizzes/{id}

Allow a user to delete a quiz if they are the creator.

Response (204 NO CONTENT): operation successful

If the specified quiz does not exist, the server returns 404 (NOT FOUND). If the specified user is not the author of this quiz, the response is the 403 (FORBIDDEN) status code.

#### POST /api/quizzes/{id}/solve

Try to solve a quiz.

Request Body:
```json
{
  "answer": [<integer>, <integer>, ...]
}
```
Response (200 OK):

If the passed answer is correct:
```json
{
  "success":true,
  "feedback":"Congratulations, you're right!"
}
```

If the answer is incorrect:
```json
{
  "success":false,
  "feedback":"Wrong answer! Please, try again."
}
```
#### GET /api/quizzes/completed?page={number}

Provides the specified part of all completions of quizzes for the authenticated user.

Response (200 OK):
```json
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"},
    {"id":<quiz id>,"completedAt":"<date_time>"}
  ]
}
```

## Data storage
The service includes an H2 file database for all data storage.

## Tests
Code tests were performed as part of the Hyperskill project with 200+ tests passed. See https://hyperskill.org/projects/91