//add to addToCart form submit
    $(document).ready(function() {
        $("#addToCartForm").submit(function(event) {
            event.preventDefault(); // Prevent default form submission

//            var addToCartBtn = document.getElementById("addToCartBtn");
//            var viewCartBtn = document.getElementById("viewCartBtn");
//            var addedToCartAnimation = document.getElementById("addedToCartAnimation");
            var selectedVariantId = document.getElementById("selectedVariantId").value;

//            addToCartBtn.hidden = true;
//            viewCartBtn.hidden = false;
//            addedToCartAnimation.hidden = false;


            var variantId = $("#variantId").val();
            var qty = $("#qty").val();

            var requestData = {
                variantId: variantId,
                qty: qty
            };

            console.log(requestData);

            $.ajax({
                url: "/cart/add",
                type: "POST",
                data: JSON.stringify(requestData),
                contentType: "application/json",
                success: function(response) {
                    // Handle success response
                    console.log(response);
                    if(response == "login"){
                        window.location.href = "/login";
                    }else{
                        //location.reload();
                        window.location.href = "?variantUuid="+selectedVariantId+"&addedToCart=true";
                    }
                },
                error: function(error) {
                    // Handle error response
                    console.error(error);
                }
            });
        });
    });



//variant change from product detail page
$(document).ready(function() {
  $('#variantId').on('change', function() {
    var selectedOption = $(this).find('option:selected');
    var url = selectedOption.data('url');
    if (url) {
      window.location.href = url;
    }
  });
});

//rating star click
function starClick(starValue){
        document.getElementById("ratingInput").value = starValue;
    }