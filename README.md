# Flight Schedule  Using lufthansa Api

<img src="https://github.com/kevinjam/Flight-Schedule/blob/master/art/main.jpg" height="500">| <img src="https://github.com/kevinjam/Flight-Schedule/blob/master/art/selectFly.jpg" height="500">

## Getting Started

Below instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

First off, you require the latest [Android Studio 3.0.0 (or newer)](https://developer.android.com/studio) to be able to build the app.

### Installing
Clone the Repository

### Setting lufthansa API key
You need to supply Api key of [LUFTHANSA](https://developer.lufthansa.com).

### The project a Splash Screen where we do a call with [lufthansa]  Access to our services is controlled via tokens (Oauth 2.0)


`
To get a token you must call our token end-point and supply your client key and client secret. Tokens remain valid for a limited time.
`

When you obtain the client_id,client_secret,grant_type Provide it to the app by putting the following in the
`Common/Constants` Replace the above with your own credentials:

```
# replace 'copy paste key here'
LUFTHANSA CREDENTIALS="copy paste key here"

```
### Versioning

Using git

## License

```
Copyright 2019 Kevin Janvier
```