var rootURL = "http://localhost:8080/coffeeshop-app/rest/orders";


//When Dom is ready...
$(document).ready(function(){
	findAll();
	$("#userSuccess").hide();
	$("#userError").hide();
});


var findAll=function(){
	console.log('findAll');
	$.ajax({
		type:'GET',
		url: rootURL,
		datatype:"json",
		success: renderList
	});
};

var formToJSONUserUpdate=function(){
	var orderID = $('#orderID').val();
	return JSON.stringify({
		"orderID": orderID,
		"coffee": $('#coffee').val(), 
		"milk": $('#milk').val(),
		"sugar":  $('#sugar').val(),
		"status" : "Awaiting Payment"
	});
};




var renderList = function(data){
	console.log("response");
	$.each(data,function(index,order){
		var orderID = order.orderID;
		console.log(order.coffee);
		if(order.status == "Awaiting Payment"){
			$('#table_body_order').append('<tr><td>'+order.orderID+'</td><td>'+order.coffee+'</td><td>'+order.milk+'</td><td>'+order.sugar+'</td><td>'+order.status+'</td><td>'
					+'<button type="button" id="edit'+orderID+'" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm" userId="' 
					+ orderID + '" data-toggle="modal" data-target="#editModal">EDIT</button></td></tr>');
		}
		else{
			$('#table_body_order').append('<tr><td>'+order.orderID+'</td><td>'+order.coffee+'</td><td>'+order.milk+'</td><td>'+order.sugar+'</td><td>'+order.status+'</td>');
		}
		$(document).on("click",'#edit'+orderID, function(){
			console.log("Edit clicked for " + orderID);
			$("#editTitle").html("EDITING ORDER #"+order.orderID);
			$("#orderID").val(order.orderID);
			$('#coffee').val(order.coffee);
			$('#milk').val(order.milk);
			$('#sugar').val(order.sugar);


			$('.modal').on("click",'#save', function(){
				$('#orderID').val(order.orderID);
				$('#coffee').val($('#coffee').val());
				$('#milk').val( $('#milk').val());
				$('#sugar').val( $('#sugar').val());




				$.ajax({
					type: 'PUT',
					contentType: 'application/json',
					url: rootURL + '/' + $('#editName').val(),
					dataType: "json",
					data: formToJSONUserUpdate(),
					success: function(data, textStatus, jqXHR){
						$('#orderID').val(order.orderID);
						$('#coffee').val(order.coffee);
						$('#milk').val(order.milk);
						$('#sugar').val(order.sugar);
						alert('Coffee updated successfully');
						location.reload(true);
					},
					error: function(jqXHR, textStatus, errorThrown){
						alert('Error');


					}
				})

			});

		});

	});
	$('#table_id_order').DataTable();	
};
