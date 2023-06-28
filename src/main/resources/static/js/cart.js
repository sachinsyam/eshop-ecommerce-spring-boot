$(document).ready(function() {
   $('.updateCartApiTrigger').click(function(e) {
        e.preventDefault(); // Prevent the default form submission

        // Create an object to hold the form data
       // var items = $("#itemList").val();
        var elements = document.querySelectorAll('[id^="itemList"]');



            var formData = [];
                $('[id^="itemList"]').each(function() {
                    var itemData = {};
                    itemData.uuid = $(this).find('.column-0').text();
                  //  itemData.productId = $(this).find('.column-2').text();
                  //  itemData.price = $(this).find('.column-3').text();
                    itemData.qty = $(this).find('.num-product').val();
                  //  itemData.total = $(this).find('.column-5').text();
                    formData.push(itemData);
                });

         var qty = $("#qty").val();

        // Send the Ajax request
        $.ajax({
            url: '/cart/update', // URL to submit the form to
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(response) {
                // Handle the success response
                console.log('Form submitted successfully');
                location.reload();
                // You can perform any additional actions or update the UI here
            },
            error: function(xhr, status, error) {
                // Handle the error response
                console.error('Error submitting form: ' + error);
                // You can display an error message or perform any other error handling here
            }
        });
    });
});

//update total locally


  // Function to update total and cart total
  function updateTotals() {
    var cartTotal = 0;

    $('.table_row').each(function() {
      var row = $(this);
      var price = parseFloat(row.find('.column-3').text());
      var quantity = parseInt(row.find('.num-product').val());
      var total = price * quantity;

      row.find('.column-5').text(total.toFixed(1));
      cartTotal += total;
    });

    $('.cartTotal').text(cartTotal.toFixed(1));
  }

  // Event handler for changing quantity
//  $('.num-product').on('change', function() {
//    updateTotals();
//  });

//delete item
$(document).ready(function() {
  $('.deleteTrigger').on('click', function() {
    var uuid = $(this).data('uuid');
    var quantityInput = $('#itemList-' + uuid).find('.num-product');
    quantityInput.val(0);
  });
});

function paymentChange(){
    var codCheckout = document.getElementById("codCheckout");
    var onlineCheckout = document.getElementById("onlineCheckout");
    var paymentMethod = document.getElementById("paymentMethod");
    if(paymentMethod.value == "COD"){
        onlineCheckout.hidden = true;
        codCheckout.hidden = false;
    }else{
       onlineCheckout.hidden = false;
       codCheckout.hidden = true;
    }
}

//RAZORPAY

var xhttp = new XMLHttpRequest();


function CreateOrderID(){


   var amount = document.getElementById("totalAmount").value;
   var nameOfUser = document.getElementById("nameOfUser").value;
   var selectedOrderAddress = document.getElementById("selectedOrderAddress").value;
   var couponCode = document.getElementById("couponCode").value;
   var onlineCheckout = document.getElementById("onlineCheckout");
   var payLoader = document.getElementById("payLoader");
   var couponFrom = document.getElementById("couponFrom");
   var addressSelector = document.getElementById("addressSelector");
   var cartItemBtn = document.getElementById("cartItemBtn");
 //  var cartItemBtn2 = document.getElementById("cartItemBtn2");
   var updateCartButton = document.getElementById("updateCartButton");
   var couponApplied = document.getElementById("couponApplied");

   onlineCheckout.hidden = true;
   if(couponApplied == null || couponApplied.hidden){
     couponFrom.hidden = true;
   }
   cartItemBtn.hidden = true;
   updateCartButton.hidden = true;
   addressSelector.hidden = true;
   payLoader.hidden = false;
 //  cartItemBtn2.hidden = false;

   var orderRequest = {
     amount: amount*100, // replace with the actual order amount
     currency: 'INR', // replace with the appropriate currency
     receipt: 'ORDER_RECEIPT', // replace with the receipt value
     address: selectedOrderAddress,
     coupon: couponCode
   };

      var savedResponse  = {
        errorDescription: "response.error.description",
        orderId: "razorpayOrderId"
    };

  $.ajax({
    url: '/razorpay/pay',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(orderRequest),
    success: function(response) {
      console.log(response);
      if(response.errorCode == "3000"){
        console.log(response.errorDescription);
        showErrorCode(response);

      }else if(response.orderId == null){
          console.log("invalid orderId and orderUuid returned from server. No items found in cart, reload page");

            var payload = {
                  errorDescription: "Payment Cancelled By User",
                   orderId: savedResponse.orderId
              };
             // Make the POST API call
              fetch('/razorpay/storePaymentErrorResponse', {
                  method: 'POST',
                  headers: {
                      'Content-Type': 'application/json'
                  },
                  body: JSON.stringify(payload)
              })
              .then(function(response) {
                  // Handle the response from the server
                  if (response.ok) {
                      console.log('POST request successful');
                  } else {
                      console.log('POST request failed');
                  }
              })
              .catch(function(error) {
                  console.log('Error occurred while making the POST request:', error);
              });


          window.location.href = '/viewCart?orderFailed=true';
      }else{
          savedResponse = response;

          document.getElementById("generatedOrderUuid").value = response.orderUuid;


          OpenCheckout(amount, nameOfUser, response.orderId, response.orderUuid)
      }


    },
    error: function(xhr, textStatus, errorThrown) {
      // Handle the error response
      console.error('Error creating order');
    }
  });


}
/*
On Payment Success
Razorpay makes a POST call to the callback URL with the razorpay_payment_id,
razorpay_order_id and razorpay_signature in the response object of the successful payment.
Only successful authorisations are auto-submitted.
*/
function OpenCheckout(amount, nameOfUser, razorpayOrderId, uuid) {
    var options = {
        "key": "rzp_test_ejnWYAlGAFzF29", // Enter the Key ID generated from the Dashboard
        "amount": amount.toString(), // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
        "currency": "INR",
        "name": nameOfUser,
        "description": "description",
        "order_id": razorpayOrderId,
        "callback_url": "/paymentGatewayResponse?orderHistoryId="+uuid,
        "theme": {
            "color": "#004A55"
        }
    };
    var rzp1 = new Razorpay(options);
rzp1.on('payment.failed', function (response) {
    console.log(response.error.code);
    console.log(response.error.description);
    console.log(response.error.source);
    console.log(response.error.step);
    console.log(response.error.reason);
    console.log(razorpayOrderId);
    console.log(response.error.metadata.payment_id);

    // Create the payload with the necessary values
    var payload = {
        errorCode: response.error.code,
        errorDescription: response.error.description,
        errorSource: response.error.source,
        errorStep: response.error.step,
        errorReason: response.error.reason,
        orderId: razorpayOrderId,
        paymentId: response.error.metadata.payment_id
    };

    // Make the POST API call to store error details.
    fetch('/razorpay/storePaymentErrorResponse', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
    .then(function(response) {
        // Handle the response from the server
        if (response.ok) {
            console.log('POST request successful');
        } else {
            console.log('POST request failed');
        }
    })
    .catch(function(error) {
        console.log('Error occurred while making the POST request:', error);
    });
});


   rzp1.on('payment.successful', function (response) {
       console.log("Payment successful!");
       console.log(response.razorpay_payment_id);
       console.log(response.response.razorpay_order_id);
       console.log(response.response.razorpay_signature);
   });

    rzp1.open();
    e.preventDefault();
}

function showErrorCode(error){
    var paymentErrorMsg = document.getElementById("paymentErrorMsg");
    paymentErrorMsg.innerHTML = error.errorDescription;
    paymentErrorMsg.hidden = false;
}

function checkTotal(){
  var totalAmount = document.getElementById("totalAmount").value;
  var onlineOption = document.getElementById("onlineOption");
  var onlinePayHiddenMsg = document.getElementById("onlinePayHiddenMsg");

  console.log(totalAmount);
  if(totalAmount > 80000){
    console.log("Total more than 80k, only COD available");
    onlinePayHiddenMsg.hidden=false;
    onlineOption.hidden=true;
  }else{
    console.log("Total less than 80k");
    onlinePayHiddenMsg=true;
    onlineOption.hidden=false;
  }
}

window.onload = function() {
  checkTotal();
};
