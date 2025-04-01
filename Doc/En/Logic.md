# Project Logic

[Back](./Help.md)

## Content

1. [Client](#client)
2. [Center](#center)
3. [Professional](#pro)

## Client <a id='client'></a>

### New User Flow

Steps:

1. Visit the website
2. Register
3. Authenticate
4. Describe the problem
5. Select a center
6. Submit request
7. Wait for response
8. Receive response (either text reply or scheduled meeting)

### Existing User Flow

Steps:

1. Visit the website
2. Authenticate
3. Describe the problem
4. Select a center
5. Submit request
6. Wait for response
7. Receive response (either text reply or scheduled meeting)

### Request Submission Logic

User fills out a form with these fields:

1. Problem title (max 25 chars), e.g.: `Depression, abusive husband, need to talk`
2. Problem description
3. Problem occurrence date
4. Problem severity status (priority levels): `EXTRA HIGH, HIGH, MEDIUM, LOW, MINOR`
5. Resolution status: `idle, processing, fulfilled, rejected`

The request goes to the user's selected center.

## Help Center <a id='center'></a>

### Center Workflow Overview

1. Visit the website
2. Authenticate
3. Review requests:
   - 3.1 View clients
   - 3.2 Review problems, assess priority
   - 3.3 Assign professional to client
4. Approve/Reject client request
5. *Is problem resolved?* Mark status as completed

### Center Logic

The center is an entity that assigns a professional to a client and decides how serious the problem is and whether it is worth taking on or not.

When you log in to your personal account, the center looks at the list of all users who have applied. The center assesses the priority of the problem on how quickly it should be solved. The center may reject the problem for some reason. If not, the center assigns a professional to the client. After the problem is solved, the center receives from the professional that the problem is solved and puts the status that the problem is solved.

## Professional <a id='pro'></a>

### Professional Workflow Overview

1. Receive client assignment from Center
2. Review client's problem
3. Send solution message or schedule meeting
4. *Problem resolved?* Send completion status to Center

### Professional Logic

A professional is an entity that will solve the client's problems, give advice, etc.

The professional receives requests from the center he is assigned to. He starts to familiarize himself with the problem and solves it. He can send a letter to the client or make an appointment. If the problem is resolved, sends the completed status to the center.

This table includes the professional's assignments

```sql
CREATE TABLE appointments (
    appointment_id bigserial NOT NULL PRIMARY KEY,
    fk_user_id int NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    fk_professional_id int REFERENCES professionals(professional_id) ON DELETE SET NULL,
    appointment_text text NOT NULL
);
```
