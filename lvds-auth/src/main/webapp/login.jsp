<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!DOCTYPE html>
	<html lang="en">

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Login</title>
		<link rel="stylesheet" href="lib/components-font-awesome/css/fontawesome-all.min.css" />
		<link rel="stylesheet" href="fonts/open-sans/open-sans.css" />
		<link rel="stylesheet" href="styles/style.css" />
	</head>

	<body>
		<div class="wrapper">

			<div class="login">
				<p class="title">Log in</p>
				<form action="<c:url value="/signin/facebook"/>" method="POST">
					<input type="hidden" name="scope" value="public_profile,email" />
					<button class="social-login social-login-facebook">
						Continue with Facebook
					</button>
				</form>

				<p class="text-center">Or</p>

				<form action="<c:url value="/login"/>" method="post" role="form">
					<c:if test="${not empty param.authentication_error}">
						<h4>Woops!</h4>
						<p class="error">Your login attempt was not successful.</p>
					</c:if>
					<c:if test="${not empty param.authorization_error}">
						<h4>Woops!</h4>
						<p class="error">You are not permitted to access that resource.</p>
					</c:if>

					<input type="text" name='username' required placeholder="Username" autofocus/>
					<i class="fas fa-user"></i>

					<input type="password" name='password' required placeholder="Password" />
					<i class="fas fa-key"></i>

					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

					<button type="submit">
						<i class="spinner"></i>
						<span class="state">Log in</span>
					</button>
				</form>
			</div>
		</div>

		<script src="lib/jquery/dist/jquery.min.js"></script>
		<script src="scripts/app.js"></script>
	</body>

	</html>