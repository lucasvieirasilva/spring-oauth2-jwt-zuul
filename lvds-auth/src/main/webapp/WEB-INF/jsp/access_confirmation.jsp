<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Confirm Access</title>

<link rel="shortcut icon" href="../images/favicon.ico"
	type="image/x-icon">
<link rel="stylesheet"
	href="../webjars/bootstrap/3.0.3/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="../webjars/bootstrap/3.0.3/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="../lib/components-font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="../fonts/open-sans/open-sans.css" />
</head>

<body class="bg-white">
	<div class="container">

		<div class="widget flat radius-bordered margin-top-50">
			<div class="widget-header lined">
				<span class="widget-caption">Please Confirm</span>
			</div>

			<div class="widget-body">

				<p>
					You hereby authorize <strong><c:forEach
							items="${client.additionalInformation}" var="entry">
							<c:if test="${entry.key == 'name' }"> "<c:out
									value="${entry.value}" />"</c:if>
						</c:forEach> </strong> to access your protected resources.
				</p>

				<form id="confirmationForm" name="confirmationForm"
					action="<%=request.getContextPath()%>/oauth/authorize"
					method="post">
					<input name="user_oauth_approval" value="true" type="hidden" />
					<ul class="list-unstyled">
						<c:forEach items="${scopes}" var="scope">
							<c:set var="approved">
								<c:if test="${scope.value}"> checked</c:if>
							</c:set>
							<c:set var="denied">
								<c:if test="${!scope.value}"> checked</c:if>
							</c:set>
							<li>
								<div class="form-group">
									<strong>${scope.key}</strong>: <label class="margin-left-10">
										<input name="${scope.key}" value="true" ${approved}
										type="radio" class="colored-success"> <span
										class="text"> Approve</span>
									</label> <label class="margin-left-10"> <input
										name="${scope.key}" value="false" ${denied} type="radio"
										class="colored-danger"> <span class="text">
											Deny</span>
									</label>
								</div>
							</li>
						</c:forEach>
					</ul>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<button class="btn btn-primary" type="submit">Submit</button>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="../webjars/jquery/1.9.0/jquery.min.js"></script>
	<script type="text/javascript"
		src="../webjars/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>
