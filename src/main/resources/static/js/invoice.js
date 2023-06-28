/*
1. The `viewInvoice` function takes an `orderId` parameter.
2. It uses the `fetch` function to send a POST request to the `/order/viewInvoice` endpoint.
3. The request includes the `orderId` as the request body.
4. The `Content-Type` header is set to `application/json` to indicate that the request body is in JSON format.
5. The response from the server is received in the first `.then()` block.
6. It checks if the response was successful (`response.ok`). If it is, it proceeds to the next `.then()` block.
7. In the next `.then()` block, the response body is converted to a `Blob` object using the `response.blob()` method. A `Blob` represents immutable raw data.
8. A temporary URL for the `Blob` is created using `URL.createObjectURL(blob)`. This URL represents the content of the `Blob`.
9. A link element (`<a>`) is created using `document.createElement('a')`.
10. The `href` attribute of the link is set to the temporary URL, and the `download` attribute is set to `${orderId}.pdf`. This sets the filename for the downloaded file.
11. A simulated click event is triggered on the link using `link.click()`. This initiates the download of the file.
12. After the download completes, the temporary URL is cleaned up using `URL.revokeObjectURL(url)`. This frees up memory resources associated with the temporary URL.
13. Any errors that occur during the process are caught in the `.catch()` block and logged to the console.

In summary, the code sends a POST request to the server, receives a PDF file in response, converts it to a `Blob` object, and initiates the download of the file in the browser by creating a temporary URL and simulating a click on a dynamically created link element.



*/
function viewInvoice(orderId) {
  fetch('/order/viewInvoice', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: orderId
  })
    .then(response => {
      if (response.ok) {
        return response.blob();
      } else {
        throw new Error('Error sending order ID');
      }
    })
    .then(blob => {
      // Create a temporary URL for the blob
      const url = URL.createObjectURL(blob);

      // Create a link element and set its attributes
      const link = document.createElement('a');
      link.href = url;
      link.download = `${orderId}.pdf`;

      // Simulate a click event on the link to start the download
      link.click();

      // Clean up the temporary URL
      URL.revokeObjectURL(url);
    })
    .catch(error => {
      console.error('An error occurred while sending the order ID:', error);
    });
}
