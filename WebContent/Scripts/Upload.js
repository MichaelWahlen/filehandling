function upload() {
    		var file = $('input[name="file"').get(0).files[0];
    		var formData = new FormData();
    		formData.append('file', file);    		
    		$.ajax({
    	        url: 'http://localhost:8080/filehandling/rest/controller/upload',
    	        type: 'POST',
    	        data : formData,
    	        cache : false,
    	        contentType : false,
    	        processData : false,
    	        dataType : "json",
    	        success : function(response) {    	        		        	
    	        	$.each(response, function(index, item){ 
    	        		console.log(item.message); $("<p>"+ item.message + "</p>").appendTo( ".inner" );
    	        	});    	        	
    	        },
    	        error : function() {
    	          alert("error");
    	        }
    	      });
        };