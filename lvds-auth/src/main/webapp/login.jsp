<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login</title>
<link rel="stylesheet"
	href="webjars/bootstrap/3.0.3/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="webjars/bootstrap/3.0.3/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="lib/components-font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="fonts/open-sans/open-sans.css" />
</head>
<body>
	<div class="row">
		<div class="col-lg-4">
			<c:if test="${not empty param.authentication_error}">
				<h1>Woops!</h1>

				<p class="error">Your login attempt was not successful.</p>
			</c:if>
			<c:if test="${not empty param.authorization_error}">
				<h1>Woops!</h1>

				<p class="error">You are not permitted to access that resource.</p>
			</c:if>
			<form action="<c:url value="/login"/>" method="post" role="form">

				<div class="form-group">
					<label for="username">Username</label> <input type="text"
						class="form-control" name='username' autocomplete="off" required />
				</div>

				<div class="form-group">
					<label for="password">Password</label> <input type="password"
						class="form-control" name='password' autocomplete="off" required />
				</div>

				<button type="submit" class="btn btn-default">
					<i class="fa fa-sign-in"></i> <span>Login</span>
				</button>

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>

			<form action="<c:url value="/signin/facebook"/>" method="POST">
				<input type="hidden" name="scope" value="public_profile,email" /> <input
					type="submit" value="Login using Facebook" />
			</form>
		</div>
	</div>

	<script src="webjars/jquery/1.9.0/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>