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
			$('#editUserId').val(order.orderID);
			$('#editName').val(order.coffee);
			$('#editPassword').val(order.milk);
			$('#editType').val(order.sugar);


			$('.modal').on("click",'#btnSave', function(){
				//var passwordValidationRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
				username = user.username;
				password = SHA1($('#editPassword').val());
				userRole = $('#editType').val();



				if(password == "" || userRole == "Choose User Type" ){
					$('#editModal').modal('show');
					console.log("editModal");
				} else {
					$.ajax({
						type: 'PUT',
						contentType: 'application/json',
						url: rootURL + '/' + $('#editName').val(),
						dataType: "json",
						data: formToJSONUserUpdate(),
						success: function(data, textStatus, jqXHR){
							$('#editUserId').val(user.userId);
							$('#editName').val(user.username);
							$('#editPassword').val(user.password);
							$('#editType').val(user.type);
							//$('#userUpdateModal').modal('toggle');
//							alert('User updated successfully');
							$("#userSuccess").html("User updated successfully");
							$("#userSuccess").show();
							location.reload(true);
						},
						error: function(jqXHR, textStatus, errorThrown){
//							alert('Error in put ajax');
							$("#userError").html("Error occured");
							$("#userError").show();

						}
					}) 
				}
			});

		});

});
$('#table_id_order').DataTable();	
};
