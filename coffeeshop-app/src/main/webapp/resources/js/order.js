var createURL = "http://localhost:8080/coffeeshop-app/rest/orders";

$(document).ready(function() {
	$("order")
	var formToJSON = function() {
		return JSON.stringify({
			"coffee" : $('#coffee').val(),
			"milk" : $('#milk').val(),
			"sugar" : $('#sugar').val(),
			"status" : "Awaiting Payment"
		});
	};

	$(document).on("click", '#submit01', function() {
		addOrder();
	});

	var addOrder = function() {
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : createURL,
			dataType : "json",
			data : formToJSON(),
			success : function(data, textStatus, jqXHR) {
				alert("Order created successfully");
				location.replace("myOrders.html");
			},
			error : function(jqXHR, textStatus, errorThrown) {
				
				alert('Error occured while creating Order:'+textStatus);
				
			}
		});

	};
});
