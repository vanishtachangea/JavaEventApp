<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<title>Afrinic</title>

<script>
function myFunction() {
  var x, text;

  // Get the value of the input field with id="numb"
  x = document.getElementById("fileId").value;

  // If x is Not a Number or less than one or greater than 10
  if (isNaN(x) || x < 1 || x > 10) {
    text = "Input not valid";
  } else {
    text = "Input OK";
  }
  document.getElementById("demo").innerHTML = text;
}
</script>
</head>
<body>

	
	<div class="container">
  <h2>Afrinic File Upload</h2>
  <p>Please select a file to upload</p>
  <form:form method="post" enctype="multipart/form-data"
				modelAdivivibute="uploadedFile" action="fileUpload">
    <div class="form-group">
      <label for="email">Upload File:</label>
      						<div style="color: red; font-style: italic;">
							<form:errors path="email" />
						</div>
    </div>
    <div class="form-group">
      <input type="file" name="file"  class="form-control" id="fileId"/>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
  </form:form>
</div>
	
</body>
</html>
