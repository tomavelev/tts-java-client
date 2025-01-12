**Spring Boot Demo App: TTS Audio Generation from flask python backend**

**Overview**
-----------

This Spring Boot demo app integrates with a Flask app that generates audio from request parameters. The Flask app
accepts HTTP requests with specific parameters and produces an audio file in response.

**Features**
------------

* **Audio Generation**: The Flask app generates an audio file based on the provided request parameters.
* **Spring Boot Integration**: This Spring Boot service that integrates with the Flask app, allowing dev users to send HTTP requests
  with parameters and receive an audio file in response 

**Requirements**
---------------

* **Java 21**: The Spring Boot app is built using Java 11.
* **Spring Boot 3.4.1**: The Spring Boot app uses version 3.4.1 of the Spring Boot framework.
* **Flask App**: The Flask app is a separate project that generates audio from request parameters.

**Setup**
---------

To set up the Spring Boot demo app, follow these steps:

1. Clone this repository to your local machine.
2. Install the required dependencies using Maven or Gradle (depending on your project structure).
3. Install the Python Dependencies
4. Configure the `application.properties` file to point to the Flask app's URL.
5. Execute `py server.py`
6. Run the Spring Boot app using your preferred method (e.g., `mvn spring-boot:run` or `gradle bootRun`).

**python API Endpoints**
-----------------

### 1. Generate Audio

#### POST /generate-audio

Generate an audio file from a given text input.

**Request Body**

* `text`: The text to be converted into an audio file.
* `fileName`: The name of the output audio file.
* `language` (optional): The language of the text. Defaults to English if not provided. It must be 3-letter
  code https://en.wikipedia.org/wiki/ISO_639-3, and it must have a Text to Speech
  model (https://huggingface.co/facebook/mms-tts#model-details)

**Response**

* A WAV audio file with the specified name and content generated from the input text.

### 2. Delete File

#### POST /delete_file

Delete a generated audio file by its name.

**Request Body**

* `fileName`: The name of the file to be deleted.

**Response**

* A JSON object with a success message if the deletion is successful.
* A JSON object with an error message if something goes wrong.

### 3. Clean Resources

#### POST /clean_resources

Clean up resources used by the API. Meant to be called after long time of no requests

**No Request Body**

* This endpoint does not require any input.
* It is used to free up resources when the API is no longer in use.

**License**
----------

This project is licensed under the MIT License. See `LICENSE.txt` for details.


**Acknowledgments**
-------------------

The python code for the Text to Speech located in [server.py](server.py) is greatly looked up
from https://github.com/rsxdalv/tts-generation-webui. The Language Model used
is [MMS-TTS](https://huggingface.co/facebook/mms-tts)


