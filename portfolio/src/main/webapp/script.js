// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function addMessage() {
  fetch('/data').then(response => response.json()).then((message) => {
    
    const messageList = document.getElementById('past-messages');
    messageList.innerHTML = '';
    console.log(message[0]);
    for (var i =0; i < message.length; i++){ 
     messageList.appendChild(createListElement(message[i]));
    }
    var currentCount = findCurrentCommentIndex(message);
    const currentMessage = document.getElementById('current-message');
    currentMessage.innerHTML = '';
    if(message.length == 0){
      currentMessage.appendChild(createListElement("No new comments"))
    }
    else{
      currentMessage.appendChild(createListElement(message[message.length-1]));
    }
    
  });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

function findCurrentCommentIndex(array){
  var index;
  for(var i = array.length-1; i >= 0; i--){
      if(array[i] != null){
        index = i;
      }
  }
  return index;
}