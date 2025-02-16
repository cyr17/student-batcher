# API Usage Documentation

This API provides functionality for managing users, subjects, and groups.

## User

  ### Endpoint: `/api/user`
  - **Methods**: `GET`, `POST`
  - **Description**: 
    - `GET`: Retrieve all user information.
    - `POST`: Create a new user.
  - **Expected Output**:
    - `GET`: Returns all user details in JSON format.
    - `POST`: Returns the created user object.

  ### Endpoint: `/api/user/{id}`

  - **Methods**: `GET`, `PUT`, `DELETE`
  - **Description**: 
    - `GET`: Retrieve user information for the id requested.
    - `PUT`: Update existing user information.
    - `DELETE`: Remove a user.

  - **Expected Output**:
    - `GET`: Returns the user details in JSON format.
    - `PUT`: Returns updated user information.
    - `DELETE`: Returns a success message.

## Subject
  ### Endpoint: `/api/subjects`
   - **Methods**: `GET`, `POST`
   - **Description**: 
     - `GET`: Retrieve all subject information.
     - `POST`: Create a new subject.
   - **Expected Output**:
     - `GET`: Returns all subject details in JSON format.
     - `POST`: Returns the created subject object.

 ### Endpoint: `/api/subjects/{id}`
  - **Methods**: `GET`, `PUT`, `DELETE`
  - **Description**: 
    - `GET`: Retrieve subject information for the id requested.
    - `PUT`: Update existing subject information.
    - `DELETE`: Remove a subject.

  - **Expected Output**:
     - `GET`: Returns the subject details in JSON format.
     - `PUT`: Returns updated subject information.
     - `DELETE`: Returns a success message.

## Group
  ### Endpoint: `/api/groups`
   - **Methods**: `GET`, `POST`,
   - **Description**: 
        - `GET`: Retrieve all group information.
        - `POST`: Create a new group.
   - **Expected Output**:
        - `GET`: Returns all group details in JSON format.
        - `POST`: Returns the created group object.

 ### Endpoint: `/api/groups/{id}`
   - **Methods**: `GET`, `PUT`, `DELETE`
   - **Description**: 
        - `GET`: Retrieve group information for the id requested.
        - `PUT`: Update existing group information.
        - `DELETE`: Remove a group.

   - **Expected Output**:
        - `GET`: Returns the group details in JSON format.
        - `PUT`: Returns updated group information.
        - `DELETE`: Returns a success message.

  ### Endpoint: `/api/groups/match`
   - **Methods**: `GET`
   - **Description**: 
        - `GET`: Retrieve potential groups
   - **Expected Output**:
        - `GET`: Returns a list of potential groups ( list of users in JSON format) .

  ### Endpoint: `/api/groups/allocate`
   - **Methods**: `GET`
   - **Description**: 
        - `GET`: Create potential groups in db , while not adding duplicates
   - **Expected Output**:
        - `GET`: Returns a list of group objects that are successfully created in the db .
