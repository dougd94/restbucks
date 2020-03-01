$(document).ready(function() {
	var currentLocation = window.location;
	//DOM manipulation code
	$("#order").hide();
	$("#currentUserName").hide();
	$("#userAccLogo").hide();
	if(sessionStorage.getItem("type") != "Customer" ){
		if(currentLocation == "http://localhost:8080/coffeeshop-app/order.html" | currentLocation == "http://localhost:8080/coffeeshop-app/order.html#" | currentLocation == "http://localhost:8080/coffeeshop-app/index.html"){
			$("#order").hide();
			$("#order").prop('disabled', true);
			
		}
	}

	if(window.location == "http://localhost:8080/coffeeshop-app/order.html"){
		$("#order").prop('disabled', true);
		$("#order").hide();
	}
	if(sessionStorage.getItem("type") == "Customer" || sessionStorage.getItem("type") == "Worker"){
		if(sessionStorage.getItem("type") == "Customer"){
			$("#order").show();
		}
		var user = getUser(sessionStorage.getItem("name"));
		var username = user.username;
		$("#order").prop('disabled', false);
		$("#loginButton").hide();
		$("#currentUserName").html(username)
		$("#currentUserName").show();
		$("#userAccLogo").show();
	}
});

function redirect() {
	sessionStorage.clear();
	location.reload();
}

function redirectWorker() {
	sessionStorage.clear();
}


var attempt = 3; // Variable to count number of attempts.
var authenticationURL = "http://localhost:8080/coffeeshop-app/rest/users";

//Below function Executes on click of login button.
function validate() {
	var userName = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	if(userName == "" ||  password == ""){
		// If any of the fields are not entered.
		alert("Seems like you left some fields empty! Please fill in all fields!");
		return;
	}else
	{
		var user = getUser(userName);
		if (user != null)
		{
			var dbPasswd = user.password;
			var userId = user.userId;
			var type = user.type;
			var username = user.username
			// Convert the password from the user to SHA1 encoded string and compare with the password stored in DB.
			if (SHA1(password) == dbPasswd)
			{
				if( type == 'Worker')
				{
					window.location="WorkerDashboard.html";
					sessionStorage.setItem("type", "Worker");
					sessionStorage.setItem("name", username);
				} 

				else if (type == 'Customer'){
					location.reload();
					sessionStorage.setItem("type", "Customer");
					sessionStorage.setItem("name", username);
				}
			}
			else
			{
				attempt--;
				if(attempt == 1){
					alert("Wrong Password Entered. You have  " + attempt + " attempt left."); 
				} else {
					alert("Wrong Password Entered. You have left " + attempt + " attempts left."); 
				}
				// Disabling fields after 3 attempts for 50000msec.
				if (attempt == 0) {
					attemptChecking(true);
					setTimeout(function() {
						attemptChecking(false)
					}, 50000);
				}
			}
		}

	}

	function attemptChecking(c) {
		document.getElementById("username").disabled = c;
		document.getElementById("password").disabled = c;
		document.getElementById("submit1").disabled = c;
		return true;
	}



}

//Retrieving the username using the 'GET' method in the json..
var getUser = function (userName)
{
	console.log(" userName: " + userName);
	var user;
	try {
		$.ajax({
			type : 'GET',
			url : authenticationURL + '/'+userName+'/',
			async : false,
			dataType : "json",
			success : function(data) {
				if( data != null)
				{
					console.log("DATA: " + data);
					user = data;
				}
				else
					alert('No such user exists.');	

			},
			error : function(data) {
				alert('No such user exists.');

			}

		});

	} catch (e) {
		alert("Error occurred: " + e.message);
	}
	return user;

}


window.onload=function(){
	document.getElementById("submit1").onclick=validate;
} 	


/**
 * Secure Hash Algorithm (SHA1)
 * http://www.webtoolkit.info/
 **/
//This function encodes the message (password) using SHA1 algorithm. 
//This is used for comparing the password entered by the user with the password stored in DB.
function SHA1(msg) {
	function rotate_left(n,s) {
		var t4 = ( n<<s ) | (n>>>(32-s));
		return t4;
	};
	function lsb_hex(val) {
		var str='';
		var i;
		var vh;
		var vl;
		for( i=0; i<=6; i+=2 ) {
			vh = (val>>>(i*4+4))&0x0f;
			vl = (val>>>(i*4))&0x0f;
			str += vh.toString(16) + vl.toString(16);
		}
		return str;
	};
	function cvt_hex(val) {
		var str='';
		var i;
		var v;
		for( i=7; i>=0; i-- ) {
			v = (val>>>(i*4))&0x0f;
			str += v.toString(16);
		}
		return str;
	};
	function Utf8Encode(string) {
		string = string.replace(/\r\n/g,'\n');
		var utftext = '';
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
		}
		return utftext;
	};
	var blockstart;
	var i, j;
	var W = new Array(80);
	var H0 = 0x67452301;
	var H1 = 0xEFCDAB89;
	var H2 = 0x98BADCFE;
	var H3 = 0x10325476;
	var H4 = 0xC3D2E1F0;
	var A, B, C, D, E;
	var temp;
	msg = Utf8Encode(msg);
	var msg_len = msg.length;
	var word_array = new Array();
	for( i=0; i<msg_len-3; i+=4 ) {
		j = msg.charCodeAt(i)<<24 | msg.charCodeAt(i+1)<<16 |
		msg.charCodeAt(i+2)<<8 | msg.charCodeAt(i+3);
		word_array.push( j );
	}
	switch( msg_len % 4 ) {
	case 0:
		i = 0x080000000;
		break;
	case 1:
		i = msg.charCodeAt(msg_len-1)<<24 | 0x0800000;
		break;
	case 2:
		i = msg.charCodeAt(msg_len-2)<<24 | msg.charCodeAt(msg_len-1)<<16 | 0x08000;
		break;
	case 3:
		i = msg.charCodeAt(msg_len-3)<<24 | msg.charCodeAt(msg_len-2)<<16 | msg.charCodeAt(msg_len-1)<<8 | 0x80;
		break;
	}
	word_array.push( i );
	while( (word_array.length % 16) != 14 ) word_array.push( 0 );
	word_array.push( msg_len>>>29 );
	word_array.push( (msg_len<<3)&0x0ffffffff );
	for ( blockstart=0; blockstart<word_array.length; blockstart+=16 ) {
		for( i=0; i<16; i++ ) W[i] = word_array[blockstart+i];
		for( i=16; i<=79; i++ ) W[i] = rotate_left(W[i-3] ^ W[i-8] ^ W[i-14] ^ W[i-16], 1);
		A = H0;
		B = H1;
		C = H2;
		D = H3;
		E = H4;
		for( i= 0; i<=19; i++ ) {
			temp = (rotate_left(A,5) + ((B&C) | (~B&D)) + E + W[i] + 0x5A827999) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B,30);
			B = A;
			A = temp;
		}
		for( i=20; i<=39; i++ ) {
			temp = (rotate_left(A,5) + (B ^ C ^ D) + E + W[i] + 0x6ED9EBA1) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B,30);
			B = A;
			A = temp;
		}
		for( i=40; i<=59; i++ ) {
			temp = (rotate_left(A,5) + ((B&C) | (B&D) | (C&D)) + E + W[i] + 0x8F1BBCDC) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B,30);
			B = A;
			A = temp;
		}
		for( i=60; i<=79; i++ ) {
			temp = (rotate_left(A,5) + (B ^ C ^ D) + E + W[i] + 0xCA62C1D6) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B,30);
			B = A;
			A = temp;
		}
		H0 = (H0 + A) & 0x0ffffffff;
		H1 = (H1 + B) & 0x0ffffffff;
		H2 = (H2 + C) & 0x0ffffffff;
		H3 = (H3 + D) & 0x0ffffffff;
		H4 = (H4 + E) & 0x0ffffffff;
	}
	var temp = cvt_hex(H0) + cvt_hex(H1) + cvt_hex(H2) + cvt_hex(H3) + cvt_hex(H4);

	return temp.toLowerCase();
}