# Flight Schedule  Using lufthansa Api

<img src="https://github.com/kevinjam/Flight-Schedule/blob/master/art/splash.jpg" height="450">|<img src="https://github.com/kevinjam/Flight-Schedule/blob/master/art/main.jpg" height="450">|<img src="https://github.com/kevinjam/Flight-Schedule/blob/master/art/selectFly.jpg" height="450"><img src="https://github.com/kevinjam/Flight-Schedule/blob/master/art/map.jpg" height="450">
<img src="https://github.com/kevinjam/Flight-Schedule/blob/master/art/main_before.jpg" height="450"><img src="https://github.com/kevinjam/Flight-Schedule/blob/master/art/calendar.jpg" height="450">

## Getting Started

Below instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

First off, you require the latest [Android Studio 3.0.0 (or newer)](https://developer.android.com/studio) to be able to build the app.

### Installing
Clone the Repository {Master Branch}

### Setting lufthansa API key
You need to supply Api key of [LUFTHANSA](https://developer.lufthansa.com).

### The project a Splash Screen makes a call to [lufthansa]  Access to our services is controlled via tokens (Oauth 2.0)


`
To get a token you must make a server request token end-point and supply your client key and client secret. Tokens remain valid for a limited time.
`
`auth response {
  "access_token": "cn6wdck6z66u4msdfx2gtgbx",
   "expires_in": 129600,
    "token_type": "bearer"
  }`

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
Copyright (C) 2019 Kevin Janvier Chinabalire

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```