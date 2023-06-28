

function deleteImage(event, imageId) {
  event.preventDefault();
  var images = document.getElementById("images");
  var imageToRemove = document.getElementById("image-" + imageId);
  if (imageToRemove !== null) { // Check if element exists before removing
    images.removeChild(imageToRemove);
  }
  var input = document.createElement("input");
  input.setAttribute("type", "hidden");
  input.setAttribute("name", "imagesToRemove");
  input.setAttribute("value", imageId);
  images.appendChild(input);
}


function deleteNewImage(event, fileName) {
  event.preventDefault();
  var imagesContainer = document.getElementById("images");

  var imageToRemove = document.querySelector("[data-filename='" + fileName + "']");

  if (imageToRemove !== null) {
    imagesContainer.removeChild(imageToRemove);

    // Remove the image from the hidden input value
    var input = document.querySelector("input[data-filename='" + fileName + "']");

    if (input !== null) {
      input.parentNode.removeChild(input);
    }
  }
}





    function enableEdit() {
      var fields = document.getElementsByClassName("editable-field");
      for (var i = 0; i < fields.length; i++) {
        fields[i].disabled = false;
        fields[i].hidden = false;
      }
      document.getElementById("add-image-btn").style.display = "block";
      document.getElementById("save-btn").style.display = "block";
      document.getElementById("cancel-btn").style.display = "block";
      document.getElementById("edit-btn").style.display = "none";


    }

    function handleImageUpload(event) {
  var files = event.target.files;
  var imagesContainer = document.getElementById("images");

  for (var i = 0; i < files.length; i++) {
    var file = files[i];
    var imageContainer = document.createElement("div");
    imageContainer.className = "d-inline-block mr-1";
    imageContainer.setAttribute("data-filename", file.name);

    var image = document.createElement("img");
    image.style.width = "200px";
    image.src = URL.createObjectURL(file);

    var deleteBtn = document.createElement("a");
    deleteBtn.className = "btn btn-sm btn-danger editable-field";
    deleteBtn.href = "#";
    deleteBtn.setAttribute("onclick", "deleteNewImage(event, '" + file.name + "')");
    deleteBtn.textContent = "X";

    imageContainer.appendChild(image);
    imageContainer.appendChild(deleteBtn);
    imagesContainer.appendChild(imageContainer);
  }
}




    // vars
    let result = document.querySelector('.result'),
      img_result = document.querySelector('.img-result'),
      img_w = document.querySelector('.img-w'),
      img_h = document.querySelector('.img-h'),
      options = document.querySelector('.options'),
      save = document.querySelector('.save'),
      cropped = document.querySelector('.cropped'),
      dwn = document.querySelector('.download'),
      upload = document.querySelector('#file-input'),
      cropper = '';

    // on change show image with crop options
    upload.addEventListener('change', e => {
      if (e.target.files.length) {
        // start file reader
        const reader = new FileReader();
        reader.onload = e => {
          if (e.target.result) {
            // create new image
            let img = document.createElement('img');
            img.id = 'image';
            img.src = e.target.result;
            // clean result before
            result.innerHTML = '';
            // append new image
            result.appendChild(img);
            // show save btn and options
            save.hidden = false;
            options.hidden = false;
            save.classList.remove('hide');
            options.classList.remove('hide');
            // init cropper
            cropper = new Cropper(img);
          }
        };
        reader.readAsDataURL(e.target.files[0]);
      }
    });

    // save on click
    save.addEventListener('click', e => {
      e.preventDefault();
      // get result to data uri
      let imgSrc = cropper.getCroppedCanvas({
        width: img_w.value // input value
      }).toDataURL();
      // remove hide class of img
      cropped.classList.remove('hide');
      img_result.classList.remove('hide');
      // show image cropped
      cropped.src = imgSrc;
      dwn.classList.remove('hide');
      dwn.download = 'imagename.png';
      dwn.setAttribute('href', imgSrc);

      // Convert data URI to Blob object
      function dataURItoBlob(dataURI) {
        var byteString = atob(dataURI.split(',')[1]);
        var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
        var ab = new ArrayBuffer(byteString.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < byteString.length; i++) {
          ia[i] = byteString.charCodeAt(i);
        }
        return new Blob([ab], { type: mimeString });
      }

      // Create FormData object and append the blob data
      var formData = new FormData();
      var file = dataURItoBlob(imgSrc);
      var pUuid = document.getElementById("pUuid").value;
      formData.append('image', file, 'croppedImage.png');
      formData.append('pUuid', pUuid);

      // Make HTTP POST request to the REST API endpoint
      fetch('http://localhost:8080/product/uploadImage', {
        method: 'POST',
        body: formData
      })
        .then(response => response.json())
        .then(data => {
          console.log('Response:', data);
          // Handle the API response as needed
          // Reload the page
          window.location.reload();
        })
        .catch(error => {
          console.error('Error:', error);
          // Handle any errors that occur during the request
           window.location.reload();
        });
    });

    //handle radio btn changes
function radioChange() {
    var radioMultiple = document.getElementById("radioMultiple");
    var radioCrop = document.getElementById("radioCrop");
    var fileInput = document.getElementById("file-input");
    var multipleFileInput = document.getElementById("multiple-file-input");
    var saveCropBtn = document.getElementById("saveCropBtn");
    var saveBtn = document.getElementById("save-btn");

    if (radioMultiple.checked) {
        multipleFileInput.hidden = false;
        fileInput.hidden = true;
        saveBtn.hidden = false;
        saveCropBtn.hidden = true;
    } else {
        multipleFileInput.hidden = true;
        fileInput.hidden = false;
        saveBtn.hidden = true;
    }
}
