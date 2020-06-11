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
function addMessage() { //eslint-disable-line
  fetch('/data').then((response) => response.json()).then((messages) => {
    const messageList = document.getElementById('past-messages');
    messageList.innerHTML = '';
    if (messages == null) {
      console.log('no information yet');
    } else {
      messages.forEach((element) => {
        messageList.appendChild(createListElement(element));
      });
    }
    const currentMessage = document.getElementById('current-message');
    currentMessage.innerHTML = '';
    if (messages.length == 0) {
      currentMessage.appendChild(createListElement('No new comments'));
    } else {
      currentMessage.appendChild(createListElement(messages[0]));
    }
  });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
