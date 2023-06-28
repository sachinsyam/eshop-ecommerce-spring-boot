$(document).ready(function(){
      var i=1;
     $("#add_row").click(function(){b=i-1;
      $('#addr'+i).html($('#addr'+b).html()).find('td:first-child').html(i+1);
      $('#tab_logic').append('<tr id="addr'+(i+1)+'"></tr>');
      i++;
  });
     $("#delete_row").click(function(){
    	 if(i>1){
		 $("#addr"+(i-1)).html('');
		 i--;
		 }
	 });



});

	 function passwordChange(){
	 var password = document.querySelector("[name='password']").value;
	 var newPassword = document.querySelector("[name='newPassword']").value;
	 var newPasswordRe = document.querySelector("[name='newPasswordRe']").value;
	 var btn = document.getElementById("submitBtn");

	 if(password == "" && newPassword == "" && newPasswordRe == ""){
         btn.disabled = false;
	 }else if(password !="" && newPassword == newPasswordRe){
	    btn.disabled = false;
	 }
	 else{
	    btn.disabled = true;
	 }


	 };

	function addAddressClick() {
        console.log("add new address");
        var addNewAddressContainer = document.getElementById("addNewAddressContainer");
        var mainProfileForm = document.getElementById("mainProfileForm");
        var flatEdit = document.getElementById("flatEdit");

        mainProfileForm.hidden = true;
        addNewAddressContainer.hidden = false;
        flatEdit.focus();
    }


    function editOldAddress(event,
                            uuid = "",
                           flat = "",
                           area = "",
                           town = "",
                           city = "",
                           state = "",
                           pin = "",
                           landmark = "",
                           defaultAddress = "") {

                             if(pin == "null"){
                                 pin="";
                               }

                 var uuidEdit = document.getElementById("uuidEdit");
                 var flatEdit = document.getElementById("flatEdit");
                 var areaEdit = document.getElementById("areaEdit");
                 var townEdit = document.getElementById("townEdit");
                 var cityEdit = document.getElementById("cityEdit");
                 var landmarkEdit = document.getElementById("landmarkEdit");
                 var stateEdit = document.getElementById("stateEdit");
                 var pinEdit = document.getElementById("pinEdit");
                 var landmarkEdit = document.getElementById("landmarkEdit");
                 var defaultAddressEdit = document.getElementById("defaultAddressEdit");

                 if(uuid == "null"){
                    uuidEdit.disabled = true;
                 }


                 uuidEdit.value = uuid;
                 flatEdit.value = flat;
                 areaEdit.value = area;
                 townEdit.value = town;
                 cityEdit.value = city;
                 stateEdit.value = state;
                 pinEdit.value = pin;
                 landmarkEdit.value = landmark;


                 defaultAddressEdit.checked = defaultAddress;

                 addAddressClick();

       };

